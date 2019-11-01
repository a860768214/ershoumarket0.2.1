package com.ershou.ershoumarket.dao;

import com.ershou.ershoumarket.dataobject.UserDo;
import org.springframework.stereotype.Component;

@Component
public interface UserDoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    int insert(UserDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    int insertSelective(UserDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    UserDo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    int updateByPrimaryKeySelective(UserDo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Sat Oct 26 15:14:04 GMT+08:00 2019
     */
    int updateByPrimaryKey(UserDo record);

    UserDo selectByTelephone(String telephone);
}