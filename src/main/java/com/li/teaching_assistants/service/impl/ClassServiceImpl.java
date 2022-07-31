package com.li.teaching_assistants.service.impl;

import com.li.teaching_assistants.dao.ClassDao;
import com.li.teaching_assistants.pojo.*;
import com.li.teaching_assistants.service.ClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Resource
    private ClassDao classDao;

    @Override
    public List<Classes> getClassByEmail(String email) {
        return classDao.getClassesByEmail(email);
    }

    @Override
    public List<Question_Set> getSetByClassId(int class_id) {
        return classDao.getSetByClassId(class_id);
    }

    @Override
    public Question_Set getSetById(String set_id) {
        return classDao.getSetById(set_id);
    }

    @Override
    public List<Performance> getPerformancesBySet(String set_id) {
        return classDao.getPerformancesBySet(set_id);
    }

    @Override
    public List<Submit_Record> getSubmitRecordsByUserPtaIdAndSetId(String email, String set_id) {
        return classDao.getSubmitRecordsByUserPtaIdAndSetId(email,set_id);
    }

    @Override
    public List<Performance> getPtaPerformanceByClassId(String class_id) {
        return classDao.getPtaPerformanceByClassId(class_id);
    }

    @Override
    public List<Performance> getNowcoderPerformanceByClassId(String class_id) {
        return classDao.getNowcoderPerformanceByClassId(class_id);
    }

    @Override
    public List<Performance> getAllPerformanceByClassId(String class_id) {
        return classDao.getAllPerformanceByClassId(class_id);
    }

    @Override
    public List<User_Group> getUserGroupByEmail(String email) {
        return classDao.getUserGroupByEmail(email);
    }

    @Override
    public void createClass(Map<String,Object> map) {
        classDao.createClass(map);
    }

    @Override
    public List<PTA_Groups> getGroupById(String group_id) {
        return classDao.getGroupById(group_id);
    }

    @Override
    public List<PTA_Groups> getBindingGroupById(String group_id) {
        return classDao.getBindingGroupById(group_id);
    }

    @Override
    public List<PTA_Groups> getUnBindingGroupById(String group_id) {
        return classDao.getUnBindingGroupById(group_id);
    }
}
