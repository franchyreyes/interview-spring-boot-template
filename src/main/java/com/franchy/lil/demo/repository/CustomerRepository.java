package com.franchy.lil.demo.repository;

import com.franchy.lil.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
