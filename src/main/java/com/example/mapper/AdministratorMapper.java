package com.example.mapper;

import com.example.pojo.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdministratorMapper {
    List<Administrator> queryAdministratorList();

    Administrator queryAdministratorById(String name);

    int addAdministrator(Administrator administrator);

    int updateAdministrator(Administrator administrator);

    int deleteAdministrator(String name);
}
