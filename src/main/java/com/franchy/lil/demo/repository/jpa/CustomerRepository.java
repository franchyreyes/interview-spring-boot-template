package com.franchy.lil.demo.repository.jpa;

import com.franchy.lil.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByActiveTrue();

    List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age);
}
