package com.ershou.ershoumarket.service;

import com.ershou.ershoumarket.service.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface UserService
{
    //通过id获得对象
    UserModel getUserById(Integer id);
    void register(UserModel userModel);

    UserModel validateLogin(String telephone, String encrptPassword);
}
