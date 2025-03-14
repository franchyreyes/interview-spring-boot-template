package com.franchy.lil.demo.service;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer() {
        logger.debug("Retrieving all active customers");
        List<Customer> customers = this.customerRepository.findByActiveTrue();
        logger.debug("Retrieved {} active customers", customers.size());
        return customers;
    }

    public List<Customer> getAllActiveAndAdultCustomer() {
        logger.debug("Retrieving all active and adults customers");
        List<Customer> customers = this.customerRepository.findByActiveTrueAndAgeGreaterThanEqual(18);
        logger.debug("Retrieved {} active and adults customers", customers.size());
        return customers;
    }

    public void saveCustomer(Customer customer) {
        logger.debug("Saving customer: {}", customer);
        this.customerRepository.save(customer);
        logger.debug("Customer saved successfully");
    }

    public void deleteCustomer(Integer customerID) {
        logger.debug("Deleting customer with ID: {}", customerID);
        Customer customer = this.customerRepository.findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerID));
        customer.setActive(false);
        this.customerRepository.save(customer);
        logger.debug("Customer deleted successfully");
    }

    public Customer updateCustomer(Integer customerID, Map<String, Object> customerMap) throws ResourceNotFoundException {
        logger.debug("Updating customer with ID: {}", customerID);
        Customer customer = this.customerRepository.findById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerID));

        customerMap.forEach((key, value) -> {
            switch (key) {
                case "name":
                    customer.setName((String) value);
                    break;
                case "email":
                    customer.setEmail((String) value);
                    break;
                case "age":
                    customer.setAge(Integer.valueOf(value.toString()));
                    break;
                default:
                    logger.warn("Unknown property: {}", key);
                    break;
            }
        });

        logger.debug("Customer updated successfully: {}", customer);
        return this.customerRepository.save(customer);

    }
}