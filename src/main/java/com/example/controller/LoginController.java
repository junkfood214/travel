package com.example.controller;

import com.example.mapper.AdministratorMapper;
import com.example.pojo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private AdministratorMapper administratorMapper;

    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model, HttpSession session)
    {
        Administrator administrator = new Administrator();
        administrator = administratorMapper.queryAdministratorById(username);
        if(administrator!=null)
        {
            String pwd = administrator.getPwd();
            if(pwd.equals(password))
            {
                session.setAttribute("loginUser",username);
                return "redirect:/users";
            }
            else
            {
                model.addAttribute("msg","用户名或密码错误！");
                return "login";
            }
        }
        else
        {
            model.addAttribute("msg","用户名不存在！");
            return "login";
        }
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session)
    {
        session.invalidate();
        return "login";
    }

}
