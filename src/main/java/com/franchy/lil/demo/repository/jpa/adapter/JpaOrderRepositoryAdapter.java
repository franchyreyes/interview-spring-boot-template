package com.franchy.lil.demo.repository.jpa.adapter;

import com.franchy.lil.demo.domain.Order;
import com.franchy.lil.demo.mapper.OrderMapper;
import com.franchy.lil.demo.model.OrderModel;
import com.franchy.lil.demo.repository.jpa.OrderRepository;
import com.franchy.lil.demo.repository.specification.OrderSpecification;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaOrderRepositoryAdapter")
@Profile("prod")
public class JpaOrderRepositoryAdapter implements OrderSpecification {

    private final OrderRepository orderRepository;

    public JpaOrderRepositoryAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll()
                .stream()
                .map(OrderMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return this.orderRepository .findById(id)
                .map(OrderMapper.INSTANCE::toDomain);
    }

    @Override
    public Order save(Order order) {
        OrderModel orderModel = OrderMapper.INSTANCE.toModel(order);
        orderModel = this.orderRepository.save(orderModel);
        return OrderMapper.INSTANCE.toDomain(orderModel);
    }
}
