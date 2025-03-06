package com.franchy.lil.demo.repository;

import com.franchy.lil.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
