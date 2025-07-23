package com.franchy.lil.demo.repository.port;

import com.franchy.lil.demo.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPort {

    List<Order> findAll();
    Optional<Order> findById(Integer id);
    Order save(Order order);
}
