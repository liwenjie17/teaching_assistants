package com.li.teaching_assistants.service;

import com.li.teaching_assistants.pojo.TeacherCollectedRecord;
import com.li.teaching_assistants.pojo.University;

import javax.annotation.Resource;
import java.util.List;

public interface DataService {
    List<University> getUniversity();
    TeacherCollectedRecord updateRecords(TeacherCollectedRecord record);
    TeacherCollectedRecord getRecordsById(String id);
}
