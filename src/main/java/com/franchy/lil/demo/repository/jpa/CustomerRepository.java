package com.franchy.lil.demo.repository.jpa;

import com.franchy.lil.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByActive(boolean active);

    List<Customer> findByActiveTrueAndAgeGreaterThanEqual(int age);
}
