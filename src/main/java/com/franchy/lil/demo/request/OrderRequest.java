package com.franchy.lil.demo.request;

import com.franchy.lil.demo.model.Customer;

public record OrderRequest(String orderNumber, Customer customer) {
}
