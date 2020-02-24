package cn.tedu.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;

import cn.tedu.order.service.OrderService;


@RestController
@RequestMapping("order/manage")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping("save")
	public SysResult saveOrder(Order order){
		try{
			orderService.saveOrder(order);
			return SysResult.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "failed", null);
		}
	}
	
	@RequestMapping("query")
	public SysResult queryMyOrder(String userId){
		try{
			orderService.queryMyOrder(userId);
			return SysResult.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "failed", null);
		}
	}

}
