package com.ershou.ershoumarket.service.impl;

import com.ershou.ershoumarket.dao.UserDoMapper;
import com.ershou.ershoumarket.dao.UserPasswordDoMapper;
import com.ershou.ershoumarket.dataobject.UserDo;
import com.ershou.ershoumarket.dataobject.UserPasswordDo;
import com.ershou.ershoumarket.service.UserService;
import com.ershou.ershoumarket.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.Transient;

@Component
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDoMapper userDoMapper;

    @Autowired
    private UserPasswordDoMapper userPasswordDoMapper;

    @Override
    public UserModel getUserById(Integer id)
    {
        //调用udm调用对应用户的dataobject
        UserDo userDo=userDoMapper.selectByPrimaryKey(id);
        if(userDo==null)
        {
            return  null;
        }
        //通过用户id获取对应加密密码
        UserPasswordDo userPasswordDo=userPasswordDoMapper.selectByUserId(userDo.getId());
        return convertFromDataObject(userDo,userPasswordDo);
    }

    @Override
    @Transient
    public void register(UserModel userModel)
    {
        if(userModel!=null)
        {
            if(!StringUtils.isNotEmpty(userModel.getName()))
            {
                UserDo userDo=new UserDo();
                //实现model-》object
                userDo=covertFromModel(userModel);
                userDoMapper.insertSelective(userDo);

                UserPasswordDo userPasswordDo=convertPasswordFromModel(userModel);
                userPasswordDoMapper.insertSelective(userPasswordDo);

                return;
            }
        }
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassWord)
    {
        //通过用户手机获取信息
        UserDo userDo=userDoMapper.selectByTelephone(telephone);
        if(userDo!=null)
        {
            UserPasswordDo userPasswordDo=userPasswordDoMapper.selectByUserId(userDo.getId());
            UserModel userModel=convertFromDataObject(userDo,userPasswordDo);
            if(StringUtils.equals(encrptPassWord,userModel.getEncrptPassword()))
            {
                return userModel;
            }
        }
        //比对密码是否匹配

        return null;
    }

    private UserPasswordDo convertPasswordFromModel(UserModel userModel)
    {
        if(userModel==null)
        {
            return null;
        }
        UserPasswordDo userPasswordDo=new UserPasswordDo();
        userPasswordDo.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDo.setUserId(userModel.getId());
        return userPasswordDo;
    }

    private UserDo covertFromModel(UserModel userModel)
    {
        if(userModel==null)
        {
            return null;
        }
        UserDo userDo=new UserDo();
        BeanUtils.copyProperties(userModel,userDo);
        return userDo;
    }

    private  UserModel convertFromDataObject(UserDo userDo, UserPasswordDo userPasswordDo)
    {
        if(userDo==null)
        {
            return null;
        }
        UserModel userModel =new UserModel();
        BeanUtils.copyProperties(userDo,userModel);
        if(userPasswordDo!=null)
        {
            userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
        }
        return userModel;
    }
}
