package com.onlineshopping.service;

import com.onlineshopping.entity.Cart;
import com.onlineshopping.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

	public Cart findByCustomer_Id(long id) {
		// TODO Auto-generated method stub
		return cartRepository.findByCustomerId(id);
	}
	public Cart save(Cart cart) {
		// TODO Auto-generated method stub
		return cartRepository.save(cart);
	}
	public List<Cart> findAll() {
		// TODO Auto-generated method stub
		return cartRepository.findAll();
	}
	public void removeCart(long customerId) {
        Cart cart = findByCustomer_Id(customerId);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }
	
	
}
