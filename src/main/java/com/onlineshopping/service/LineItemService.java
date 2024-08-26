package com.onlineshopping.service;


import com.onlineshopping.entity.LineItem;
import com.onlineshopping.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineItemService {

    @Autowired
    private LineItemRepository lineItemRepository;

    public LineItem save(LineItem lineItem) {
        return lineItemRepository.save(lineItem);
    }

    public LineItem findById(Long id) {
        return lineItemRepository.findById(id).orElse(null);
    }

    public List<LineItem> findAll() {
        return lineItemRepository.findAll();
    }

    public void delete(Long id) {
        lineItemRepository.deleteById(id);
    }
}
