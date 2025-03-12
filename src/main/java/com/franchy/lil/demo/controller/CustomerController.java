package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.request.CustomerRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Customer>>> getCustmers() {
        logger.debug("Retrieving all active customers");
        List<Customer> customers = this.customerService.getAllCustomer();
        logger.debug("Retrieved {} customers", customers.size());
        ApiResponse<List<Customer>> response = new ApiResponse<>(true, "Retrieving all customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/active/adults")
    public ResponseEntity<ApiResponse<List<Customer>>> getCustmersActiveAndAdult() {
        logger.debug("Retrieving all customers");
        List<Customer> customers = this.customerService.getAllCustomer();
        logger.debug("Retrieved {} customers", customers.size());
        ApiResponse<List<Customer>> response = new ApiResponse<>(true, "Retrieving all customers", customers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> saveCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        logger.debug("Saving customer with request: {}", customerRequest);
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());
        customer.setActive(true);

        this.customerService.saveCustomer(customer);
        ApiResponse<Customer> response = new ApiResponse<>(true, "Resource created successfully", customer);
        logger.debug("Customer saved successfully: {}", customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer customerID) {
        logger.debug("Deleting customer with ID: {}", customerID);
        this.customerService.deleteCustomer(customerID);
        logger.debug("Customer deleted successfully");
    }

    @PutMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer customerID,
                               @RequestBody Map<String, Object> customerMap) throws ResourceNotFoundException {
        logger.debug("Updating customer with ID: {}", customerID);
        this.customerService.updateCustomer(customerID, customerMap);
        logger.debug("Customer updated successfully");
    }
}
