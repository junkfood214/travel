package com.example.config;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取语言中的请求参数
        String language = httpServletRequest.getParameter("l");
        //没有就使用默认的
        Locale locale = Locale.getDefault();

        //System.out.println(language);

        //链接中如果携带了参数则解析
        if(language!=null)
        {
            String[] split =  language.split("_");
            locale =  new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}