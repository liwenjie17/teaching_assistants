package com.li.teaching_assistants.dao;

import com.li.teaching_assistants.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Update("UPDATE teaching_assistants.user SET  user_image = #{image}" +
            "WHERE user_email = #{email}")
    void updateImage(@Param("image") String image,@Param("email") String email);
    void updateUserInfo(User user);
    @Select("SELECT * FROM teaching_assistants.user WHERE user_email = #{email}")
    User getUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM teaching_assistants.user")
    List<User> getAllUser();
}
