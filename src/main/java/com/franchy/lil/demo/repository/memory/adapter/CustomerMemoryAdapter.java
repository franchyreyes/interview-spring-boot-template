package com.franchy.lil.demo.repository.memory.adapter;

import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.mapper.CustomerMapper;
import com.franchy.lil.demo.model.CustomerModel;
import com.franchy.lil.demo.repository.memory.CustomerMemory;
import com.franchy.lil.demo.repository.specification.CustomerSpecification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("customerMemoryAdapterRepo")
public class CustomerMemoryAdapter implements CustomerSpecification {

    private final CustomerMemory customerMemory;

    public CustomerMemoryAdapter(CustomerMemory customerMemory){
        this.customerMemory = customerMemory;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return this.customerMemory
                .findById(id)
                .map(CustomerMapper.INSTANCE::toDomain);
    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return this.customerMemory.findByActive(active).stream()
                .map(CustomerMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age) {
        return this.customerMemory.findByActiveTrueAndAgeGreaterThanEqual(age).stream()
                .map(CustomerMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public Customer save(Customer customer) {
        CustomerModel model = CustomerMapper.INSTANCE.toModel(customer);
        return CustomerMapper.INSTANCE.toDomain(this.customerMemory.save(model));
    }
}
