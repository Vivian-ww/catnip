package cn.tedu.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;

import cn.tedu.cart.mapper.CartMapper;

@Service
public class CartService {

	@Autowired
	CartMapper cartMapper;
	@Autowired
	RestTemplate restTemplate;
	
	public List<Cart> queryMyCart(String userId) {
		return cartMapper.queryMyCart(userId);
	}

	//userId productId,查询数据库表格是否已有数据
	//如果已经存在,将旧num和新num叠加重新update到表格中
	//如果不存在才是insert新增;
	public void cartSave(Cart cart) {
		//1.判断是否存在
		Cart exists = cartMapper.queryOne(cart);
		if(exists != null){//不为空，已存在该商品，只需更新数量
			exists.setNum(cart.getNum()+exists.getNum());
			cartMapper.updateNum(exists);
		}else{//为空，商品不存在，只需新增
			//调用prodservice获取商品详细信息
			Product product = restTemplate.getForObject(
					"http://productservice/product/manage/item/"+cart.getProductId(), 
					Product.class);
			//设置cart的数据
			cart.setProductPrice(product.getProductPrice());
			cart.setProductImage(product.getProductImgurl());
			cart.setProductName(product.getProductName());
			cartMapper.saveCart(cart);
		}
	}

	public void updateNum(Cart cart) {
		cartMapper.updateNum(cart);
	}

	public void deleteCart(Cart cart) {
		cartMapper.deleteCart(cart);
	}

}
