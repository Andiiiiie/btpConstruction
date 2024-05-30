package com.example.testsessionandspringsecurity.controller;

import com.example.testsessionandspringsecurity.entity.User;
import com.example.testsessionandspringsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "security/login";
    }

    @GetMapping("/")
    public String home() {
        return "global/home";
    }
    @GetMapping("/register")
    public String registerForm(@ModelAttribute("user") User user)
    {
        return "security/register";
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("phoneNumber") String phoneNumber,@RequestParam("address") String address)
    {
        ModelAndView modelAndView=new ModelAndView("security/login");
        try {
            User user=userService.register(email,password,firstName,lastName,phoneNumber,address);
            modelAndView.addObject("user",user);
            modelAndView.addObject("success","User registered, you can log in now");
        }catch (Exception e)
        {
            System.out.println("tato");
            modelAndView.setViewName("security/register");
            modelAndView.addObject("error",e.getMessage());
        }
//        userService.register(request.getParameter("email"),request.getParameter("password"));
        return modelAndView;
    }



    @GetMapping("/add-to-cart")
    public String addToCart(HttpSession httpSession) {
        if (httpSession.getAttribute("cart")==null)
        {
            System.out.println("tsy nisy");
            httpSession.setAttribute("cart", "panier");
        }
        else
        {
            System.out.println("tatob eee"+httpSession.getAttribute("cart"));
        }
        return "redirect:/";
    }


    @GetMapping("/remove-to-cart")
    public String removeToCart(HttpSession httpSession) {
        if (httpSession.getAttribute("cart")!=null)
        {
            System.out.println("tsy nisy");
            httpSession.removeAttribute("cart");
        }

        return "redirect:/";
    }


    @GetMapping("/testAdmin")
    public ModelAndView testAdmin()
    {
        ModelAndView modelAndView=new ModelAndView("global/test");
        modelAndView.addObject("texte","ADMIN");
        return modelAndView;
    }


    @GetMapping("/testUser")
    public ModelAndView testUser()
    {
        ModelAndView modelAndView=new ModelAndView("global/test");
        modelAndView.addObject("texte","USER");

        return modelAndView;
    }

    @GetMapping("/test")
    public ModelAndView test()
    {
        ModelAndView modelAndView=new ModelAndView("pdf/billet");
        modelAndView.addObject("title","USER");
        modelAndView.addObject("content","MET VEEE");

        return modelAndView;
    }

}
