package com.lowi.authdemo.service.impl;

import com.lowi.authdemo.domain.LoginUser;
import com.lowi.authdemo.domain.ResponseResult;
import com.lowi.authdemo.domain.User;
import com.lowi.authdemo.service.LoginService;
import com.lowi.authdemo.utlis.JwtUtil;
import com.lowi.authdemo.utlis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        //3使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误！");
        }

        //4自己生成jwt给前端
        LoginUser loginUser= (LoginUser)(authenticate.getPrincipal());
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String,String> result=new HashMap(){{
            put("token",jwt);
        }
        };
        //5系统用户相关所有信息放入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        return new ResponseResult(200,"登陆成功",result);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userId);

        return new ResponseResult(200,"退出成功！");
    }
}
