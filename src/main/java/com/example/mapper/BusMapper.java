package com.example.mapper;

import com.example.pojo.Bus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusMapper {

    List<Bus> queryBusList();

    Bus queryBusById(String location);

    int addBus(Bus bus);

    int updateBus(Bus bus);

    int deleteBus(String location);

}
