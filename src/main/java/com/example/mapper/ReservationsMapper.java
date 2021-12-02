package com.example.mapper;

import com.example.pojo.Reservations;
import com.example.pojo.Way;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReservationsMapper {
    List<Reservations> queryReservationsList();

    List<Reservations> queryReservationsByCustName(String custName);

    Reservations queryReservationsById(String resvKey);

    int addReservations(Reservations reservations);

    int updateReservations(Reservations reservations);

    int deleteReservations(String resvKey);

    int deleteReservationsByCustomer(Reservations reservations);
}
