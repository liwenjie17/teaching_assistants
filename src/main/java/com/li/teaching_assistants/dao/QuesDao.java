package com.li.teaching_assistants.dao;

import com.li.teaching_assistants.pojo.NowCoder_Ques;
import com.li.teaching_assistants.pojo.PTA_Ques;
import com.li.teaching_assistants.pojo.Question_Set;
import com.li.teaching_assistants.pojo.Submission_Records;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuesDao {
    List<NowCoder_Ques> getNowCoderQuesByEmailAndLabel(@Param("email") String email,@Param("label") String label);
    List<PTA_Ques> getPTAQuesByEmailAndLabel(@Param("email")String email,@Param("label") String label);
    List<Question_Set> getLabel(@Param("email") String email);
    List<NowCoder_Ques> getNowCoderQuesByEmailAndType(@Param("email") String email,@Param("type") String type);
    List<Submission_Records> getCollectTopicByEmail(@Param("email") String email);
    void updateCollectTopic(@Param("notes") String notes,@Param("userId") String userId,@Param("quesId") String quesId);
    List<Question_Set> getSetByEmail(@Param("email") String email);
    List<PTA_Ques> getPTAQuesByEmailAndSetId(@Param("email")String email,@Param("set_id") String set_id);
    PTA_Ques getPTAQuesByEmailAndQuesId(@Param("email")String email,@Param("ques_id")String ques_id);
    Submission_Records getCollectTopicByEmailAndQuesId(@Param("email")String email,@Param("ques_id")String ques_id);
    void insertCollectTopicWithEmailAndQuesId(Submission_Records submission_records);
    void deleteCollectTopicByEmailAndQuesId(@Param("user_id")String user_id,@Param("ques_id")String ques_id);
}
