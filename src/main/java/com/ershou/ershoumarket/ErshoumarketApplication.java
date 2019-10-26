package com.ershou.ershoumarket;

import com.ershou.ershoumarket.dao.UserDoMapper;
import com.ershou.ershoumarket.dataobject.UserDo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("ALL")
@RestController
@SpringBootApplication(scanBasePackages = {"com.ershou.ershoumarket"})
@Component
@MapperScan("com.ershou.ershoumarket.dao")
public class ErshoumarketApplication
{
    @Autowired
    private UserDoMapper userDoMapper;

    @RequestMapping("/")
    public  String home()
    {
        UserDo userDo=userDoMapper.selectByPrimaryKey(1);
        if(userDo==null)
        {
            return "用户对象不存在";
        }
        else
        {
            return userDo.getName();
        }
    }

    public static void main(String[] args)
    {
        SpringApplication.run(ErshoumarketApplication.class, args);
    }

}
