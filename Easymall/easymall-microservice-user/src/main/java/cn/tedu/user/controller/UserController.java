package cn.tedu.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;

import cn.tedu.user.service.UserService;

@RestController
@RequestMapping("user/manage")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("checkUserName")
	public SysResult checkUsername(String userName) {
		Integer exists = userService.checkUserName(userName);
		//根据exist判断返回结果
		if(exists == 0){//不存在就是可用数据
			return SysResult.ok();
		}else{
			return SysResult.build(201, "已存在", null);
		}	
	}
	
	@RequestMapping("save")
	public SysResult userRegist(User user) {
		try{
			userService.userSave(user);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
	
	//这里加入HttpServletRequest/Response是因为要设置Cookies
	@RequestMapping("login")
	public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response){
		
			String ticket = userService.userLogin(user);
			if(StringUtils.isNotEmpty(ticket)){
				//tiket的值必须是唯一的
				CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
				return SysResult.ok();
			}else{
				return SysResult.build(201,"用户名或密码错误", null);
			}
	}
	
	@RequestMapping("/query/{ticket}")
	public SysResult checkLoginUser(@PathVariable String ticket){
		String userJson=userService.queryUserJson(ticket);
		//判断非空
		if(StringUtils.isNotEmpty(userJson)){
			//确实曾经登录过.也正在登录使用状态中
			return SysResult.build(200, "ok", userJson);
		}else{
			return SysResult.build(201, "", null);
		}
	}

}
