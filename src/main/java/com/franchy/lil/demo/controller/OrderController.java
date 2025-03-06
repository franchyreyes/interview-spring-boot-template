package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.mapper.OrderMapper;
import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.request.OrderRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<Order>> saveOrder(@RequestBody OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(orderRequest.orderNumber());
        order.setCustomer(orderRequest.customer());
        Order saveOrder = this.orderService.addOrder(order);
        ApiResponse<Order> response = new ApiResponse<>(true, "Resource created successfully", saveOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderDTO> getOrder(){
        List<Order> orders = this.orderService.getAllOrder();
        return OrderMapper.INSTANCE.toDTOList(orders);
    }

}
