package com.franchy.lil.demo;

import com.franchy.lil.demo.request.CustomerRequest;
import jakarta.validation.Valid;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping()
    public List<Customer> getCustmers(){
        return this.customerService.getAllCustomer();
    }

    @PostMapping
    public void saveCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());
        this.customerService.saveCustomer(customer);

    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer customerID){
        this.customerService.deleteCustomer(customerID);
    }

    @PutMapping("{customerID}")
    public void updateCustomer(
            @PathVariable("customerID") Integer customerID,
            @RequestBody Map<String, Object> customerMap
            ) throws Exception {
        this.customerService.updateCustomer(customerID, customerMap);
    }
}
