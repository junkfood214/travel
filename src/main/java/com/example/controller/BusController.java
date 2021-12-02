package com.example.controller;

import com.example.mapper.BusMapper;
import com.example.mapper.ReservationsMapper;
import com.example.pojo.Bus;
import com.example.pojo.Customers;
import com.example.pojo.Reservations;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.UUID;

@RequestMapping("/bus")
@Controller
public class BusController {
    @Autowired
    private BusMapper busMapper;

    @Autowired
    private ReservationsMapper reservationsMapper;

    @RequestMapping("/add")
    public String add(
            @RequestParam("location") String location,
            @RequestParam("price") int price,
            @RequestParam("numBus") int numBus,
            Model model
    )throws Exception
    {
        try{
            Bus bus = new Bus(location,price,numBus,numBus);
            busMapper.addBus(bus);
            return "redirect:/bus/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","添加失败");
            return "bus/add";
        }
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        Collection<Bus> buses = busMapper.queryBusList();
        model.addAttribute("buses",buses);
        return "bus/list";
    }

    @RequestMapping("/search/{location}")
    public String search(
            @PathVariable("location")String location,
            Model model
    )
    {
        Bus bus = busMapper.queryBusById(location);
        model.addAttribute("bus",bus);
        return "bus/update";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam("location") String location,
            @RequestParam("price") int price,
            @RequestParam("numBus") int numBus,
            @RequestParam("numAvail") int numAvail,
            Model model
    )
    {
        try{
            Bus bus = new Bus(location,price,numBus,numAvail);
            busMapper.updateBus(bus);
            return "redirect:/bus/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","修改失败");
            return "bus/update";
        }
    }

    @RequestMapping("/delete/{location}")
    public String delete(
            @PathVariable("location")String location
    )
    {
        try{
            busMapper.deleteBus(location);
        }
        catch (Exception e)
        {
            System.out.println("删除失败");
        }
        return "redirect:/bus/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "bus/add";
    }

    @RequestMapping("/reservations/{resvContent}")
    public String reservations(
            @PathVariable("resvContent")String resvContent,
            Model model
    )
    {
        Subject subject = SecurityUtils.getSubject();

        Customers currountCustomer = (Customers) subject.getPrincipal();

        Reservations reservations = new Reservations();

        reservations.setCustName(currountCustomer.getCustName());
        reservations.setResvType(3);
        reservations.setResvContent(resvContent);
        reservations.setResvKey(System.currentTimeMillis() + UUID.randomUUID().toString().replace(".", "").substring(0, 6)  );

        try
        {
            reservationsMapper.addReservations(reservations);
            return "redirect:/customers/person";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","预订失败");
            return "/bus/list";
        }
    }

}
