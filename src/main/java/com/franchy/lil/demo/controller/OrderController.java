package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.mapper.OrderMapper;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.model.OrderRedis;
import com.franchy.lil.demo.request.OrderRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.OrderService;
import com.franchy.lil.demo.service.RedisService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final RedisService<OrderRedis> orderRedisService;

    public OrderController(OrderService orderService, RedisService<OrderRedis> orderRedisService) {
        this.orderService = orderService;
        this.orderRedisService = orderRedisService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<Order>> saveOrder(@RequestBody OrderRequest orderRequest) {
        logger.debug("Saving order with request: {}", orderRequest);
        Order order = new Order();
        order.setOrderNumber(orderRequest.orderNumber());
        order.setCustomer(orderRequest.customer());
        Order saveOrder = this.orderService.addOrder(order);
        ApiResponse<Order> response = new ApiResponse<>(true, "Resource created successfully", saveOrder);
        logger.debug("Order saved successfully: {}", saveOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrder() {
        logger.debug("Retrieving all orders");
        String key = "getAllDataOrder";
        if (this.orderRedisService.checkKeyExists(key)) {
            logger.debug("Cache hit for key: {}", key);
            List<OrderRedis> orderRedisList = this.orderRedisService.getEntities(key);
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, "All order data",
                    OrderMapper.INSTANCE.orderRedisListToOrderDTOList(orderRedisList));
            logger.debug("Retrieved orders from cache");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        logger.debug("Cache miss for key: {}", key);
        List<Order> orders = this.orderService.getAllOrder();
        List<OrderDTO> ordersDTO = OrderMapper.INSTANCE.orderToOrderDTOList(orders);
        List<OrderRedis> ordersRedis = OrderMapper.INSTANCE.orderDTOListToOrderRedisList(ordersDTO);
        if (!orders.isEmpty()) {
            this.orderRedisService.save(key, ordersRedis);
            logger.debug("Orders saved to cache with key: {}", key);
        }

        ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, "Get orders: ", ordersDTO);
        logger.debug("Retrieved orders from database");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}