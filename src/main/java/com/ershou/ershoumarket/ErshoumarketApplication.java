package com.ershou.ershoumarket;

import com.ershou.ershoumarket.dao.UserDoMapper;
import com.ershou.ershoumarket.dataobject.UserDo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = {"com.ershou.ershoumarket"})
@MapperScan(value = {"com.ershou.ershoumarket.dao","com.ershou.ershoumarket.service.UserService"})
public class ErshoumarketApplication
{
//    autowired的类要在类的顶端加注解@Component才能被autowired注入
    @Autowired
    private UserDoMapper userDoMapper;

    @RequestMapping("/")
    public  String home()
    {
        //测试
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
