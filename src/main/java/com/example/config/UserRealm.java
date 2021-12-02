package com.example.config;

import com.example.mapper.AdministratorMapper;
import com.example.mapper.CustomersMapper;
import com.example.pojo.Administrator;
import com.example.pojo.Customers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    private CustomersMapper customersMapper;

//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        Administrator administrator = administratorMapper.queryAdministratorById(userToken.getUsername());

        if(administrator==null)
        {
            Customers customers = customersMapper.queryCustomersById(userToken.getUsername());
            if(customers==null)
            {
                return null;
            }
            else
            {
                return new SimpleAuthenticationInfo(customers,customers.getCustID(),"");
            }
        }

        return new SimpleAuthenticationInfo(administrator,administrator.getPwd(),"");
    }
//授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();
        try {
            Administrator currentAdministrator = (Administrator) subject.getPrincipal();
            //设置当前用户对应权限
            info.addStringPermission(currentAdministrator.getPerms());
            info.addRole(currentAdministrator.getRole());
        }catch (Exception e)
        {
            Customers currentCustomers = (Customers) subject.getPrincipal();
            info.addRole("customer");
        }
        System.out.println(info.getRoles());
        return info;
    }
}
