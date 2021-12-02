package com.example.mapper;

import com.example.pojo.Hotels;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelsMapper {

    List<Hotels> queryHotelsList();

    Hotels queryHotelsById(String location);

    int addHotels(Hotels hotels);

    int updateHotels(Hotels hotels);

    int deleteHotels(String location);

}

