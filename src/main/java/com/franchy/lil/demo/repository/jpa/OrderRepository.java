package com.franchy.lil.demo.repository.jpa;

import com.franchy.lil.demo.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
}
