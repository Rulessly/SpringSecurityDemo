package com.lowi.authdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lowi.authdemo.domain.LoginUser;
import com.lowi.authdemo.domain.User;
import com.lowi.authdemo.mapper.MenuMapper;
import com.lowi.authdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户账号或者密码错误");
        }

        //TODO 查询权限信息  一会儿真实查数据库，现在先做一个假的权限
//        List<String> list=new ArrayList<>(Arrays.asList("sayhello","delgoods"));
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user,perms);
    }
}
