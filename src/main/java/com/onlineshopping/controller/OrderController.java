package com.onlineshopping.controller;


import com.onlineshopping.entity.*;
import com.onlineshopping.exception.OrderCancellationFailedException;
import com.onlineshopping.service.CartService;
import com.onlineshopping.service.CustomerService;
import com.onlineshopping.service.OrderLineItemService;
import com.onlineshopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/shop/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineItemService orderLineItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestParam("customerId") long customerId, @RequestBody List<Product> products) {
        // Retrieve customer
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (!optionalCustomer.isPresent()) {
            return ResponseEntity.badRequest().body("Customer not found");
        }
        Customer customer = optionalCustomer.get();

        // Retrieve cart
        Cart cart = cartService.findByCustomer_Id(customerId);
        if (cart == null) {
            return ResponseEntity.badRequest().body("Cart not found for the customer");
        }

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Pending");

        double totalAmount = 0;

        List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (Product product : products) {
            LineItem cartLineItem = cart.getLineItemList().stream()
                .filter(li -> li.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

            if (cartLineItem != null) {
                OrderLineItem orderLineItem = new OrderLineItem();
                orderLineItem.setProduct(product);
                orderLineItem.setUnitPrice(cartLineItem.getUnitPrice());
                orderLineItem.setQuantity(cartLineItem.getQuantity());
                orderLineItem.setItemTotal(cartLineItem.getItemTotal());
                
                orderLineItem.setOrder(order);
                orderLineItems.add(orderLineItem);
                totalAmount += orderLineItem.getItemTotal();
            }
        }

        order.setOrderLineItems(orderLineItems);
        order.setTotalAmount(totalAmount);

        // Save order and line items
        orderService.save(order);
        orderLineItemService.saveAll(orderLineItems);

        // Optionally clear the cart or update its status
        cartService.removeCart(customerId);

        return ResponseEntity.ok("Order created successfully");
    }

    @PostMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam("orderId") Long orderId) {
        try {
            Order cancelledOrder = orderService.cancelOrder(orderId);
            return ResponseEntity.ok("Order cancelled successfully: " + cancelledOrder.getOrderId());
        } catch (RuntimeException e) {
            throw new OrderCancellationFailedException("Order cancellation failed: " + e.getMessage());
        }
    }

    @GetMapping("/viewAllOrders")
    public ResponseEntity<List<Order>> viewAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/viewByStatus")
    public ResponseEntity<List<Order>> viewByStatus(@RequestParam("status") String status) {
        try {
            List<Order> orders = orderService.findOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
//    @GetMapping("/viewByOrderedDate")
//    public ResponseEntity<List<Order>> viewByOrderedDate(@RequestParam("date") String date) {
//        try {
//            LocalDateTime orderDate = LocalDateTime.parse(date); // Assumes date is in ISO format
//            List<Order> orders = orderService.findOrdersByDate(orderDate);
//            return ResponseEntity.ok(orders);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
    
}
