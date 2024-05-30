package com.example.testsessionandspringsecurity.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Getter
@Setter
public class ErrorCon {

    @GetMapping("/error403")
    public String error1()
    {
        System.out.println("atoooo");
        return "global/error403";
    }
}
