package com.franchy.lil.demo.service;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.model.OrderRedis;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.repository.jpa.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
        logger.debug("Adding order: {}", order);
        if (checkCustomerExists(order)) {
            Order savedOrder = this.orderRepository.save(order);
            logger.debug("Order added successfully: {}", savedOrder);
            return savedOrder;
        }
        logger.error("Customer not found for order: {}", order);
        throw new ResourceNotFoundException("Customer not found");
    }

    public boolean checkCustomerExists(Order order) {
        logger.debug("Checking if customer exists for order: {}", order);
        boolean exists = this.customerRepository.existsById(order.getCustomer().getId());
        logger.debug("Customer exists: {}", exists);
        return exists;
    }

    public List<Order> getAllOrder() {
        logger.debug("Retrieving all orders");
        List<Order> orders = this.orderRepository.findAll();
        logger.debug("Retrieved {} orders", orders.size());
        return orders;
    }
}