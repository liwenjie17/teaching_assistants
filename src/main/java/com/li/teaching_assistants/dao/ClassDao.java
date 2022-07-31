package com.li.teaching_assistants.dao;

import com.li.teaching_assistants.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassDao {
    List<Classes> getClassesByEmail(@Param("email") String email);
    List<Question_Set> getSetByClassId(@Param("class_id") int class_id);
    @Select("SELECT * FROM questions_set WHERE set_id = #{set_id}")
    Question_Set getSetById(@Param("set_id") String set_id);
    List<Performance> getPerformancesBySet(@Param("set_id") String set_id);
    List<Submit_Record> getSubmitRecordsByUserPtaIdAndSetId(@Param("user_pta_id")String user_pta_id,@Param("set_id") String set_id);
    List<Performance> getPtaPerformanceByClassId(@Param("class_id") String class_id);
    List<Performance> getNowcoderPerformanceByClassId(@Param("class_id") String class_id);
    List<Performance> getAllPerformanceByClassId(@Param("class_id") String class_id);
    List<User_Group> getUserGroupByEmail(@Param("email")String email);
    //void createClass(@Param("className")String className,@Param("classOwner")String classOwner,@Param("groupId")String groupId,List<PTA_Groups> groups);
    void createClass(Map<String,Object> map);
    List<PTA_Groups> getGroupById(@Param("group_id") String group_id);
    List<PTA_Groups> getBindingGroupById(@Param("group_id") String group_id);
    List<PTA_Groups> getUnBindingGroupById(@Param("group_id") String group_id);
}
