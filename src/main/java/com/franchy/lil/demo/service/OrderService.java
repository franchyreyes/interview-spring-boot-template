package com.franchy.lil.demo.service;

import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.domain.Order;
import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.OrderModel;
import com.franchy.lil.demo.model.OrderRedis;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.repository.jpa.OrderRepository;
import com.franchy.lil.demo.repository.specification.CustomerSpecification;
import com.franchy.lil.demo.repository.specification.OrderSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderSpecification orderAdapter;
    private final CustomerSpecification customerAdapter;

    @Autowired
    public OrderService(OrderSpecification orderAdapter, CustomerSpecification customerAdapter) {
        this.orderAdapter = orderAdapter;
        this.customerAdapter = customerAdapter;
    }

    public Order addOrder(Order order) {
        logger.debug("Adding order: {}", order);
        if (checkCustomerExists(order)) {
            Order savedOrder = this.orderAdapter.save(order);
            logger.debug("Order added successfully: {}", savedOrder);
            return savedOrder;
        }
        logger.debug("Customer not found for order: {}", order);
        throw new ResourceNotFoundException("Customer not found");
    }

    public boolean checkCustomerExists(Order order) {
        logger.debug("Checking if customer exists for order: {}", order);
        Optional<Customer> exists = this.customerAdapter.findById(order.getCustomer().getId());
        logger.debug("Customer exists: {}", exists.isPresent());
        return exists.isPresent();
    }

    public List<Order> getAllOrder() {
        logger.debug("Retrieving all orders");
        List<Order> orders = this.orderAdapter.findAll();
        logger.debug("Retrieved {} orders", orders.size());
        return orders;
    }
}