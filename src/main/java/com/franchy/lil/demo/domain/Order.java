package com.franchy.lil.demo.domain;

import com.franchy.lil.demo.model.CustomerModel;

import java.util.Objects;

public class Order {

    private Integer id;
    private String orderNumber;

    private Customer customer;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order(Integer id, String orderNumber, Customer customer) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customer = customer;
    }

    public Order() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, customer);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderNumber='" + orderNumber + '\'' + ", customer=" + customer + '}';
    }
}
