package com.franchy.lil.demo.repository.jpa.adapter;

import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.mapper.CustomerMapper;
import com.franchy.lil.demo.model.CustomerModel;
import com.franchy.lil.demo.repository.jpa.CustomerRepository;
import com.franchy.lil.demo.repository.specification.CustomerSpecification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpaCustomerRepositoryAdapter")
@Profile("prod")
public class JpaCustomerRepositoryAdapter  implements CustomerSpecification {

    private final CustomerRepository customerRepository;

    public JpaCustomerRepositoryAdapter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return this.customerRepository.findById(id)
                .map(CustomerMapper.INSTANCE::toDomain);
    }

    @Override
    public List<Customer> findByActive(boolean active) {
        return this.customerRepository.findByActive(active)
                .stream()
                .map(CustomerMapper.INSTANCE::toDomain)
                .toList();
    }

    @Override
    public List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age) {
        return
                this.customerRepository.findByActiveTrueAndAgeGreaterThanEqual(age)
                        .stream()
                        .map(CustomerMapper.INSTANCE::toDomain)
                        .toList();
    }

    @Override
    public Customer save(Customer customer) {
        CustomerModel customerModel = CustomerMapper.INSTANCE.toModel(customer);
        customerModel = this.customerRepository.save(customerModel);
        return CustomerMapper.INSTANCE.toDomain(customerModel);

    }
}
