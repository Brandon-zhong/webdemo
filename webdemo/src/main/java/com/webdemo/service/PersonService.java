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
public class PersonService {

    private final static Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    PersonRepository personRepository;

    /**
     * 用户id的唯一性验证
     *
     * @param id 要验证的id
     * @return id的验证结果，true 表示此id可以使用，false 表示此id已经被占用
     */
    public boolean checkPersonOnly(String id) {
        Person person = personRepository.getOne(id);
        if (null == person) {
            log.info("此 ID ：{} 可以使用", id);

            return true;
        }
        return false;
    }

}
