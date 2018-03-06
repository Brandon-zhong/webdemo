package com.webdemo.service;

import com.webdemo.Utils.ResultUtils;
import com.webdemo.enums.ResultEnum;
import com.webdemo.pojo.Person;
import com.webdemo.pojo.Result;
import com.webdemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final static Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    PersonRepository personRepository;

    /**
     * 登录验证
     *
     * @param person 登录要验证的用户参数
     * @return 返回登录验证后的结果
     */
    public Result<Person> login(Person person) {
        Person user = personRepository.findById(person.getId());
        System.out.println(user);
        if (null == user) {
            log.info(ResultEnum.USER_NOT_FOUND.getMsg());
            return ResultUtils.error(ResultEnum.USER_NOT_FOUND.getCode(), ResultEnum.USER_NOT_FOUND.getMsg());
        }
        if (!user.getPwd().equals(person.getPwd())) {
            log.info(ResultEnum.PWD_NOT_MATCH.getMsg());
            return ResultUtils.error(ResultEnum.PWD_NOT_MATCH.getCode(), ResultEnum.PWD_NOT_MATCH.getMsg());
        }
        user.setPwd(null);
        log.info(ResultEnum.LOGIN_SUCESS.getMsg());
        Result<Person> result = new Result<>();
        result.setCode(ResultEnum.LOGIN_SUCESS.getCode());
        result.setMsg(ResultEnum.LOGIN_SUCESS.getMsg());
        result.setData(user);

        return result;
    }

}
