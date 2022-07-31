package com.li.teaching_assistants.runnable;

import com.li.teaching_assistants.pojo.Submission_Records;
import com.li.teaching_assistants.service.QuesService;
import com.li.teaching_assistants.service.RedisService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;

@Data
@NoArgsConstructor
@Slf4j
public class NowcoderItemContentRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String pythonName = "Teacher_Assistants_NK_Item_Content.py";
    private String userEmail;
    private String userNowcoderId;
    private String submitId;
    private String quesId;

    @Resource
    private QuesService quesService;

    @Resource
    private RedisService redisService;

    public NowcoderItemContentRunnable(String userEmail, String userNowcoderId, String submitId, String quesId) {
        this.userEmail = userEmail;
        this.userNowcoderId = userNowcoderId;
        this.submitId = submitId;
        this.quesId = quesId;
    }

    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName + " " + userNowcoderId + " " + submitId;
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
