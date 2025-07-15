package com.franchy.lil.demo.domain;

public class Customer {


     private Integer id;
     private String name;
     private Integer age;
     private String email;
     private Boolean active;

    public Customer(){

    }

    public Customer(Integer id, String name, Integer age, String email, Boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
