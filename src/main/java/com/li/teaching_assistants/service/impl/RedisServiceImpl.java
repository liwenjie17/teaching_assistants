package com.li.teaching_assistants.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.pojo.PTA_Ques;
import com.li.teaching_assistants.pojo.Submission_Records;
import com.li.teaching_assistants.pojo.User;
import com.li.teaching_assistants.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void insertUser(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(user.getUserEmail(),user);
        String string = jsonObject.toJSONString();
        //System.out.println(string);
        stringRedisTemplate.opsForValue().set(user.getUserEmail(),string,14, TimeUnit.DAYS);
        log.info(user.getUserEmail()+"已存入缓存");
    }

    @Override
    public User getUser(String email) {

        String userStr = stringRedisTemplate.opsForValue().get(email);
        //System.out.println(userStr);

        if(userStr != null){
            JSONObject jsonObject = JSON.parseObject(userStr);
            Object user = jsonObject.get(email);
            log.info("已获取"+email+"缓存信息");
            //System.out.println(user1);
            return JSONObject.parseObject(user.toString(), User.class);
        }else
            return null;
    }

    @Override
    public void deleteUser(String email) {
        stringRedisTemplate.delete(email);
    }

    @Override
    public void insertCollectQues(String email, Submission_Records records) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(email+records.getQuesId(),records);
        String string = jsonObject.toJSONString();
        stringRedisTemplate.opsForValue().set(email+records.getQuesId(),string,14, TimeUnit.DAYS);
        log.info(email+"的"+records.getQuesId()+"已存入缓存");
    }

    @Override
    public Submission_Records getCollectQues(String email,String ques_id) {
        String pta_ques = stringRedisTemplate.opsForValue().get(email+ques_id);

        if(pta_ques != null){
            JSONObject jsonObject = JSON.parseObject(pta_ques);
            Object pta = jsonObject.get(email+ques_id);
            log.info("已获取"+email+"的"+ques_id+"缓存信息");
            //System.out.println(user1);
            return JSONObject.parseObject(pta.toString(), Submission_Records.class);
        }else
            return null;
    }

    @Override
    public void deleteCollectQues(String email,String ques_id) {
        stringRedisTemplate.delete(email+ques_id);
    }
}
