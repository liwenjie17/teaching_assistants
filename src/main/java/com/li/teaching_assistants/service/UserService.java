package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    User register(User user);
    User findByUserEmail(String  email);
    User findByUserEmailAndUserPassword(String email,String password);
    User updateUserInfo(User user);
    User getUserByEmail(String email);
    void updateImage( String image,String email);
    User findByUserPtaId(String pta_id);
}
