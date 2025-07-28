package com.franchy.lil.demo.repository;


import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.repository.port.CustomerPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("dev")
public class CustomerPortIntegrationDevTest {

    @Autowired
    private CustomerPort customerPort;

    @Test
    void shouldUseMemoryAdapterWhenDevProfileIsActive() {
        // Arrange
        Customer customer = new Customer(10,"Franchy", 28,"franchyreyes@hotmail", true);

        // Act
        Customer saved = customerPort.save(customer);

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Franchy");
    }

    @Test
    void shouldReturnUserId_WhenUserIsValid() {
        // Arrange
        Customer customerArrange = new Customer(10,"Franchy", 28,"franchyreyes@hotmail", true);

        // Act
        Customer saved = customerPort.save(customerArrange);
        Optional<Customer> findCustomer = customerPort.findById(saved.getId());


        // Assert
        findCustomer.ifPresentOrElse(
                customer -> {
                    // Assert
                    assertThat(customer.getId()).isNotNull();
                    assertThat(customer.getName()).isEqualTo("Franchy");
                },
                () -> fail("Customer not found")
        );

    }

    @Test
    void shouldReturnListUser_WhenFindByActiveTrue() {
        // Arrange
        Customer customer1 = new Customer(1, "Franchy", 28, "franchy@example.com", true);
        Customer customer2 = new Customer(2, "Ana", 30, "ana@example.com", true);
        customerPort.save(customer1);
        customerPort.save(customer2);

        // Act
        List<Customer> customers = customerPort.findByActive(true);

        // Assert
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSizeGreaterThanOrEqualTo(2);
        assertThat(customers)
                .extracting(Customer::getName)
                .contains("Franchy", "Ana");
    }

    @Test
    void shouldReturnListUser_WhenFindByActiveTrueAndAgeGreaterThanEqual() {
        // Arrange
        Customer customer1 = new Customer(1, "Franchy", 28, "franchy@example.com", true);
        Customer customer2 = new Customer(2, "Ana", 17, "ana@example.com", true);
        customerPort.save(customer1);
        customerPort.save(customer2);

        // Act
        List<Customer> customers = customerPort.findByActiveTrueAndAgeGreaterThanEqual(18);

        // Assert
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSizeGreaterThanOrEqualTo(1);
        assertThat(customers)
                .extracting(Customer::getName)
                .contains("Franchy");
    }

}

