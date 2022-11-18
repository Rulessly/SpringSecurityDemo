package com.lowi.authdemo.service;

import com.lowi.authdemo.domain.ResponseResult;
import com.lowi.authdemo.domain.User;

public interface LoginService {

    public ResponseResult login(User user);

    ResponseResult logout();
}
