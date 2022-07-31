package com.li.teaching_assistants.repository;

import com.li.teaching_assistants.pojo.Ques_Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuesRepository extends JpaRepository<Ques_Bank,String> {

    List<Ques_Bank> findAll();

    @Override
    <S extends Ques_Bank> S save(S entity);



}
