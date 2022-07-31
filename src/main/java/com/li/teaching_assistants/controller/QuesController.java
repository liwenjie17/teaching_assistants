package com.li.teaching_assistants.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.pojo.*;
import com.li.teaching_assistants.runnable.NowcoderItemContentRunnable;
import com.li.teaching_assistants.runnable.StuPTAItemContentRunnable;
import com.li.teaching_assistants.service.QuesService;
import com.li.teaching_assistants.service.RedisService;
import com.li.teaching_assistants.service.TokenService;
import com.li.teaching_assistants.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ques")
@Slf4j
public class QuesController {
    @Resource
    private QuesService quesService;
    @Resource
    private TokenService tokenService;
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;
    @RequestMapping("/getAllQuesBank")
    public JSONObject getAllQuesBank(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("QuesBank",quesService.findAll());
        return jsonObject;
    }

    @RequestMapping("/getSet")
    public JSONObject getSet(HttpServletRequest request,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            List<Question_Set> label = quesService.getSetByEmail(verify.get("email").toString());
            JSONArray jsonArray = new JSONArray();
            for(Question_Set set : label){
                Map<String,Object> map = new HashMap<>();
                map.put("value",set.getSetId());
                map.put("label",set.getSetTitle());
                jsonArray.add(map);
            }
            data.put("children",jsonArray);
            meta.put("msg","查询用户所学题集成功");
            meta.put("isOk",true);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("/getPersonalQues")
    public JSONObject getPersonalQues(@RequestBody Map<String,Object> map, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();
            if(map.containsKey("Array")){
                ArrayList array = (ArrayList) map.get("Array");
                if(array.get(0).toString().equals("PTA")){
                    List<PTA_Ques> ptaQues = null;
                    if(array.size() > 1)
                        ptaQues = quesService.getPTAQuesByEmailAndSetId(email,array.get(2).toString());
                    else
                        ptaQues = quesService.getPTAQuesByEmailAndSetId(email,null);
                    JSONArray PTAjsonArray = new JSONArray();
                    PTAjsonArray.addAll(ptaQues);
                    log.info("查询"+email+"的题目成功");
                    data.put("Ques",PTAjsonArray);
                    data.put("flag",1);     // 标志为1，返回PTA题目
                    meta.put("msg","查询"+email+"的题目成功");
                    meta.put("isOk",true);
                    jsonObject.put("data",data);
                    jsonObject.put("meta",meta);
                }else if(array.get(0).toString().equals("牛客网")){
                    if(array.size() == 2){
                        List<NowCoder_Ques> nowCoder_ques = quesService.getNowCoderQuesByEmailAndType(email, array.get(1).toString());
                        JSONArray NowcoderJsonArray = new JSONArray();
                        NowcoderJsonArray.addAll(nowCoder_ques);
                        log.info("查询"+email+"标签为"+array.get(1).toString()+"的题目成功");
                        data.put("Ques",NowcoderJsonArray);
                        data.put("flag",2);     // 标志为2，返回牛客网题目
                        meta.put("msg","查询"+email+"标签为"+array.get(1).toString()+"的题目成功");
                        meta.put("isOk",true);
                        jsonObject.put("data",data);
                        jsonObject.put("meta",meta);
                    }else if(array.size() == 3){
                        List<NowCoder_Ques> nowCoder_ques = quesService.getNowCoderQuesByEmailAndLabel(email,array.get(2).toString());
                        JSONArray NowcoderJsonArray = new JSONArray();
                        NowcoderJsonArray.addAll(nowCoder_ques);
                        log.info("查询"+email+"标签为"+array.get(2).toString()+"的题目成功");
                        data.put("Ques",NowcoderJsonArray);
                        data.put("flag",2);     // 标志为2，返回牛客网题目
                        meta.put("msg","查询"+email+"标签为"+array.get(2).toString()+"的题目成功");
                        meta.put("isOk",true);
                        jsonObject.put("data",data);
                        jsonObject.put("meta",meta);
                    }
                }
            }else {
                meta.put("msg","未正确入参Array");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("/collectTopic")
    public JSONObject collectTopic(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();
            if(map.containsKey("ques_id") && map.containsKey("setId")){
                User user = userService.getUserByEmail(email);
                Submission_Records records = redisService.getCollectQues(email, map.get("ques_id").toString());
                if(records == null){
                    //爬取收藏题目信息
                    new Thread(new StuPTAItemContentRunnable(email,user.getUserPtaId(),map.get("setId").toString(),
                            map.get("ques_id").toString())).start();
                }else {
                    Submission_Records records1 = quesService.getCollectTopicByEmailAndQuesId(email, map.get("ques_id").toString());
                    if(records1 == null)
                        //存进数据库
                        quesService.insertCollectTopicWithEmailAndQuesId(records);
                }
                meta.put("msg",email+"收藏"+map.get("ques_id").toString()+"题目成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }else if(map.containsKey("quesNo")){
                User user = userService.getUserByEmail(email);
                NowCoder_Ques nowCoder_ques = quesService.findByQuesNo(map.get("quesNo").toString());
                Submission_Records records = redisService.getCollectQues(email, nowCoder_ques.getQuesId());
                if(records == null){
                    new Thread(new NowcoderItemContentRunnable(email,
                            user.getUserNowcoderId(),nowCoder_ques.getSubmitId(),
                            nowCoder_ques.getQuesId())).start();
                }else {
                    Submission_Records records1 = quesService.getCollectTopicByEmailAndQuesId(email, nowCoder_ques.getQuesId());
                    if(records1 == null)
                        //存进数据库
                        quesService.insertCollectTopicWithEmailAndQuesId(records);
                }
                meta.put("msg",email+"收藏"+map.get("ques_id").toString()+"题目成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }else {
                meta.put("msg","未正确入参");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("/getCollectTopic")
    public JSONObject getCollectTopic(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            String email = verify.get("email").toString();
            List<Submission_Records> collectTopicByEmail = quesService.getCollectTopicByEmail(email);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(collectTopicByEmail);
            log.info("获取"+email+"的题目收藏记录成功");
            data.put("collectTopic",jsonArray);
            meta.put("msg","获取"+email+"的题目收藏记录成功");
            meta.put("isOk",true);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("/updateCollectTopic")
    public JSONObject updateCollectTopic(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            String email  = verify.get("email").toString();
            if(map.containsKey("userNotes") && map.containsKey("quesId")){
                User user = userService.getUserByEmail(email);
                //更新收藏题目的笔记
                quesService.updateCollectTopic(map.get("userNotes").toString(),
                        user.getUserPtaId(),map.get("quesId").toString());
                //返回更新后的收藏题目
                List<Submission_Records> collectTopicByEmail = quesService.getCollectTopicByEmail(email);
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(collectTopicByEmail);
                log.info("修改"+email+"题号为"+map.get("quesId").toString()+"的题目成功");
                data.put("collectTopic",jsonArray);
                meta.put("msg","修改"+email+"题号为"+map.get("quesId").toString()+"的题目成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }

    @RequestMapping("deleteCollectTopic")
    public JSONObject deleteCollectTopic(@RequestBody Map<String,Object> map,HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        Map<String, Object> verify = tokenService.verifyToken(request,response);
        if((boolean)verify.get("isOk")){
            String email  = verify.get("email").toString();
            User user = userService.getUserByEmail(email);
            if(map.containsKey("quesId") && map.get("quesId") != null){
                //删除收藏题目
                quesService.deleteCollectTopicByEmailAndQuesId(user.getUserPtaId(),map.get("quesId").toString());
                log.info(email+"删除"+map.get("quesId").toString()+"成功");

                meta.put("msg",email+"删除"+map.get("quesId").toString()+"成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            meta.put("msg","登陆信息已过期，请重新登录");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }
        return jsonObject;
    }
}
