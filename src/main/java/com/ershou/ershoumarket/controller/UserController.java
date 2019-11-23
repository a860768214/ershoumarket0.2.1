package com.ershou.ershoumarket.controller;

import com.ershou.ershoumarket.controller.viewobject.UserVO;
import com.ershou.ershoumarket.error.BusinessException;
import com.ershou.ershoumarket.error.EmBusinessError;
import com.ershou.ershoumarket.response.CommenReturnType;
import com.ershou.ershoumarket.service.UserService;
import com.ershou.ershoumarket.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户登录接口
    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType login(@RequestParam(name="telephone")String telephone,
                                  @RequestParam(name="password")String password
                                  ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException
    {
        //如果为空则报错
        if(org.apache.commons.lang3.StringUtils.isEmpty(telephone)||
                org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telephone,this.EncodeByMD5(password));

        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommenReturnType.create(null);
    }



    //otp注册接口
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType register(@RequestParam(name="telephone")String telephone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password
                                     ) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        //验证手机号和对应otpcode
        String inSessionotpCode= (String) this.httpServletRequest.getSession().getAttribute(telephone);
        //多加判空处理
        if(com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionotpCode))
        {
            UserModel userModel=new UserModel();
            userModel.setName(name);
            userModel.setGender(gender);
            userModel.setAge(age);
            userModel.setTelephone(telephone);
            userModel.setRegister_mode("byphone");
            userModel.setEncrptPassword(this.EncodeByMD5("password"));
            userService.register(userModel);
            return CommenReturnType.create(null);
        }
        return null;
    }

    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
//加密字符串
        String newStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }


    @RequestMapping(value = "/getotp",method = RequestMethod.POST,consumes = {"application/x-www-form-urlencoded"})
    @ResponseBody
    public CommenReturnType getOtp(@RequestParam(name="telephone")String telphone)
    {
        //按照规则声称验证码
//        Random random=new Random();
//        int randomInt=random.nextInt(99999);
//        randomInt+=10000;
//        String otpCode=String.valueOf(randomInt);
//        测试用固定验证码
        String otpCode="123456";
        //将OTP验证码和手机号关联
        //使用httpsession绑定手机号和OTPCode
        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        //发送验证码,暂时模拟为打印
        System.out.println("telephone= "+telphone+"OTPCode= "+otpCode);
        return CommenReturnType.create(null);
    }

    @RequestMapping("/get")
//    调用service服务获取对应id的用户对象并返回给前端
    @ResponseBody
    public CommenReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException
    {
        UserModel userModel=userService.getUserById(id);

        if(userModel==null)
        {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
//        删去UseModel的某些成员防止前端看到用户密码
        UserVO userVO=convertFromModel(userModel);

        return CommenReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel)
    {
        if(userModel==null)
        {
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    //定义未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request,Exception ex)
    {
        BusinessException businessException=(BusinessException)ex;
        CommenReturnType commenReturnType=new CommenReturnType();
        commenReturnType.setStatus("fail");
        Map<String,Object> responseData=new HashMap<>();
        responseData.put("errCode",businessException.getErrCode());
        responseData.put("errMsg",businessException.getErrMsg());
        commenReturnType.setData(responseData);
        return commenReturnType;
    }
}
