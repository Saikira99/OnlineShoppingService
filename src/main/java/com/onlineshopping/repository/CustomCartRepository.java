package com.onlineshopping.repository;


import com.onlineshopping.entity.Cart;

public interface CustomCartRepository {
    Cart findByCustomerId(long customerId);
}
