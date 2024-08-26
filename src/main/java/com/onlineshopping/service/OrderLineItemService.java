package com.onlineshopping.service;


import com.onlineshopping.entity.OrderLineItem;
import com.onlineshopping.repository.OrderLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineItemService {

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    public List<OrderLineItem> saveAll(List<OrderLineItem> orderLineItems) {
        return orderLineItemRepository.saveAll(orderLineItems);
    }
}
