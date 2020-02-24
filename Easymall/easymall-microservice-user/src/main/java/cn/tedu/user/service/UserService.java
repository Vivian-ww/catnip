package cn.tedu.user.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import com.jt.common.utils.PrefixUtils;
import com.jt.common.utils.UUIDUtil;
import cn.tedu.user.mapper.UserMapper;
import redis.clients.jedis.JedisCluster;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	JedisCluster jedis;
	
	/*SysResult对象:
	Integer status; 200表示用户名不存在,可以使用,其他表示不可用
	String msg;成功返回 “ok”,失败返回其他信息
	Object data;根据需求携带其他数据*/
	public Integer checkUserName(String userName) {
		return userMapper.queryUserName(userName);
	}

	public void userSave(User user) {
		//user的数据填写完整 userId password加密
		user.setUserId(UUIDUtil.getUUID());
		//加密password
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		//insert 数据到数据库
		userMapper.userSave(user);
	}

	public String userLogin(User user) {
		
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		User exists = userMapper.queryUserByNameAndPassword(user);
		if(exists!=null){//不为空，用户存在
			//将userJason保存到redis中                   使用系统时间更加符合业务逻辑
			String ticket = PrefixUtils.USER_TICKET_PREFIX + System.currentTimeMillis() + exists.getUserId();
			//2.登录人数控制逻辑：存入loginTicket(key) ticket(value)
			String loginTicket = PrefixUtils.USER_LOGINTICKETS_PREFIX + exists.getUserId();
			try {
				String userJson = MapperUtil.MP.writeValueAsString(exists);
				if(StringUtils.isNotEmpty(jedis.get(loginTicket))){
					//如果已经存在loginTicket的值，且不为空--删除
					jedis.del(ticket);
				}
				jedis.setex(loginTicket, 60*30, ticket);
				//key:EM_TICKET+"随机数"+userId(唯一)  value:userName,userPassword --userJson			 
				jedis.setex(ticket, 60*30, userJson);
				return ticket;
			} catch (Exception e) {
				e.printStackTrace();
				return ""; 
			}
		}else{
			return "";
		}
	}

	public String queryUserJson(String ticket) {
		//1.登录超时设置
		try{
			//计算缓存剩余时间
			Long leftTime = jedis.pttl(ticket);
			System.out.println(leftTime);
			//续约逻辑：当剩余时间少于多少就续约
			//少于10分钟,延长5分钟
			if(leftTime < 1000*60*10){
				jedis.pexpire(ticket, leftTime + 1000*60*5);
			}
			System.out.println(jedis.pttl(ticket));
			return jedis.get(ticket);		
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

}
