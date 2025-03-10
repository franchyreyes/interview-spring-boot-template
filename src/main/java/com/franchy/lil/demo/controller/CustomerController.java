package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.request.CustomerRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustmers() {
        return this.customerService.getAllCustomer();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> saveCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());

        this.customerService.saveCustomer(customer);
        ApiResponse<Customer> response = new ApiResponse<>(true, "Resource created successfully", customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer customerID) {
        this.customerService.deleteCustomer(customerID);
    }

    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer customerID,
                               @RequestBody Map<String, Object> customerMap) throws ResourceNotFoundException {
        this.customerService.updateCustomer(customerID, customerMap);
    }
}
