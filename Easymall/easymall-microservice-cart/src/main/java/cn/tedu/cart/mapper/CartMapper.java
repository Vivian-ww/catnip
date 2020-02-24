package cn.tedu.cart.mapper;

import java.util.List;

import com.jt.common.pojo.Cart;

public interface CartMapper {

	List<Cart> queryMyCart(String userId);

	Cart queryOne(Cart cart);

	void updateNum(Cart exists);

	void saveCart(Cart cart);

	void deleteCart(Cart cart);

}
