package com.franchy.lil.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Order")
public class OrderRedis implements Serializable {

    @Id
    private String orderNumber;
    private String customerName;
    private String customerEmail;

    public OrderRedis(String orderNumber, String customerName, String customerEmail) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public OrderRedis() {
    }

    @Override
    public String toString() {
        return "OrderRedis{" + "orderNumber='" + orderNumber + '\'' + ", customerName='" + customerName + '\'' + ", " + "customerEmail='" + customerEmail + '\'' + '}';
    }
}
