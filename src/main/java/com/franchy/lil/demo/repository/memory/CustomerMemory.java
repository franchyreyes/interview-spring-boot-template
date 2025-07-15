package com.franchy.lil.demo.repository.memory;

import com.franchy.lil.demo.model.CustomerModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomerMemory {

    Map<Integer, CustomerModel> customerMap = new HashMap<>();

    public CustomerModel save(CustomerModel customer){
        if(customerMap.isEmpty()){
            customerMap.put(1, customer);
            customer.setId(1);
        }else if(customer.getId() != null){
            if(containsKey(customer.getId())) {
                customerMap.put(customer.getId(), customer);
            }
        }else{
            int customerID = customerMap.size() + 1;
            customer.setId(customerID);
            customerMap.put(customerID, customer);
        }

        return customer;
    }


    public Optional<CustomerModel> findById(int id) {
        return Optional.of(customerMap.get(id));
    }

    public List<CustomerModel> findByActive(boolean active) {
        return this.customerMap
                .values()
                .stream()
                .filter(customer_-> customer_.getActive() == active)
                .toList();

    }

    public List<CustomerModel> findByActiveTrueAndAgeGreaterThanEqual(int id) {
        return this.customerMap
                .values()
                .stream()
                .filter(customer-> customer.getActive() && customer.getAge() >= id )
                .toList();

    }


    public boolean containsKey(int id) {
        return customerMap.containsKey(id);
    }

}
