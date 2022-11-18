package com.lowi.authdemo.controller;


import com.lowi.authdemo.domain.ResponseResult;
import com.lowi.authdemo.domain.User;
import com.lowi.authdemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

public class DemoController {


    @Autowired
    private LoginService loginService;


    @GetMapping("hello")
    @PreAuthorize("hasAuthority('dev:pull:code')")
    public String hello(){
        return "hello";
    }


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        return loginService.login(user);
    }


    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return  loginService.logout();
    }


}
