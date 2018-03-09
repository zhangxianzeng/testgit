package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;
import com.jt.common.vo.SysResult;

public interface CartService {

	List<Cart> findCartListByUserId(Long userId);

	SysResult updateCartNum(Long userId, Long itemId, Integer num);

	void deleteCart(Long userId, Long itemId);

	void saveCart(Cart cart);

}
