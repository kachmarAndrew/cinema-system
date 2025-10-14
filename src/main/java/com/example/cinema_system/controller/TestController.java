package com.example.cinema_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/helloYaryna")
    public String hello() {
        return "hello test commit Yaryna";
    }
    

    @GetMapping("/helloViktoria")
    public String helloViktoria() {
        return "hello test commit Viktoria";
    }
}
