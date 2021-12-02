package com.example;

import com.example.mapper.AdministratorMapper;
import com.example.mapper.UserMapper;
import com.example.pojo.Administrator;
import com.example.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class SpringMybatisApplicationTests {

    @Autowired
    private AdministratorMapper administratorMapper;

    @Test
    public void main()
    {
        System.out.println(administratorMapper.queryAdministratorById("a"));
    }

}
