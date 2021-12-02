package com.example.controller;

import com.example.mapper.FlightsMapper;
import com.example.mapper.ReservationsMapper;
import com.example.pojo.Customers;
import com.example.pojo.Flights;
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

@RequestMapping("/flights")
@Controller
public class FlightsController {
    @Autowired
    private FlightsMapper flightsMapper;

    @Autowired
    private ReservationsMapper reservationsMapper;

    @RequestMapping("/add")
    public String add(
            @RequestParam("flightNum") String flightNum,
            @RequestParam("price") int price,
            @RequestParam("numSeats") int numSeats,
            @RequestParam("FromCity") String FromCity,
            @RequestParam("ArivCity") String ArivCity,
            Model model
    )throws Exception
    {
        try{
            Flights flights = new Flights(flightNum,price,numSeats,numSeats,FromCity,ArivCity);
            flightsMapper.addFlights(flights);
            return "redirect:/flights/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","添加失败");
            return "flights/add";
        }
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        Collection<Flights> flightses = flightsMapper.queryFlightsList();
        model.addAttribute("flights",flightses);
        model.addAttribute("msg","");
        return "flights/list";
    }

    @RequestMapping("/search/{flightNum}")
    public String search(
            @PathVariable("flightNum")String flightNum,
            Model model
    )
    {
        Flights flights = flightsMapper.queryFlightsById(flightNum);
        model.addAttribute("flights",flights);
        return "flights/update";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam("flightNum") String flightNum,
            @RequestParam("price") int price,
            @RequestParam("numSeats") int numSeats,
            @RequestParam("numAvail") int numAvail,
            @RequestParam("FromCity") String FromCity,
            @RequestParam("ArivCity") String ArivCity,
            Model model
    )
    {
        try{
            Flights flights = new Flights(flightNum,price,numSeats,numAvail,FromCity,ArivCity);
            flightsMapper.updateFlights(flights);
            return "redirect:/flights/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","修改失败");
            return "flights/update";
        }
    }

    @RequestMapping("/delete/{flightNum}")
    public String delete(
            @PathVariable("flightNum")String flightNum
    )
    {
        try{
            flightsMapper.deleteFlights(flightNum);
        }
        catch (Exception e)
        {
            System.out.println("删除失败");
        }
        return "redirect:/flights/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "flights/add";
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
        reservations.setResvType(1);
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
            return "/flights/list";
        }
    }

}
