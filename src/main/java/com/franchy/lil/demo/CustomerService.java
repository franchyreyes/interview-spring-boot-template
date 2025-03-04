package com.franchy.lil.demo;

import com.franchy.lil.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer(){
        return this.customerRepository.findAll();
    }

    public void saveCustomer(Customer customer){
        this.customerRepository.save(customer);
    }

    public void deleteCustomer(Integer customerID){
        this.customerRepository.deleteById(customerID);
    }

    public void updateCustomer(Integer customerID, Map<String, Object> customerMap) throws ResourceNotFoundException {
        Customer customer =
                this.customerRepository.findById(customerID)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Customer not found with id " + customerID));;
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
               }
            });
           this.customerRepository.save(customer);
    }
}