package com.li.teaching_assistants.runnable;

import com.li.teaching_assistants.pojo.PTA_Ques;
import com.li.teaching_assistants.pojo.Submission_Records;
import com.li.teaching_assistants.service.QuesService;
import com.li.teaching_assistants.service.RedisService;
import com.li.teaching_assistants.service.TokenService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;

@Data
@NoArgsConstructor
@Slf4j
public class StuPTAItemContentRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String pythonName = "Teacher_Assistants_Stu_PTA_Item_Content.py";
    private String userEmail;
    private String userPtaId;
    private String setId;
    private String quesId;

    @Resource
    private RedisService redisService;
    @Resource
    private QuesService quesService;

    public StuPTAItemContentRunnable(String userEmail,String userPtaId, String setId, String quesId) {
        this.userEmail = userEmail;
        this.userPtaId = userPtaId;
        this.setId = setId;
        this.quesId = quesId;
    }

    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName + " " + userPtaId + " " + setId + " " + quesId;
        log.info("执行脚本:"+common);
        try {
            Process process = Runtime.getRuntime().exec(common);
            Submission_Records records = quesService.getCollectTopicByEmailAndQuesId(userEmail, quesId);
            redisService.insertCollectQues(userEmail,records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
