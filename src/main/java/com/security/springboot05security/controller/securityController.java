package com.security.springboot05security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//注意不是@RestController
@Controller
public class securityController {

    //使用thymeleaf return要返回html的名称

    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/vip")
    public String vip(){
        return "vip";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
