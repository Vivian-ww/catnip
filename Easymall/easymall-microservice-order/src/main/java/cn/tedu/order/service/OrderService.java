package cn.tedu.order.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.common.pojo.Order;
import com.jt.common.utils.UUIDUtil;

import cn.tedu.order.mapper.OrderMapper;

public class OrderService {

	@Autowired
	OrderMapper orderMapper;
	
	public void saveOrder(Order order) {
		order.setOrderId(UUIDUtil.getUUID());
		orderMapper.saveOrder(order);
	}

	public void queryMyOrder(String userId) {
		orderMapper.queryMyOrder(userId);
	}

}
