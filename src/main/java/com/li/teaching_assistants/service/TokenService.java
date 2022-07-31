package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public interface TokenService {
    String createToken(User user, Date date);
    Map<String,Object> verifyToken(HttpServletRequest request,HttpServletResponse response);
}
