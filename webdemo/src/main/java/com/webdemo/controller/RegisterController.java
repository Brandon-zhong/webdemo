package com.webdemo.controller;

import com.webdemo.pojo.Person;
import com.webdemo.pojo.Result;
import com.webdemo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(Person person) {
        Result result = registerService.register(person);
        return result;
    }

}
