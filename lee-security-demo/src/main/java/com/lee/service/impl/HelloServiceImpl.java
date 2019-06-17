package com.lee.service.impl;

import com.lee.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String Greeting() {
        System.out.println("Greeting");
        return "Greeting";
    }
}
