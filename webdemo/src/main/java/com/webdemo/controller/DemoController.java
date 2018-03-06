package com.webdemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class DemoController {

    @RequestMapping("/login")
    @Secured("ROLE_MANAGE")
    public String hello(HttpServletResponse response) {
//        Cookie cookie = new Cookie("Authorization", "Bearer" + UUID.randomUUID().toString());
//        response.addCookie(cookie);
        return "/login";
    }

    @RequestMapping("/welcome")
//    @Secured("ROLE_USE")
    public String welcome() {
        return "/welcome";
    }
}
