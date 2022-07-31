package com.li.teaching_assistants.controller;


import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.pojo.All_Rank;
import com.li.teaching_assistants.pojo.User;
import com.li.teaching_assistants.runnable.NowcoderCompletedTopicRunnable;
import com.li.teaching_assistants.runnable.PTALoginRunnable;
import com.li.teaching_assistants.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Value(value = "${server.address}")
    private String Address;
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private SendMailService sendMailService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisService redisService;
    @Resource
    private RankService rankService;
    @RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public JSONObject Register(@RequestBody Map<String,Object> map,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        if(map != null){
            HttpSession session = request.getSession(false);
            if(session == null){
                meta.put("msg","验证码失效或未验证邮箱");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
                return jsonObject;
            }
            String email = (String) session.getAttribute("email");
            String code = (String) session.getAttribute("code");
            log.info(email+"的验证码为"+code);
            if(map.get("email").toString().equals(email) && map.get("verify").toString().equals(code)){
                if(userService.findByUserEmail(map.get("email").toString()) == null){
                    User user = new User();
                    user.setUserEmail(map.get("email").toString());
                    user.setUserName(map.get("name").toString());
                    user.setUserPassword(map.get("pass").toString());
                    user.setUserStatus(0);
                    user.setUserImage("http://"+Address+":8080/images/default.png");
                    userService.register(user);
                    meta.put("msg","success");
                    meta.put("isOk",true);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                    log.info(map.get("email").toString()+"注册成功");
                }else{
                    meta.put("msg","用户已存在");
                    meta.put("isOk",false);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                    log.info(map.get("email").toString()+"用户已存在");
                }
            }else{
                meta.put("msg","验证码错误");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
                log.info("验证码错误");
            }
        }else{
            meta.put("msg","map为空");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
            log.info("map为空");
        }
        return jsonObject;
    }
    @RequestMapping(value = "/verify", produces = "application/json;charset=UTF-8")
    public void Verify(@RequestBody(required = false) Map<String,Object> map,HttpServletRequest request){
        if(map != null){
            if(map.containsKey("email")){
                String code = getVerifyCode(6);
                HttpSession session = request.getSession(true);
                session.setAttribute("email",map.get("email").toString());
                session.setAttribute("code",code);
                log.info("email====="+map.get("email").toString());
                log.info("code====="+code);
                sendMailService.sendMail(map.get("email").toString(),code);
                log.info(map.get("email").toString()+"发送验证码"+code+"成功");
            }else {
                log.info("未输入邮箱");
            }
        }else{
            log.info("未正确入参");
        }
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public JSONObject login(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        if(map != null){
            User user = userService.findByUserEmailAndUserPassword(map.get("email").toString(), map.get("pass").toString());
            User user1 = userService.getUserByEmail(map.get("email").toString());
            if(user == null && user1 != null && user1.getUserPassword().equals(map.get("pass").toString()))
                user = user1;

            if (user != null) {
                String token = "";
                redisService.insertUser(user);
                //记住密码
                if(map.get("remember").toString().equals("true")){
                    Cookie cookie1 = new Cookie("email",user.getUserEmail());
                    Cookie cookie2 = new Cookie("password",user.getUserPassword());
                    Cookie cookie3 = new Cookie("remember","true");
                    cookie1.setMaxAge(24*60*60*14);//14天有效
                    cookie1.setPath("/");

                    cookie2.setMaxAge(24*60*60*14);//14天有效
                    cookie2.setPath("/");

                    cookie3.setMaxAge(24*60*60*14);//14天有效
                    cookie3.setPath("/");

                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                    response.addCookie(cookie3);
                    long nowMillis = System.currentTimeMillis();
                    long validTime = nowMillis + 3600 * 1000 * 24 * 14; //14天有效
                    Date date = new Date(validTime);
                    //创建token
                    token = tokenService.createToken(user,date);
                }else {//不记住密码
                    long nowMillis = System.currentTimeMillis();
                    long validTime = nowMillis + 3600 * 1000 * 2; //2小时有效
                    Date date = new Date(validTime);
                    token = tokenService.createToken(user,date);
                }
                data.put("user_name",user.getUserName());
                data.put("user_mail",user.getUserEmail());
                data.put("user_status",user.getUserStatus());
                data.put("token",token);
                meta.put("msg","登陆成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
                log.info(user.getUserName()+"登陆成功");
            }else{
                meta.put("msg","账号或密码错误！");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
                log.info("账号或密码错误！");
            }
        }else{
            meta.put("msg","入参有误");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
            log.info("入参有误");
        }
        return jsonObject;
    }
    @RequestMapping("/turn")
    public JSONObject Turn(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        if ((boolean)tokenService.verifyToken(request,response).get("isOk")) {
            meta.put("msg","已通过验证");
            meta.put("isOk",true);
            log.info("已通过验证");
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }else {
            meta.put("msg","未通过验证，先登录");
            meta.put("isOk",false);
            log.info("未通过验证");
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("/getUserInfo")
    public JSONObject getUserInfo(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verifyToken = tokenService.verifyToken(request,response);
        if ((boolean)verifyToken.get("isOk")){
            String email = verifyToken.get("email").toString();
            User user = userService.findByUserEmail(email);
            JSONArray rank = rankService.getAllRank(email);

            data.put("icon",user.getUserImage());
            data.put("name",user.getUserName());
            data.put("email",user.getUserEmail());
            data.put("pass",user.getUserPassword());
            data.put("gender",user.getUserGender());
            data.put("birthday",user.getUserBirthday());
            data.put("status",user.getUserStatus());
            if((int)data.put("status",user.getUserStatus()) == 0)
                data.put("student",true);
            else
                data.put("student",false);
            data.put("youke",false);
            data.put("pta_account",user.getUserPtaAccount());
            data.put("pta_password",user.getUserPtaPassword());
            data.put("nowcoder_account",user.getUserNowcoderAccount());
            data.put("nowcoder_password",user.getUserNowcoderPassword());
            data.put("chaoxing_account",user.getUserChaoxingAccount());
            data.put("chaoxing_password",user.getUserChaoxingPassword());
            data.put("unit",user.getUserUnit());
            data.put("true_name",user.getUserTrueName());
            data.put("student_id",user.getUserStudentId());
            data.put("invite_code",user.getUserInviteCode());
            if(rank.size() > 0 && user.getUserStatus() == 0){
                All_Rank score = (All_Rank) rank.get(0);
                data.put("score",score.getScore());
                data.put("title",score.getTitle());
                data.put("rankImage",score.getRankImage());
                data.put("current",score.getCurrent());
                data.put("nextLevel",score.getNextLevel());
                data.put("pta_number",score.getPtaNumber());
                data.put("nowcoder_number",score.getNowcoderNumber());
                data.put("pta_change",user.getUserPtaChange());
                data.put("nowcoder_change",user.getUserNowcoderChange());
                User user1 = new User();
                user1.setUserEmail(email);
                user1.setUserScore(Integer.parseInt(score.getScore()));
                user1.setUserPtaNumber(score.getPtaNumber());
                user1.setUserNowcoderNumber(score.getNowcoderNumber());
                userService.updateUserInfo(user1);
            }

            meta.put("msg","验证成功");
            meta.put("isOk",true);
        }else {
            data.put("youke",true);
            meta.put("msg","验证失败");
            meta.put("isOk",false);
        }
        jsonObject.put("data",data);
        jsonObject.put("meta",meta);
        return jsonObject;
    }

    @RequestMapping(value = "/updateUserInfo", produces = "application/json;charset=UTF-8")
    public JSONObject updateUserInfo(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response) throws InterruptedException, ExecutionException {
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verifyToken = tokenService.verifyToken(request,response);
        User user = new User();
        if ((boolean)verifyToken.get("isOk")){
            String userEmail = verifyToken.get("email").toString();
            //User old_user = userService.getUserByEmail(verifyToken.get("email").toString());
            user.setUserEmail(verifyToken.get("email").toString());
            if(map.containsKey("name") && map.get("name") != null)
                user.setUserName(map.get("name").toString());
            if(map.containsKey("old_pass") && map.get("old_pass") != null){
                if(SecureUtil.md5(userEmail + map.get("old_pass").toString())
                        .equals(userService.getUserByEmail(userEmail).getUserPassword())){
                    if(map.containsKey("new_pass") && map.get("new_pass") != null){
                        user.setUserPassword(SecureUtil.md5(userEmail+map.get("new_pass").toString()));
                    }
                }else {
                    log.info("旧密码错误");
                    meta.put("msg","旧密码错误");
                    meta.put("isOk",false);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                    return jsonObject;
                }
            }
            if(map.containsKey("gender") && map.get("gender") != null)
                user.setUserGender((int)map.get("gender"));
            if(map.containsKey("birthday") && map.get("birthday") != null)
                user.setUserBirthday(map.get("birthday").toString());
            if(map.containsKey("status") && map.get("status") != null)
                user.setUserStatus(Integer.parseInt(map.get("status").toString()));
            if(map.containsKey("pta_account") && map.get("pta_account") != null)
                user.setUserPtaAccount(map.get("pta_account").toString());
            if(map.containsKey("pta_pass") && map.get("pta_pass") != null)
                user.setUserPtaPassword(map.get("pta_pass").toString());
            if(map.containsKey("nowcoder_account") && map.get("nowcoder_account") != null)
                user.setUserNowcoderAccount(map.get("nowcoder_account").toString());
            if(map.containsKey("nowcoder_pass") && map.get("nowcoder_pass") != null)
                user.setUserNowcoderPassword(map.get("nowcoder_pass").toString());
            if(map.containsKey("chaoxing_account") && map.get("chaoxing_account") != null)
                user.setUserChaoxingAccount(map.get("chaoxing_account").toString());
            if(map.containsKey("chaoxing_pass") && map.get("chaoxing_pass") != null)
                user.setUserChaoxingPassword(map.get("chaoxing_pass").toString());
            if(map.containsKey("unit") && map.get("unit") != null)
                user.setUserUnit(map.get("unit").toString());
            if(map.containsKey("true_name") && map.get("true_name") != null)
                user.setUserTrueName(map.get("true_name").toString());
            if(map.containsKey("student_id") && map.get("student_id") != null)
                user.setUserStudentId(map.get("student_id").toString());
            if(map.containsKey("invite_code") && map.get("invite_code") != null)
                user.setUserInviteCode(map.get("invite_code").toString());

            User user1 = userService.updateUserInfo(user);

            if(map.containsKey("pta_account") && map.get("pta_account") != null){
                if(map.containsKey("pta_pass") && map.get("pta_pass") != null){
                    new Thread(new PTALoginRunnable(userEmail,map.get("pta_account").toString(),
                            map.get("pta_pass").toString())).start();
                }
            }

            if(map.containsKey("nowcoder_account") && map.get("nowcoder_account") != null){
                if(map.containsKey("nowcoder_pass") && map.get("nowcoder_pass") != null){
                    new Thread(new NowcoderCompletedTopicRunnable(userEmail,map.get("nowcoder_account").toString(),
                            map.get("nowcoder_pass").toString())).start();
                }
            }

            log.info(verifyToken.get("email").toString()+"用户信息更新成功");

            data.put("icon",user1.getUserImage());
            data.put("name",user1.getUserName());
            data.put("email",user1.getUserEmail());
            data.put("pass",user1.getUserPassword());
            data.put("gender",user1.getUserGender());
            data.put("birthday",user1.getUserBirthday());
            data.put("status",user1.getUserStatus());
            if((int)data.put("status",user1.getUserStatus()) == 0)
                data.put("student",true);
            data.put("youke",false);
            data.put("pta_account",user1.getUserPtaAccount());
            data.put("pta_password",user1.getUserPtaPassword());
            data.put("nowcoder_account",user1.getUserNowcoderAccount());
            data.put("nowcoder_password",user1.getUserNowcoderPassword());
            data.put("chaoxing_account",user1.getUserChaoxingAccount());
            data.put("chaoxing_password",user1.getUserChaoxingPassword());
            data.put("unit",user1.getUserUnit());
            data.put("true_name",user1.getUserTrueName());
            data.put("student_id",user1.getUserStudentId());
            data.put("invite_code",user1.getUserInviteCode());
            JSONArray rank = rankService.getAllRank(userEmail);
            if(rank.size() > 0 && user.getUserStatus() == 0){
                All_Rank score = (All_Rank) rank.get(0);
                data.put("score",score.getScore());
                data.put("title",score.getTitle());
                data.put("rankImage",score.getRankImage());
                data.put("current",score.getCurrent());
                data.put("nextLevel",score.getNextLevel());
                data.put("pta_number",score.getPtaNumber());
                data.put("nowcoder_number",score.getNowcoderNumber());
                data.put("pta_change",user.getUserPtaChange());
                data.put("nowcoder_change",user.getUserNowcoderChange());
                User user2 = new User();
                user2.setUserEmail(userEmail);
                user2.setUserScore(Integer.parseInt(score.getScore()));
                user2.setUserPtaNumber(score.getPtaNumber());
                user2.setUserNowcoderNumber(score.getNowcoderNumber());
                userService.updateUserInfo(user2);
            }

            meta.put("msg",verifyToken.get("email").toString()+"用户信息更新成功");
            meta.put("isOk",true);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }else {
            log.info("登陆信息已过期，请重新登录");
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }
    
    private String getVerifyCode(int n){
        Random r = new Random();
        StringBuffer sb =new StringBuffer();
        for(int i = 0;i < n;i ++){
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
        return sb.toString();
    }
}
