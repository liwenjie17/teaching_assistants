package com.li.teaching_assistants.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.li.teaching_assistants.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rank")
@Slf4j
public class RankController {
    @Resource
    private RankService rankService;
    @RequestMapping("/getAllRank")
    public JSONObject getAllRank(@RequestBody Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> data = new HashMap<>();
        Map<String,Object> meta = new HashMap<>();
        JSONArray rank = rankService.getAllRank(null);
        //System.out.println("size===="+rank.size());
        int totalPage = (int) Math.ceil((double)(rank.size()/50.0));
        if(rank.size() != 0){
            if(map.containsKey("page")){
                int page = Integer.parseInt(map.get("page").toString());
                if(page == 1){
                    JSONArray pageRank1_5 = new JSONArray();
                    for(int i=0;i<5 && i<rank.size();i++){
                        pageRank1_5.add(rank.get(i));
                    }
                    data.put("rank1_5",pageRank1_5);
                    JSONArray pageRank = new JSONArray();
                    for(int i=5;i < 50 && i<rank.size();i++){
                        pageRank.add(rank.get(i));
                    }
                    data.put("rank",pageRank);
                }else {
                    JSONArray pageRank = new JSONArray();
                    for(int i=(page-1)*50;i<(page-1)*50 + 50 && i<rank.size() ;i++){
                        pageRank.add(rank.get(i));
                    }
                    data.put("rank",pageRank);
                }
                data.put("totalPage",totalPage);
                meta.put("msg","获取全站排名成功");
                meta.put("isOk",true);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }else {
                meta.put("msg","未入参page");
                meta.put("isOk",false);
                jsonObject.put("data",data);
                jsonObject.put("meta",meta);
            }
        }else {
            meta.put("msg","查询失败");
            meta.put("isOk",false);
            jsonObject.put("data",data);
            jsonObject.put("meta",meta);
        }

        return jsonObject;
    }
}
