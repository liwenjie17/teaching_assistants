package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuesService {

    List<Ques_Bank> findAll();
    Ques_Bank save(Ques_Bank ques_bank);
    List<NowCoder_Ques> getNowCoderQuesByEmailAndLabel( String email,String label);
    List<PTA_Ques> getPTAQuesByEmailAndLabel(String email,  String label);
    List<Question_Set> getLabel( String email);
    List<NowCoder_Ques> getNowCoderQuesByEmailAndType( String email, String type);
    List<Submission_Records> getCollectTopicByEmail( String email);
    void updateCollectTopic(String notes,String userId, String quesId);
    List<Question_Set> getSetByEmail(String email);
    List<PTA_Ques> getPTAQuesByEmailAndSetId(String email,String set_id);
    PTA_Ques getPTAQuesByEmailAndQuesId(String email,String ques_id);
    Submission_Records getCollectTopicByEmailAndQuesId(String email,String ques_id);
    void insertCollectTopicWithEmailAndQuesId(Submission_Records submission_records);
    void deleteCollectTopicByEmailAndQuesId(String user_id,String ques_id);
    NowCoder_Ques findByQuesNo(String quesNo);
}
