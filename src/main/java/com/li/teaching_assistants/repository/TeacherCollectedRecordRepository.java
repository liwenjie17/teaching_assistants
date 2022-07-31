package com.li.teaching_assistants.repository;

import com.li.teaching_assistants.pojo.TeacherCollectedRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherCollectedRecordRepository extends MongoRepository<TeacherCollectedRecord,String> {
    //id为email+Coll.
    Optional<TeacherCollectedRecord> findById(String id);

    @Override
    <S extends TeacherCollectedRecord> S save(S s);
}
