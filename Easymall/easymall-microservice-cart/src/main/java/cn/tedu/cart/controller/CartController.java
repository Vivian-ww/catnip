package cn.tedu.cart.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;

import cn.tedu.cart.service.CartService;


@RestController
@RequestMapping("cart/manage")
public class CartController {
	@Autowired
	CartService cartService;
	
	@RequestMapping("query")
	public List<Cart> queryMyCart(String userId) {
		//对userId非空判断
		if(!StringUtils.isNotEmpty(userId)){
			return null;//userId为空不做后续调用
		}
		List<Cart> cartList = cartService.queryMyCart(userId);
		return cartList;
	}
	
	@RequestMapping("save")
	public SysResult cartSave(Cart cart){
		try{
			cartService.cartSave(cart);
			return SysResult.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "添加购物车失败", null);
		}
	}
	
	@RequestMapping("update")
	public SysResult updateNum(Cart cart){
		try{
			cartService.updateNum(cart);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}	
	}
	
	@RequestMapping("delete")
	public SysResult deleteCart(Cart cart){
		try{
			cartService.deleteCart(cart);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, "", null);
			}
	}

}
