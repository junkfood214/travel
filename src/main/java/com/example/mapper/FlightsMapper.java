package com.example.mapper;

import com.example.pojo.Flights;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FlightsMapper {

    List<Flights> queryFlightsList();

    Flights queryFlightsById(String flightNum);

    int addFlights(Flights flights);

    int updateFlights(Flights flights);

    int deleteFlights(String flightNum);

}
