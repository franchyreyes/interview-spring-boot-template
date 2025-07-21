package com.franchy.lil.demo.repository.memory.adapter;

import com.franchy.lil.demo.domain.Order;
import com.franchy.lil.demo.repository.specification.OrderSpecification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("orderMemoryAdapterRepo")
@Profile("dev")
public class OrderMemoryAdapter implements OrderSpecification {
    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }
}
