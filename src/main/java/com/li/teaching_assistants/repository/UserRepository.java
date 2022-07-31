package com.li.teaching_assistants.repository;

import com.li.teaching_assistants.pojo.Ques_Bank;
import com.li.teaching_assistants.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Override
    <S extends User> S save(S entity);
    User findByUserEmail(String mail);
    User findByUserPtaId(String pta_id);
}
