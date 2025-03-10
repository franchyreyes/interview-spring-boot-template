package com.franchy.lil.demo.service;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.model.OrderRedis;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.repository.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final RedisService<OrderRedis> redisServices;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        RedisService<OrderRedis> redisServices) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.redisServices = redisServices;
    }

    public Order addOrder(Order order) {
        if (checkCustomerExists(order)) {
            return this.orderRepository.save(order);
        }
        throw new ResourceNotFoundException("Customer not found");
    }

    public boolean checkCustomerExists(Order order) {
        return this.customerRepository.existsById(order.getCustomer().getId());
    }

    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }
}
