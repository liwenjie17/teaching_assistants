package com.li.teaching_assistants.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.li.teaching_assistants.pojo.User;
import com.li.teaching_assistants.service.TokenService;
import com.li.teaching_assistants.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    UserService userService;

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public String createToken(User user,Date date) {
        String token = "";
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long aging = date.getTime();
        token = JWT.create()
                .withAudience(user.getUserEmail()+"")
                .withSubject("TeachersAndStudents")
                .withIssuedAt(now)                          //发行时间
                .withNotBefore(now)                         //有效时间从现在开始
                .withExpiresAt(new Date(aging))   //有效时长
                .sign(Algorithm.HMAC256(user.getUserPassword()));

        return token;
    }

    @Override
    public Map<String,Object> verifyToken(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        //从请求头里面取出token
        String token  = request.getHeader("Authorization");
        log.info("请求头："+token);
        //执行认证
        if(token == null){
            logger.info("无token，请重新登录");
            map.put("msg","无token，请重新登录");
            map.put("isOk",false);
            return map;
        }
        String userEmail;
        try {
            userEmail = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException jwtD){
            logger.info("token解析错误");
            map.put("msg","token解析错误");
            map.put("isOk",false);
            return map;
        }
        User user = userService.getUserByEmail(userEmail);
        if(user == null){
            logger.info("用户不存在，请重新登录");
            map.put("msg","用户不存在，请重新登录");
            map.put("isOk",false);
            return map;
        }else {
            logger.info("获得用户"+userEmail+"的信息");
            map.put("email",user.getUserEmail());
            map.put("msg","获得用户"+userEmail+"的信息");
            map.put("isOk",true);
        }
        //验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
        }catch (JWTVerificationException jwtV){
            logger.info("验证token失败");
            map.put("msg","验证token失败");
            map.put("isOk",false);
            return map;
        }

        return map;
    }
}
