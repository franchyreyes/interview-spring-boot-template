package com.franchy.lil.demo.repository.port;

import com.franchy.lil.demo.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerPort {

    Optional<Customer> findById(Integer id);
    List<Customer> findByActive(boolean active);
    List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age);
    Customer save(Customer customer);

}
