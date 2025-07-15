package com.franchy.lil.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name = "customer")
public class CustomerModel {
    @Id
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence"

    )
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    private String name;


    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age should not be less than 0")
    private Integer age;

    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public CustomerModel(Integer id, String name, String email, Integer age, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.active = active;
    }

    public CustomerModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerModel customer = (CustomerModel) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email,
                customer.email) && Objects.equals(age, customer.age) && Objects.equals(active, customer.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, active);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", age=" + age + "," +
                " active=" + active + '}';
    }
}
