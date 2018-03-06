package com.webdemo.controller;

import com.webdemo.Utils.Utils;
import com.webdemo.pojo.Person;
import com.webdemo.pojo.Result;
import com.webdemo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;


    private final static Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/security_login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(Person person, HttpServletRequest request, HttpServletResponse response) {
//        Cookie[] cookies = request.getCookies();
//        String header = Utils.getCookieValueByName(cookies, "Authorization");
//
//        log.info("com.webdemo.controller.LoginController.login-->{}", person.toString());
//        log.info("request header-->" + header);
        log.info("com.webdemo.controller.LoginController.login");
        Result<Person> result = loginService.login(person);
        if (result.getCode() == 200) {
            request.getSession().setAttribute("user", result.getData());
            result.setData(null);
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout(String id, HttpServletRequest request) {
        request.getSession().removeAttribute(id);
        return "true";
    }

}
