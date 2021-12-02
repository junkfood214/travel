package com.example.controller;

import com.example.mapper.ReservationsMapper;
import com.example.pojo.Customers;
import com.example.pojo.Reservations;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Controller
@RequestMapping("/reservations")
public class ReservationsController {

    @Autowired
    private ReservationsMapper reservationsMapper;

    @RequestMapping("/add")
    public String add(
            @RequestParam("resvKey") String resvKey,
            @RequestParam("resvType") int resvType,
            @RequestParam("custName") String custName,
            @RequestParam("resvContent") String resvContent,
            Model model
    )throws Exception
    {
        try{
            Reservations reservations = new Reservations(custName,resvType,resvKey,resvContent);
            reservationsMapper.addReservations(reservations);
            return "redirect:/reservations/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","添加失败");
            return "reservations/add";
        }
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        Collection<Reservations> reservations = reservationsMapper.queryReservationsList();
        model.addAttribute("reservations",reservations);
        return "reservations/list";
    }

    @RequestMapping("/search/{resvKey}")
    public String search(
            @PathVariable("resvKey")String resvKey,
            Model model
    )
    {
        Reservations reservations = reservationsMapper.queryReservationsById(resvKey);
        model.addAttribute("reservations",reservations);
        return "reservations/update";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam("resvKey") String resvKey,
            @RequestParam("custName") String custName,
            @RequestParam("resvType") int resvType,
            @RequestParam("resvContent") String resvContent,
            Model model
    )
    {
        try{
            Reservations reservations = new Reservations(custName,resvType,resvKey,resvContent);
            reservationsMapper.updateReservations(reservations);
            return "redirect:/reservations/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","修改失败");
            return "reservations/update";
        }
    }

    @RequestMapping("/delete/{resvKey}")
    public String delete(
            @PathVariable("resvKey")String resvKey
    )
    {
        try{
            reservationsMapper.deleteReservations(resvKey);
        }
        catch (Exception e)
        {
            System.out.println("删除失败");
        }
        return "redirect:/reservations/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "reservations/add";
    }

}
