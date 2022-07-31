package com.li.teaching_assistants.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.dao.DataDao;
import com.li.teaching_assistants.pojo.TeacherCollectedRecord;
import com.li.teaching_assistants.pojo.TeacherCollectedRecordInitialize;
import com.li.teaching_assistants.pojo.University;
import com.li.teaching_assistants.service.DataService;
import com.li.teaching_assistants.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/data")
public class DataController {
    @Resource
    private DataService dataService;
    @Resource
    private TokenService tokenService;

    @RequestMapping("/getUniversityName")
    public JSONObject getUniversityName(){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        List<University> university = dataService.getUniversity();
        if(university.isEmpty()){
            meta.put("msg","查询失败");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
            return jsonObject;
        }

        JSONArray jsonArray = new JSONArray();
        for(University uni : university){
            jsonArray.add(uni.getUniversityName());
        }
        data.put("universityName",jsonArray);
        meta.put("msg","查询成功，返回全国大学名称");
        meta.put("isOk",true);
        jsonObject.put("data",data);
        jsonObject.put("meta",meta);
        return jsonObject;
    }

    @RequestMapping("/updateTeacherCollectedRecords")
    public JSONObject updateTeacherCollectedRecords(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request, response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();
            if(map.containsKey("records")){
                System.out.println(map.get("records").toString());
                ArrayList jsonArray = (ArrayList) map.get("records");
                //JSONObject jsonObject1 = JSONObject.parseObject(map.get("records").toString());
                System.out.println(jsonArray);
                JSONArray recordsToJSONArray = JSONArray.parseArray(map.get("records").toString());
                String recordToJSONString = recordsToJSONArray.toJSONString();
                TeacherCollectedRecord record = new TeacherCollectedRecord();
                record.setId(email+"Coll.");
                record.setEmail(email);
                record.setRecords(recordToJSONString);
                dataService.updateRecords(record);
                log.info(email+"修改收藏记录成功");
                meta.put("msg", email+"修改收藏记录成功");
                meta.put("isOk", true);
                jsonObject.put("data", data);
                jsonObject.put("meta", meta);
            }else {
                meta.put("msg", "未正确入参records");
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

    @RequestMapping("/getTeacherCollectedRecords")
    public JSONObject getTeacherCollectedRecords(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request, response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();
            TeacherCollectedRecord records = dataService.getRecordsById(email + "Coll.");
            if(records != null){
                JSONArray jsonArray = JSONArray.parseArray(records.getRecords());
                log.info(email+"获取收藏记录成功");
                data.put("records",jsonArray);
                meta.put("msg", email+"获取收藏记录成功");
                meta.put("isOk", true);
                jsonObject.put("data", data);
                jsonObject.put("meta", meta);
            }else {
                JSONArray jsonArray = new JSONArray();

                TeacherCollectedRecordInitialize initialize = new TeacherCollectedRecordInitialize();
                initialize.setId("1");
                initialize.setLabel("欢迎记录");
                initialize.setLevel(1);
                initialize.setEdit(false);
                initialize.setValueEdit(false);
                initialize.setValue("### 题目内容:\n" +
                        "在此输入题目内容……\n" +
                        "\n" +
                        "### 输入格式:\n" +
                        "```in\n" +
                        "12-34\n" +
                        "```\n" +
                        "### 输出格式:\n" +
                        "```in\n" +
                        "12-34\n" +
                        "```\n" +
                        "## 题目思路:\n" +
                        "在此写题目思路\n" +
                        "\n" +
                        "### 题目代码:\n" +
                        "```c++\n" +
                        "#include<stdio.h>\n" +
                        "int main()\n" +
                        "{\n" +
                        "\tprintf(\"Hello World!\\n\");\n" +
                        " \treturn 0;\n" +
                        "}\n" +
                        "```\n" +
                        "#### 如果还有题目思路二 可以复制前面的粘贴到下面");

                jsonArray.add(initialize);
                String InitRecords = jsonArray.toJSONString();

                TeacherCollectedRecord record = new TeacherCollectedRecord();
                record.setId(email+"Coll.");
                record.setEmail(email);
                record.setRecords(InitRecords);
                dataService.updateRecords(record);
                //todo 解决字符串转义符的问题

                log.info(email+"收藏记录初始化成功");
                data.put("records",jsonArray);
                meta.put("msg", email+"收藏记录初始化成功");
                meta.put("isOk", true);
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
