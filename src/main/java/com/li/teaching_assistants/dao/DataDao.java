package com.li.teaching_assistants.dao;

import com.li.teaching_assistants.pojo.University;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataDao {
    void insertUniversity(@Param("university") String university);
    @Select("SELECT * FROM teaching_assistants.university")
    List<University> getUniversity();
}
