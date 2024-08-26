package com.onlineshopping.repository;


import com.onlineshopping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {
	 List<Order> findByOrderDate(LocalDateTime date);
     List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
     List<Order> findByStatus(String status);
}
