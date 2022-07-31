package com.li.teaching_assistants.dao;

import com.li.teaching_assistants.pojo.All_Rank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankDao {
    List<All_Rank> getAllRank(@Param("email") String email);
}
