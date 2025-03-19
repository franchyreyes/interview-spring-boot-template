package com.franchy.lil.demo.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomApi implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Custom Api Started");
    }
}
