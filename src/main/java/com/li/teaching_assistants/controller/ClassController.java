package com.li.teaching_assistants.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.dao.ClassDao;
import com.li.teaching_assistants.pojo.*;
import com.li.teaching_assistants.runnable.TeaPTAGroupsSetRunnable;
import com.li.teaching_assistants.service.ClassService;
import com.li.teaching_assistants.service.TokenService;
import com.li.teaching_assistants.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classes")
@Slf4j
public class ClassController {
    @Resource
    private ClassService classService;
    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;

    @RequestMapping("/getMyClass")
    public JSONObject getMyClass(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject  = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verifyToken = tokenService.verifyToken(request, response);
        if((Boolean) verifyToken.get("isOk")){
            List<Classes> classes = classService.getClassByEmail(verifyToken.get("email").toString());
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();

            for(Classes cls : classes){
                Map<String,Object> map = new HashMap<>();
                cls.setClassOwner(userService.getUserByEmail(cls.getClassOwner()).getUserTrueName());
                List<User> unBindUsers = new ArrayList<>();
                List<PTA_Groups> unBindGroup = classService.getUnBindingGroupById(cls.getGroupsId());
                for(PTA_Groups group : unBindGroup){
                    User user = new User();
                    user.setUserTrueName(group.getUserName());
                    unBindUsers.add(user);
                }
                cls.setUnBindUsers(unBindUsers);
                jsonArray.add(cls);
                map.put("label",cls.getClassName());
                map.put("value",cls.getClassId());
                jsonArray1.add(map);
            }
            log.info("查找"+verifyToken.get("email").toString()+"的班级信息成功");
            data.put("class",jsonArray);
            data.put("classesName",jsonArray1);
            meta.put("msg","查找成功");
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

    @RequestMapping("/getSets")
    public JSONObject getSets(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verifyToken = tokenService.verifyToken(request, response);
        JSONArray jsonArray = new JSONArray();
        if((Boolean) verifyToken.get("isOk")){
            if(map.containsKey("value")){
                List<Question_Set> sets = classService.getSetByClassId((int) map.get("value"));
                for(Question_Set question_set : sets){
                    Map<String,Object> set = new HashMap<>();
                    set.put("value",question_set.getSetId());
                    set.put("label",question_set.getSetTitle());
                    jsonArray.add(set);
                }
                log.info("查询题集成功");
                data.put("children",jsonArray);
                meta.put("msg","查询题集成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            log.info("登陆信息已过期，请重新登录");
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }
    @RequestMapping("/getClassRank")
    public JSONObject getClassRank(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> verifyToken = tokenService.verifyToken(request, response);
        ArrayList array = null;
        if ((Boolean) verifyToken.get("isOk")) {
            if (map.containsKey("Array")) {
                array = (ArrayList) map.get("Array");
                if (array.size() == 3 && array.get(1).toString().equals("题目集")) {

                    Question_Set set = classService.getSetById(array.get(2).toString());
                    set.setSetCreator(userService.findByUserPtaId(set.getSetCreator()).getUserTrueName());
                    String string = JSON.toJSONString(set);
                    JSONObject jsonObject1 = JSON.parseObject(string);

                    List<Performance> performances = classService.getPerformancesBySet(array.get(2).toString());
                    int count, rank = 1;
                    JSONArray jsonArray = new JSONArray();
                    for (Performance performance : performances) {
                        performance.setRank(rank++);
                        performance.setPercentageComplete(performance.getPercentageComplete() * 100);
                        count = 0;
                        List<Submit_Record> records = classService.getSubmitRecordsByUserPtaIdAndSetId(performance.getUserId(), array.get(2).toString());

                        for (Submit_Record submit_record : records) {
                            if (submit_record.getSubmitState() == 1)
                                count++;
                        }
                        if(records.size() > 0){
                            DecimalFormat df = new DecimalFormat("#0.00");
                            String format = df.format(((float) count / (float) records.size()) * 100);
                            performance.setPassingRate(format);
                        }else {
                            performance.setPassingRate("未知");
                        }
                        //performance.setPassingRate((double) count / (double) records.size());
                        performance.setPlatform("PTA");
                        jsonArray.add(performance);
                    }
                    log.info("查询题集信息成功");
                    data.put("setInfo", jsonObject1);
                    data.put("performance", jsonArray);
                    meta.put("msg", "查询题集成功");
                    meta.put("isOk", true);
                    jsonObject.put("data", data);
                    jsonObject.put("meta", meta);
                }else {
                    if (map.containsKey("class")) {
                        if (array.size() == 2 && array.get(0).toString().equals("PTA")){
                            List<Performance> performances = classService.getPtaPerformanceByClassId(map.get("class").toString());
                            int rank = 1;
                            JSONArray jsonArray = new JSONArray();
                            for (Performance performance : performances) {
                                performance.setRank(rank++);
                                performance.setPlatform("PTA");
                                jsonArray.add(performance);
                            }
                            log.info("查询题集信息成功");
                            data.put("performance", jsonArray);
                            meta.put("msg", "查询题集成功");
                            meta.put("isOk", true);
                            jsonObject.put("data", data);
                            jsonObject.put("meta", meta);
                        }else if (array.size() == 2 &&  array.get(0).toString().equals("牛客网")){
                            List<Performance> performances = classService.getNowcoderPerformanceByClassId(map.get("class").toString());
                            int rank = 1;
                            JSONArray jsonArray = new JSONArray();
                            for (Performance performance : performances) {
                                performance.setRank(rank++);
                                performance.setPlatform("牛客网");
                                jsonArray.add(performance);
                            }
                            log.info("查询题集信息成功");
                            data.put("performance", jsonArray);
                            meta.put("msg", "查询题集成功");
                            meta.put("isOk", true);
                            jsonObject.put("data", data);
                            jsonObject.put("meta", meta);
                        }else {
                            List<Performance> performances = classService.getAllPerformanceByClassId(map.get("class").toString());
                            int rank = 1;
                            JSONArray jsonArray = new JSONArray();
                            for (Performance performance : performances) {
                                performance.setRank(rank++);
                                performance.setPlatform("ALL");
                                jsonArray.add(performance);
                            }
                            log.info("查询题集信息成功");
                            data.put("performance", jsonArray);
                            meta.put("msg", "查询题集成功");
                            meta.put("isOk", true);
                            jsonObject.put("data", data);
                            jsonObject.put("meta", meta);
                        }
                    }
                }
            }
        }else {
            meta.put("msg", "登陆信息已过期，请重新登录");
            meta.put("isOk", false);
            jsonObject.put("data", data);
            jsonObject.put("meta", meta);
        }
            return jsonObject;
    }

    @RequestMapping("/getGroups")
    public JSONObject getGroups(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request, response);
        if((boolean)verify.get("isOk")){
            List<User_Group> groups = classService.getUserGroupByEmail(verify.get("email").toString());
            JSONArray jsonArray = new JSONArray();
            for(User_Group group : groups){
                Map<String ,Object> map = new HashMap<>();
                map.put("label",group.getGroupName());
                map.put("value",group.getGroupsId());
                jsonArray.add(map);
            }
            log.info("查询"+verify.get("email").toString()+"的用户组成功");
            data.put("groups",jsonArray);
            meta.put("msg", "查询"+verify.get("email").toString()+"的用户组成功");
            meta.put("isOk", true);
            jsonObject.put("data", data);
            jsonObject.put("meta", meta);
        }else {
            meta.put("msg", "登陆信息已过期，请重新登录");
            meta.put("isOk", false);
            jsonObject.put("data", data);
            jsonObject.put("meta", meta);
        }
        return jsonObject;
    }

    @RequestMapping("/createClassByGroupId")
    public JSONObject createClassByGroupId(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request, response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();

            if(map.containsKey("className") && map.containsKey("value")){
                String groupId = map.get("value").toString();
                String className = map.get("className").toString();

                User user = userService.getUserByEmail(email);
                //子线程先爬取用户组成员到数据库中
                TeaPTAGroupsSetRunnable ptaGroupsSetRunnable = new TeaPTAGroupsSetRunnable(user.getUserPtaId(),groupId);
                Thread thread = new Thread(ptaGroupsSetRunnable);
                thread.start();

                Map<String,Object> map1 = new HashMap<>();
                map1.put("className",className);
                map1.put("classOwner",email);
                map1.put("groupId",groupId);
                List<PTA_Groups> group = classService.getBindingGroupById(groupId);
                map1.put("group",group);
                classService.createClass(map1);
                log.info(email+"创建班级"+className+"成功，组id为"+map.get("value").toString());
                meta.put("msg", "创建班级成功");
                meta.put("isOk", true);
                jsonObject.put("data", data);
                jsonObject.put("meta", meta);
            }else {
                meta.put("msg", "未正确入参className和value");
                meta.put("isOk", false);
                jsonObject.put("data", data);
                jsonObject.put("meta", meta);
            }
        }else {
            meta.put("msg", "登陆信息已过期，请重新登录");
            meta.put("isOk", false);
            jsonObject.put("data", data);
            jsonObject.put("meta", meta);
        }
        return jsonObject;
    }
}
