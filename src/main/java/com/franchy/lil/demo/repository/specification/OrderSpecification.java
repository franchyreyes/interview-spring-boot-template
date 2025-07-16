package com.franchy.lil.demo.repository.specification;

import com.franchy.lil.demo.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderSpecification {

    List<Order> findAll();
    Optional<Order> findById(Integer id);
    Order save(Order order);
}
