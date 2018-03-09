package com.jt.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl extends BaseService<Cart>implements CartService,BeanNameAware {
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		
		return cartMapper.select(cart);
	}

	@Override
	public SysResult updateCartNum(Long userId, Long itemId, Integer num) {
		
		try {
			cartMapper.updateCartByUserIdAndItemId(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "商品修改失败");
		}
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("我是service"+name);
		
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		super.deleteByWhere(cart);
		
	}

	@Override
	public void saveCart(Cart cart) {
		//首先根据userId和CartId判断购物车信息是否存在,如果存在则数量求和.如果不存则在进行入库操作
		
		Cart queryCart = new Cart();
		queryCart.setUserId(cart.getUserId());
		queryCart.setItemId(cart.getItemId());
		
		Cart cartDB = super.queryByWhere(queryCart);
		
		if(cartDB !=null){
			cartDB.setNum(cartDB.getNum()+cart.getNum());
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}else{
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
	}
}
