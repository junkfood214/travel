package com.example.controller;

import com.example.mapper.CustomersMapper;
import com.example.pojo.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private CustomersMapper customersMapper;

    @RequestMapping("/register")
    public String registerCustomer(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model
    )
    {
        Customers customers = new Customers();
        customers.setCustName(username);
        customers.setCustID(password);
        try
        {
            customersMapper.addCustomers(customers);
            return "redirect:/toLogin";
        }catch (Exception e)
        {
            model.addAttribute("msg","注册失败");
            return "register";
        }
    }

    @RequestMapping("/toRegister")
    public String toRegister()
    {
        return "register";
    }
}
