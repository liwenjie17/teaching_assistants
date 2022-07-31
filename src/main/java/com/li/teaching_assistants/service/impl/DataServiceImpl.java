package com.li.teaching_assistants.service.impl;

import com.li.teaching_assistants.dao.DataDao;
import com.li.teaching_assistants.pojo.TeacherCollectedRecord;
import com.li.teaching_assistants.pojo.University;
import com.li.teaching_assistants.repository.TeacherCollectedRecordRepository;
import com.li.teaching_assistants.service.DataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {
    @Resource
    private DataDao dataDao;

    @Resource
    private TeacherCollectedRecordRepository recordRepository;

    @Override
    public List<University> getUniversity() {
        return dataDao.getUniversity();
    }

    @Override
    public TeacherCollectedRecord updateRecords(TeacherCollectedRecord record) {
        return recordRepository.save(record);
    }

    @Override
    public TeacherCollectedRecord getRecordsById(String id) {
        Optional<TeacherCollectedRecord> record = recordRepository.findById(id);
        return record.orElse(null);
    }
}
