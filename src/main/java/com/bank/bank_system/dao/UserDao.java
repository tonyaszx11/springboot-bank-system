package com.bank.bank_system.dao;

import com.bank.bank_system.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Select("select user_id, user_authority from user where user_login_name = #{loginName} and user_password = #{password}")
    @ResultMap("userMap")
    User login(@Param("loginName") String loginName, @Param("password") String password);

//    @Select("select user_id from user where user_login_name = #{loginName} and user_password = #{password} " +
//            "and user_authority = 1")
//    @ResultMap("userMap")
//    User adminLogin(@Param("loginName") String loginName, @Param("password") String password);

    @Select("select user_id, user_login_name, user_name, user_gender, user_phone, user_address" +
            ", user_email from user where user_authority = 0")
    @ResultMap("userMap")
    User[] selectAllCustomer();

    @Select("select user_id from user where user_login_name = #{userLoginName}")
    @ResultMap("userMap")
    User selectByUserLoginName(String userLoginName);

    @Select("select user_id, user_login_name, user_name, user_gender, user_phone, user_address" +
            ", user_email, user_authority from user where user_id = #{userId}")
    @ResultMap("userMap")
    User selectByUserId(Integer userId);
    @Insert("insert into user values(null, #{userLoginName}, #{userPassword}, #{userName}, #{userGender}, " +
            "#{userPhone}, #{userAddress}, #{userEmail}, 0)")
    int insert(User user);
//    @Update("update user set user_name = #{userName}, user_gender = #{userGender}, user_phone = #{userPhone}, " +
//            "user_address = #{userAddress}, user_email = #{userEmail} where user_id = #{userId}")
    int update(User user);


}
