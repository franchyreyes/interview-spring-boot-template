package com.franchy.lil.demo.service;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.repository.CustomerRepository;
import com.franchy.lil.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository){
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order addOrder(Order order){
        if(checkCustomerExists(order)){
            return this.orderRepository.save(order);
        }
        throw new ResourceNotFoundException("Customer not found");
    }

    public boolean checkCustomerExists(Order order) {
        return this.customerRepository.existsById(order.getCustomer().getId());
    }

    public List<Order> getAllOrder(){
        return this.orderRepository.findAll();
    }
}
