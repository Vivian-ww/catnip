package cn.tedu.product.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.PrefixUtils;
import com.jt.common.vo.EasyUIResult;

import cn.tedu.product.mapper.ProdMapper;
import redis.clients.jedis.JedisCluster;

@Service
public class ProdService {
	
	@Autowired
	ProdMapper prodMapper;
	@Autowired
	JedisCluster jedis;
	
	public EasyUIResult prodPageQuery(Integer page, Integer rows) {
		
		EasyUIResult result = new EasyUIResult();
		
		Integer start = (page-1)*rows;
		List<Product> plist = prodMapper.prodPageQuery(start, rows);
		Integer total = prodMapper.queryTotal();
		
		result.setRows(plist);
		result.setTotal(total);
		return result;
	}

	public Product queryById(String prodId) {
		//select * from t_product product_id=#{prodId}
		//从持久层查询表格返回封装对象，并存入redis
		//定义存入缓存的数据："query_prod" + prodId(key) value(product)
		String prodKey = "query_prod" + prodId;
		//定义一个缓存锁
		String redisLock = "prod_update_"+ prodId +".lock";
		if(jedis.exists(redisLock)){//如果有锁，说明正在修改商品信息，则不使用缓存
			return prodMapper.queryById(prodId);
		}else{//不在修改期间，需要使用缓存
			try{
				if(StringUtils.isNotEmpty(jedis.get(prodKey))){
					String prodJason = jedis.get(prodKey);
					Product product = MapperUtil.MP.readValue(prodJason, Product.class);
					return product;
				}else{	
					Product product = prodMapper.queryById(prodId);
					String prodJason = MapperUtil.MP.writeValueAsString(product);
					jedis.setex(prodKey, 60*24*2, prodJason);
					return product;
				}
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}

	public void productSave(Product product) {
		//补充封装完毕productIde属性值,uuid		
		product.setProductId(UUID.randomUUID().toString());
		prodMapper.productSave(product);
	}

	//事务管理
	//在高并发情况下，如果修改后台数据，缓存数据需要删除
	//且关闭缓存（加锁）---在查询商品处加锁
	public void productUpdate(Product product) {
		
		String redisLock = PrefixUtils.PRODUCT_REDISLOCK_PREFIX+product.getProductId()+".lock";
		String prodKey = PrefixUtils.PRODUCT_QUERY_PREFIX + product.getProductId();
		
		//把锁存入redis(prodLock,商品在缓存中的剩余时间)
		jedis.setex(redisLock, Integer.parseInt(jedis.ttl(prodKey)+""), "");
		//更新之前,对应商品缓存删除
		jedis.del(prodKey);
		//删除缓存成功了,才能进行商品更新,否则用户查到旧数据
		prodMapper.productUpdate(product);
		//更新成功后，关闭锁
		jedis.del(redisLock);
	}

	public int deleteProd(String[] ids) {
		//n用来记录删除成功的次数
		int n =0;
		for(String id:ids){
			n = prodMapper.deleteProd(id) + n ;
		}
		return n;
	}
}
