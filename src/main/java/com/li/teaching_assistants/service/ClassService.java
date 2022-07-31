package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClassService {
    List<Classes> getClassByEmail(String email);
    List<Question_Set> getSetByClassId(int class_id);
    Question_Set getSetById(String set_id);
    List<Performance> getPerformancesBySet(String set_id);
    List<Submit_Record> getSubmitRecordsByUserPtaIdAndSetId(String email, String set_id);
    List<Performance> getPtaPerformanceByClassId( String class_id);
    List<Performance> getNowcoderPerformanceByClassId( String class_id);
    List<Performance> getAllPerformanceByClassId( String class_id);
    List<User_Group> getUserGroupByEmail(String email);
    void createClass(Map<String,Object> map);
    List<PTA_Groups> getGroupById(String group_id);
    List<PTA_Groups> getBindingGroupById(String group_id);
    List<PTA_Groups> getUnBindingGroupById( String group_id);
}
