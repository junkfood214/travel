package com.example.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {



    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        CustomShiroFilterFactoryBean  bean = new CustomShiroFilterFactoryBean ();
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();


        /*
            anno:无需认证即可访问
            authc:必须认证才能访问
            user:必须使用记住我才能使用
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色的权限才能访问
        */

        filterChainDefinitionMap.put("/toLogin", "anon");
        filterChainDefinitionMap.put("/user/logout", "logout");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/bus/add", "roles[root]");
        filterChainDefinitionMap.put("/bus/list", "authc");
        filterChainDefinitionMap.put("/bus/toAdd", "roles[root]");
        filterChainDefinitionMap.put("/bus/delete/*", "roles[root]");
        filterChainDefinitionMap.put("/bus/update", "roles[root]");
        filterChainDefinitionMap.put("/bus/search/*", "roles[root]");
        filterChainDefinitionMap.put("/bus/reservations/*", "roles[customer]");
        filterChainDefinitionMap.put("/hotels/add", "roles[root]");
        filterChainDefinitionMap.put("/hotels/list", "authc");
        filterChainDefinitionMap.put("/hotels/toAdd", "roles[root]");
        filterChainDefinitionMap.put("/hotels/delete/*", "roles[root]");
        filterChainDefinitionMap.put("/hotels/update", "roles[root]");
        filterChainDefinitionMap.put("/hotels/search/*", "roles[root]");
        filterChainDefinitionMap.put("/hotels/reservations/*", "roles[customer]");
        filterChainDefinitionMap.put("/flights/add", "roles[root]");
        filterChainDefinitionMap.put("/flights/list", "authc");
        filterChainDefinitionMap.put("/flights/toAdd", "roles[root]");
        filterChainDefinitionMap.put("/flights/delete/*", "roles[root]");
        filterChainDefinitionMap.put("/flights/update", "roles[root]");
        filterChainDefinitionMap.put("/flights/search/*", "roles[root]");
        filterChainDefinitionMap.put("/flights/reservations/*", "roles[customer]");
        filterChainDefinitionMap.put("/customers/add", "roles[root]");
        filterChainDefinitionMap.put("/customers/list", "roles[root]");
        filterChainDefinitionMap.put("/customers/toAdd", "roles[root]");
        filterChainDefinitionMap.put("/customers/delete/*", "roles[root]");
        filterChainDefinitionMap.put("/customers/update", "roles[root]");
        filterChainDefinitionMap.put("/customers/search/*", "roles[root]");
        filterChainDefinitionMap.put("/customers/reservations/*", "roles[customer]");
        filterChainDefinitionMap.put("/customers/person","roles[customer]");
        filterChainDefinitionMap.put("/customers/details/*","roles[root]");
        filterChainDefinitionMap.put("/customers/unsubscribe/**","roles[customer]");
        filterChainDefinitionMap.put("/**","anon");



        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        //登录请求
        bean.setLoginUrl("/flights/list");
        //未授权请求
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }


    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm)
    {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        defaultWebSecurityManager.setSessionManager(mySessionManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public DefaultWebSessionManager mySessionManager(){
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        //将sessionIdUrlRewritingEnabled属性设置成false
        defaultSessionManager.setSessionIdUrlRewritingEnabled(false);
        return defaultSessionManager;
    }




    @Bean(name="userRealm")
    public UserRealm userRealm()
    {
        return new UserRealm();
    }

    @Bean
    public ShiroDialect getShiroDialect()
    {
        return new ShiroDialect();
    }

}
