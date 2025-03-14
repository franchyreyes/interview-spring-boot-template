package com.franchy.lil.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderDTO {
    @Schema(example = "50")
    private String orderNumber;
    @Schema(example = "Franchy")
    private String customerName;
    @Schema(example = "Franchy@example.com")
    private String customerEmail;

    public OrderDTO(String orderNumber, String customerName, String customerEmail) {
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

    public OrderDTO() {
    }

    @Override
    public String toString() {
        return "OrderDTO{" + "orderNumber='" + orderNumber + '\'' + ", customerName='" + customerName + '\'' + ", " +
                "customerEmail='" + customerEmail + '\'' + '}';
    }
}
