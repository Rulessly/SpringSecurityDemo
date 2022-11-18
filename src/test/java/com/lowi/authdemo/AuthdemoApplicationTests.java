package com.lowi.authdemo;

import com.lowi.authdemo.domain.User;
import com.lowi.authdemo.mapper.MenuMapper;
import com.lowi.authdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class AuthdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {

        List<User> users = userMapper.selectList(null);
        System.out.println(users.get(0));
    }

    @Test
    void dopassword(){
        String result=passwordEncoder.encode("123456");
        System.out.println(result);
    }


    @Autowired
    private MenuMapper mapper;
    @Test
    public void doss(){
        List<String> list = mapper.selectPermsByUserId(1L);
        System.out.println(list);
    }

}
