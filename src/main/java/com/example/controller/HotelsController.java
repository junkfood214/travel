package com.example.controller;

import com.example.mapper.HotelsMapper;
import com.example.mapper.ReservationsMapper;
import com.example.pojo.Bus;
import com.example.pojo.Customers;
import com.example.pojo.Hotels;
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

@RequestMapping("/hotels")
@Controller
public class HotelsController {

    @Autowired
    private HotelsMapper hotelsMapper;

    @Autowired
    private ReservationsMapper reservationsMapper;

    @RequestMapping("/add")
    public String add(
            @RequestParam("location") String location,
            @RequestParam("price") int price,
            @RequestParam("numRooms") int numRooms,
            Model model
    )throws Exception
    {
        try{
            Hotels hotels = new Hotels(location,price,numRooms,numRooms);
            hotelsMapper.addHotels(hotels);
            return "redirect:/hotels/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","添加失败");
            return "hotels/add";
        }
    }

    @RequestMapping("/list")
    public String list(Model model)
    {
        Collection<Hotels> hotels = hotelsMapper.queryHotelsList();
        model.addAttribute("hotels",hotels);
        return "hotels/list";
    }

    @RequestMapping("/search/{location}")
    public String search(
            @PathVariable("location")String location,
            Model model
    )
    {
        Hotels hotels = hotelsMapper.queryHotelsById(location);
        model.addAttribute("hotels",hotels);
        return "hotels/update";
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam("location") String location,
            @RequestParam("price") int price,
            @RequestParam("numRooms") int numRooms,
            @RequestParam("numAvail") int numAvail,
            Model model
    )
    {
        try{
            Hotels hotels = new Hotels(location,price,numRooms,numAvail);
            hotelsMapper.updateHotels(hotels);
            return "redirect:/hotels/list";
        }
        catch (Exception e)
        {
            model.addAttribute("msg","修改失败");
            return "hotels/update";
        }
    }

    @RequestMapping("/delete/{location}")
    public String delete(
            @PathVariable("location")String location
    )
    {
        try{
            hotelsMapper.deleteHotels(location);
        }
        catch (Exception e)
        {
            System.out.println("删除失败");
        }
        return "redirect:/hotels/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "hotels/add";
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
        reservations.setResvType(2);
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
            return "/hotels/list";
        }
    }

}
