package com.franchy.lil.demo.repository.jpa.adapter;

import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.repository.specification.CustomerSpecification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaCustomerRepositoryAdapter")
public class JpaCustomerRepositoryAdapter  implements CustomerSpecification {

    private final CustomerRepository customerRepository;

    public JpaCustomerRepositoryAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return this.customerRepository.findById(id);
    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return this.customerRepository.findByActive(active);
    }

    @Override
    public List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age) {
        return this.customerRepository.findByActiveTrueAndAgeGreaterThanEqual(age);
    }

    @Override
    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }
}
