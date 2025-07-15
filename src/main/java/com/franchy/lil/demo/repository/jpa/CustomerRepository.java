package com.franchy.lil.demo.repository.jpa;

import com.franchy.lil.demo.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {

    List<CustomerModel> findByActive(boolean active);

    List<CustomerModel> findByActiveTrueAndAgeGreaterThanEqual(int age);
}
