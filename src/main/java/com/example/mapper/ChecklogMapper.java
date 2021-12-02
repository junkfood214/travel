package com.example.mapper;

import com.example.pojo.Checklog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Mapper
@Repository
public interface ChecklogMapper {
     List<Checklog> queryById(String name);
     List<Checklog> queryAll();
     int insert(Checklog checklog);
     List<Checklog> queryByTime(Timestamp start, Timestamp end);
}
