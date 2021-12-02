package com.example.mapper;

import com.example.pojo.Customers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CustomersMapper {

    List<Customers> queryCustomersList();

    Customers queryCustomersById(String custName);

    int addCustomers(Customers customers);

    int updateCustomers(Customers customers);

    int deleteCustomers(String custName);

}
