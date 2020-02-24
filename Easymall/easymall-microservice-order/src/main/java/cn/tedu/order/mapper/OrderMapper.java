package cn.tedu.order.mapper;

import com.jt.common.pojo.Order;

public interface OrderMapper {

	void saveOrder(Order order);

	void queryMyOrder(String userId);

}
