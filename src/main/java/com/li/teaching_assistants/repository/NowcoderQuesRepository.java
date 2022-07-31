package com.li.teaching_assistants.repository;

import com.li.teaching_assistants.pojo.NowCoder_Ques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NowcoderQuesRepository extends JpaRepository<NowCoder_Ques,String> {
    NowCoder_Ques findByQuesNo(String quesNo);
}
