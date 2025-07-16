package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.domain.Order;
import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.dto.OrderNumberDTO;
import com.franchy.lil.demo.mapper.OrderMapper;
import com.franchy.lil.demo.model.CustomerModel;
import com.franchy.lil.demo.model.OrderModel;
import com.franchy.lil.demo.model.OrderRedis;
import com.franchy.lil.demo.request.OrderRequest;
import com.franchy.lil.demo.response.ApiResponses;
import com.franchy.lil.demo.service.OrderService;
import com.franchy.lil.demo.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order Endpoint", description = "Operations related to orders")
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


    @Operation(summary = "Save a new order")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "Order created successfully", content = {@Content(mediaType = "application/json", schema =
    @Schema(implementation = OrderDTO.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server" + " error", content = @Content)}
    )
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponses<OrderNumberDTO>> saveOrder(@RequestBody OrderRequest orderRequest) {
        logger.debug("Saving order with request: {}", orderRequest);

        Order order = new Order();
        order.setOrderNumber(orderRequest.orderNumber());
        Customer customer = new Customer();
        customer.setId(orderRequest.customerID());
        order.setCustomer(customer);

        Order saveOrder = this.orderService.addOrder(order);
        OrderNumberDTO orderNumberDTO = OrderMapper.INSTANCE.toOrderNumberDTO(saveOrder);
        ApiResponses<OrderNumberDTO> response = new ApiResponses<>
                (true,
                        "Resource created successfully",
                        orderNumberDTO);
        logger.debug("Order saved successfully: {}", saveOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all orders")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the orders", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders not found", content = @Content)})
    @GetMapping
    public ResponseEntity<ApiResponses<List<OrderDTO>>> getOrder() {
        logger.debug("Retrieving all orders");
        String key = "getAllDataOrder";
        if (this.orderRedisService.checkKeyExists(key)) {
            logger.debug("Cache hit for key: {}", key);
            List<OrderRedis> orderRedisList = this.orderRedisService.getEntities(key);
            ApiResponses<List<OrderDTO>> response = new ApiResponses<>(true, "All order data",
                    OrderMapper.INSTANCE.orderRedisListToOrderDTOList(orderRedisList));
            logger.debug("Retrieved orders from cache");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        logger.debug("Cache miss for key: {}", key);
        List<Order> orders = this.orderService.getAllOrder();
        List<OrderDTO> ordersDTO = OrderMapper.INSTANCE.toDTO(orders);
        List<OrderRedis> ordersRedis = OrderMapper.INSTANCE.toOrderRedis(ordersDTO);
        if (!orders.isEmpty()) {
            this.orderRedisService.save(key, ordersRedis);
            logger.debug("Orders saved to cache with key: {}", key);
        }

        ApiResponses<List<OrderDTO>> response = new ApiResponses<>(true, "Get orders: ", ordersDTO);
        logger.debug("Retrieved orders from database");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}