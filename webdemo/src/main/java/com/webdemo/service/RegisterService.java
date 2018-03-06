package com.webdemo.service;

import com.webdemo.Utils.ResultUtils;
import com.webdemo.pojo.Person;
import com.webdemo.pojo.Result;
import com.webdemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final static Logger log = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;


    /**
     * 用户注册
     *
     * @param person 要注册的用户信息
     * @return 返回注册结果 200 表示 注册成功，404 表示注册失败
     */
    public Result register(Person person) {

        boolean bool = personService.checkPersonOnly(person.getId());
        if (!bool) {
            log.info("注册失败,用户id ：{} 已经被占用", person.getId());
            return ResultUtils.error(404, "注册失败,用户id已经被占用");
        }

        Person save = personRepository.save(person);
        if (null == save) {
            log.info("用户注册失败");
            return ResultUtils.error(404,"用户注册失败");
        }
        Result result = new Result();
        result.setCode(200);
        result.setMsg("注册成功");

        return result;
    }


}
