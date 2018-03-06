package com.webdemo.service;

import com.webdemo.exception.UsernameIsExitedException;
import com.webdemo.pojo.Person;
import com.webdemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brandon on 2018/3/3.
 *
 * @author brandon
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("com.webdemo.service.UserService.loadUserByUsername");

        Person person = personRepository.findById(s);
        if (person == null) {
            throw new UsernameIsExitedException("该用户不存在");
        }

        System.err.println("com.webdemo.service.UserService.loadUserByUsername--> " + person);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(person.getAuthorities()));


        // 关于 UserDetailsService 和 User 对象之前的文章已经讲过.
        return new org.springframework.security.core.userdetails.User(person.getId(), person.getPwd(), authorities);
    }
}
