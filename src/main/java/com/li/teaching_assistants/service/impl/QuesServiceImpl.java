package com.li.teaching_assistants.service.impl;

import com.li.teaching_assistants.dao.QuesDao;
import com.li.teaching_assistants.pojo.*;
import com.li.teaching_assistants.repository.NowcoderQuesRepository;
import com.li.teaching_assistants.repository.QuesRepository;
import com.li.teaching_assistants.service.QuesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuesServiceImpl implements QuesService {
    @Resource
     private QuesRepository quesRepository;
    @Resource
    private QuesDao quesDao;
    @Resource
    private NowcoderQuesRepository nowcoderQuesRepository;
    @Override
    public List<Ques_Bank> findAll() {
        return quesRepository.findAll();
    }

    @Override
    public Ques_Bank save(Ques_Bank ques_bank) {
        return quesRepository.save(ques_bank);
    }

    @Override
    public List<NowCoder_Ques> getNowCoderQuesByEmailAndLabel(String email,String label) {
        return quesDao.getNowCoderQuesByEmailAndLabel(email,label);
    }

    @Override
    public List<PTA_Ques> getPTAQuesByEmailAndLabel(String email, String label) {
        return quesDao.getPTAQuesByEmailAndLabel(email,label);
    }

    @Override
    public List<Question_Set> getLabel(String email) {
        return quesDao.getLabel(email);
    }

    @Override
    public List<NowCoder_Ques> getNowCoderQuesByEmailAndType(String email, String type) {
        return quesDao.getNowCoderQuesByEmailAndType(email,type);
    }

    @Override
    public List<Submission_Records> getCollectTopicByEmail(String email) {
        return quesDao.getCollectTopicByEmail(email);
    }

    @Override
    public void updateCollectTopic(String notes, String userId, String quesId) {
        quesDao.updateCollectTopic(notes,userId,quesId);
    }

    @Override
    public List<Question_Set> getSetByEmail(String email) {
        return quesDao.getSetByEmail(email);
    }

    @Override
    public List<PTA_Ques> getPTAQuesByEmailAndSetId(String email, String set_id) {
        return quesDao.getPTAQuesByEmailAndSetId(email,set_id);
    }

    @Override
    public PTA_Ques getPTAQuesByEmailAndQuesId(String email, String ques_id) {
        return quesDao.getPTAQuesByEmailAndQuesId(email,ques_id);
    }

    @Override
    public Submission_Records getCollectTopicByEmailAndQuesId(String email, String ques_id) {
        return quesDao.getCollectTopicByEmailAndQuesId(email,ques_id);
    }

    @Override
    public void insertCollectTopicWithEmailAndQuesId(Submission_Records submission_records) {
        quesDao.insertCollectTopicWithEmailAndQuesId(submission_records);
    }

    @Override
    public void deleteCollectTopicByEmailAndQuesId(String user_id, String ques_id) {
        quesDao.deleteCollectTopicByEmailAndQuesId(user_id,ques_id);
    }

    @Override
    public NowCoder_Ques findByQuesNo(String quesNo) {
        return nowcoderQuesRepository.findByQuesNo(quesNo);
    }
}
