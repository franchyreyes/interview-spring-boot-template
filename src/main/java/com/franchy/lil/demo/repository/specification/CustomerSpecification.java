package com.franchy.lil.demo.repository.specification;

import com.franchy.lil.demo.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerSpecification {

    Optional<Customer> findById(Integer id);
    List<Customer> findByActive(boolean active);
    List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age);
    Customer save(Customer customer);

}
