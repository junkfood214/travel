package com.example.controller;

import com.example.mapper.CustomersMapper;
import com.example.mapper.FlightsMapper;
import com.example.mapper.ReservationsMapper;
import com.example.pojo.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.Utlis.generateWay;

import java.util.*;

@RequestMapping("/customers")
@Controller
public class CustomersController {
    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private ReservationsMapper reservationsMapper;

    @Autowired
    private FlightsMapper flightsMapper;

    @RequestMapping("/add")
    public String add(
            @RequestParam("custName") String custName,
            @RequestParam("custID") String custID,
            Model model
    )throws Exception
    {
        try{
            Customers customers = new Customers(custName,custID);
            customersMapper.addCustomers(customers);
            return "redirect:/customers/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","添加失败");
            return "customers/add";
        }
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        Collection<Customers> customers = customersMapper.queryCustomersList();
        model.addAttribute("customers",customers);
        return "customers/list";
    }

    @RequestMapping("/search/{custName}")
    public String search(
            @PathVariable("custName")String custName,
            Model model
    )
    {
        Customers customers = customersMapper.queryCustomersById(custName);
        model.addAttribute("customers",customers);
        return "customers/update";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam("custName") String custName,
            @RequestParam("custID") String custID,
            Model model
    )
    {
        try{
            Customers customers = new Customers(custName,custID);
            customersMapper.updateCustomers(customers);
            return "redirect:/customers/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","修改失败");
            return "customers/update";
        }
    }

    @RequestMapping("/delete/{custName}")
    public String delete(
            @PathVariable("custName")String custName
    )
    {
        try{
            customersMapper.deleteCustomers(custName);
        }
        catch (Exception e)
        {
            System.out.println("删除失败");
        }
        return "redirect:/customers/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "customers/add";
    }

    @RequestMapping("/person")
    public String person(Model model)
    {
        Subject subject = SecurityUtils.getSubject();

        Customers currountCustomer = (Customers) subject.getPrincipal();

        String cusrName = currountCustomer.getCustName();

        List<Reservations> reservationsList =  reservationsMapper.queryReservationsByCustName(cusrName);
        ListIterator<Reservations> listIterator = reservationsList.listIterator();
        List<Way> ways = new ArrayList<Way>();
        List<String> hotels = new ArrayList<String>(),bus = new ArrayList<String>();
        List<Flights> flightsList = new ArrayList<Flights>();
        while(listIterator.hasNext())
        {
            Reservations currountReservation = listIterator.next();
            if(currountReservation.getResvType()==1)
            {
                flightsList.add(flightsMapper.queryFlightsById(currountReservation.getResvContent()));
                Flights wayFlghts = flightsMapper.queryFlightsById(currountReservation.getResvContent());
                Way way = new Way(wayFlghts.getFromCity(),wayFlghts.getArivCity());
                ways.add(way);
            }
            else if(currountReservation.getResvType()==2)
            {
                hotels.add(currountReservation.getResvContent());
            }
            else if(currountReservation.getResvType()==3)
            {
                bus.add(currountReservation.getResvContent());
            }
        }
        List<String> travel = generateWay.generate(ways);
        model.addAttribute("ways",travel);
        model.addAttribute("flights",flightsList);
        model.addAttribute("hotels",hotels);
        model.addAttribute("buses",bus);
        if(travel==null || travel.isEmpty())
        {
            model.addAttribute("error_hotel","终点处可能未订酒店");
            model.addAttribute("error_bus","终点处可能未订公交");
            model.addAttribute("error_travel","路线不完整");
        }
        else
        {
            String lastTravel = travel.get(travel.size()-1);
            if(!hotels.contains(lastTravel))
            {
                model.addAttribute("error_hotel","终点处可能未订酒店");
            }
            if(!bus.contains(lastTravel))
            {
                model.addAttribute("error_bus","终点处可能未订公交");
            }
        }
        return "customers/details";

    }


    @RequestMapping("/details/{custName}")
    public String details(
            @PathVariable("custName") String cusrName,
            Model model
    )
    {
        List<Reservations> reservationsList =  reservationsMapper.queryReservationsByCustName(cusrName);
        ListIterator<Reservations> listIterator = reservationsList.listIterator();
        List<Way> ways = new ArrayList<Way>();
        List<String> hotels = new ArrayList<String>(),bus = new ArrayList<String>();
        List<Flights> flightsList = new ArrayList<Flights>();
        while(listIterator.hasNext())
        {
            Reservations currountReservation = listIterator.next();
            if(currountReservation.getResvType()==1)
            {
                flightsList.add(flightsMapper.queryFlightsById(currountReservation.getResvContent()));
                Flights wayFlghts = flightsMapper.queryFlightsById(currountReservation.getResvContent());
                Way way = new Way(wayFlghts.getFromCity(),wayFlghts.getArivCity());
                ways.add(way);
            }
            else if(currountReservation.getResvType()==2)
            {
                hotels.add(currountReservation.getResvContent());
            }
            else if(currountReservation.getResvType()==3)
            {
                bus.add(currountReservation.getResvContent());
            }
        }
        List<String> travel = generateWay.generate(ways);
        model.addAttribute("ways",travel);
        model.addAttribute("flights",flightsList);
        model.addAttribute("hotels",hotels);
        model.addAttribute("buses",bus);
        if(travel==null || travel.isEmpty())
        {
            model.addAttribute("error_hotel","终点处可能未订酒店");
            model.addAttribute("error_bus","终点处可能未订公交");
            model.addAttribute("error_travel","路线不完整");
        }
        else
        {
            String lastTravel = travel.get(travel.size()-1);
            if(!hotels.contains(lastTravel))
            {
                model.addAttribute("error_hotel","终点处可能未订酒店");
            }
            if(!bus.contains(lastTravel))
            {
                model.addAttribute("error_bus","终点处可能未订公交");
            }
        }
        return "customers/details";
    }

    @RequestMapping("/unsubscribe/flights/{flightNum}")
    public String unsubscribeFlights(
            @PathVariable("flightNum")String flightNum,
            Model model
    )
    {
        Subject subject = SecurityUtils.getSubject();

        Customers currountCustomer = (Customers) subject.getPrincipal();

        Reservations reservations = new Reservations();

        reservations.setCustName(currountCustomer.getCustName());
        reservations.setResvType(1);
        reservations.setResvContent(flightNum);
        reservations.setResvKey("19030500319");

        try {
            reservationsMapper.deleteReservationsByCustomer(reservations);
        }catch (Exception e)
        {
            System.out.println("退订失败");
        }

        return "redirect:/customers/person";

    }

    @RequestMapping("/unsubscribe/hotels/{location}")
    public String unsubscribeHotels(
            @PathVariable("location")String location,
            Model model
    )
    {
        Subject subject = SecurityUtils.getSubject();

        Customers currountCustomer = (Customers) subject.getPrincipal();

        Reservations reservations = new Reservations();

        reservations.setCustName(currountCustomer.getCustName());
        reservations.setResvType(2);
        reservations.setResvContent(location);
        reservations.setResvKey("19030500319");

        try {
            reservationsMapper.deleteReservationsByCustomer(reservations);
        }catch (Exception e)
        {
            System.out.println("退订失败");
        }

        return "redirect:/customers/person";

    }

    @RequestMapping("/unsubscribe/bus/{location}")
    public String unsubscribeBus(
            @PathVariable("location")String location,
            Model model
    )
    {
        Subject subject = SecurityUtils.getSubject();

        Customers currountCustomer = (Customers) subject.getPrincipal();

        Reservations reservations = new Reservations();

        reservations.setCustName(currountCustomer.getCustName());
        reservations.setResvType(3);
        reservations.setResvContent(location);
        reservations.setResvKey("19030500319");

        try {
            reservationsMapper.deleteReservationsByCustomer(reservations);
        }catch (Exception e)
        {
            System.out.println("退订失败");
        }

        return "redirect:/customers/person";

    }

}
