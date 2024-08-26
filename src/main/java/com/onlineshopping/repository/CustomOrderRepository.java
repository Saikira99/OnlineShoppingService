package com.onlineshopping.repository;


import com.onlineshopping.entity.Order;

public interface CustomOrderRepository {
    Order findByCustomerId(long customerId);
}
