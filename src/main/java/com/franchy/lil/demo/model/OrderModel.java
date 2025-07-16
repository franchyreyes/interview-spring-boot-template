package com.franchy.lil.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity(name = "Order_customer")
public class OrderModel {
    @Id
    @SequenceGenerator(name = "order_id_sequence", sequenceName = "order_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence"

    )
    private Integer id;

    @NotBlank(message = "orderNumber cannot be blank")
    private String orderNumber;

    @ManyToOne
    private CustomerModel customer;


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

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public OrderModel(Integer id, String orderNumber, CustomerModel customer) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customer = customer;
    }

    public OrderModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel order = (OrderModel) o;
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
