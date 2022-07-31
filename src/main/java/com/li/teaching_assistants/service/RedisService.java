package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.PTA_Ques;
import com.li.teaching_assistants.pojo.Submission_Records;
import com.li.teaching_assistants.pojo.User;

public interface RedisService {
    void insertUser(User user);
    User getUser(String email);
    void deleteUser(String email);
    void insertCollectQues(String email, Submission_Records records);
    Submission_Records getCollectQues(String email,String ques_id);
    void deleteCollectQues(String email,String ques_id);
}
