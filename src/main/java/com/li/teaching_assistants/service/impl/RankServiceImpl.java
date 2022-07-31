package com.li.teaching_assistants.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.li.teaching_assistants.dao.RankDao;
import com.li.teaching_assistants.dao.UserDao;
import com.li.teaching_assistants.pojo.All_Rank;
import com.li.teaching_assistants.service.RankService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
public class RankServiceImpl implements RankService {
    @Resource
    private RankDao rankDao;
    @Resource
    private UserDao userDao;

    @Value(value = "${image.address}")
    private String Address;



    @Override
    public JSONArray getAllRank(String email) {
        List<All_Rank> ranks = rankDao.getAllRank(email);
        JSONArray jsonArray = new JSONArray();
        int rankNum = 1;
        String imagePath = "http://"+Address+":8080/images/";

        for(All_Rank rank : ranks){
            rank.setRank(rankNum++);
            rank.setCompleteNumber(rank.getPtaNumber()+rank.getNowcoderNumber());
            rank.setScore(String.valueOf(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore())));
            rank.setIcon(userDao.getUserByEmail(rank.getEmail()).getUserImage());
            int score =Integer.parseInt(rank.getScore());
            if(score >=0 && score < 50 ){      //黑铁
                rank.setTitle("编程小白");
                rank.setRankImage(imagePath+"1.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()));
                rank.setNextLevel(50);
            } else if(score < 120){            //青铜
                rank.setTitle("编程菜鸟");
                rank.setRankImage(imagePath+"2.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 50);
                rank.setNextLevel(70);
            } else if(score < 210) {            //白银
                rank.setTitle("编程入门");
                rank.setRankImage(imagePath + "3.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 120);
                rank.setNextLevel(90);
            } else if(score < 320){             //黄金
                rank.setTitle("编程初级");
                rank.setRankImage(imagePath + "4.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 210);
                rank.setNextLevel(110);
            } else if (score < 500){            //白金
                rank.setTitle("编程中级");
                rank.setRankImage(imagePath + "5.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 320);
                rank.setNextLevel(180);
            } else if (score < 800){            //钻石
                rank.setTitle("编程高级");
                rank.setRankImage(imagePath + "6.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 500);
                rank.setNextLevel(300);
            } else if (score < 1100){           //大师
                rank.setTitle("编程牛人");
                rank.setRankImage(imagePath + "7.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 800);
                rank.setNextLevel(300);
            } else if (score < 1400){           //宗师
                rank.setTitle("编程大牛");
                rank.setRankImage(imagePath + "8.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 1100);
                rank.setNextLevel(300);
            } else if(score < 1700){            //王者
                rank.setTitle("编王之王");
                rank.setRankImage(imagePath + "9.png");
                rank.setCurrent(Integer.parseInt(rank.getNowcoderScore())+Integer.parseInt(rank.getPtaScore()) - 1400);
                rank.setNextLevel(300);
            }
            jsonArray.add(rank);
        }
        return jsonArray;
    }

}
