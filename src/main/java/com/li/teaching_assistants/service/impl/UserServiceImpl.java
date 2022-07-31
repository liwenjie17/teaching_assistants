package com.li.teaching_assistants.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.li.teaching_assistants.dao.UserDao;
import com.li.teaching_assistants.pojo.User;
import com.li.teaching_assistants.repository.UserRepository;
import com.li.teaching_assistants.service.RedisService;
import com.li.teaching_assistants.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Resource
    UserDao userDao;

    @Resource
    private RedisService redisService;



    @Override
    public User register(User user) {
        String userEmail = user.getUserEmail();
        String password = user.getUserPassword();
        String pass = SecureUtil.md5(userEmail + password);
        user.setUserPassword(pass);
        return userRepository.save(user);
    }

    @Override
    public User findByUserEmail(String email) {
        User user = redisService.getUser(email);
        if(user != null)
            return user;
        else{
            User user1 = userRepository.findByUserEmail(email);
            if (user1 != null)
                redisService.insertUser(user1);
            return user1;
        }
    }

    @Override
    public User findByUserEmailAndUserPassword(String email,String password) {
        User user = userDao.getUserByEmail(email);
        if(user != null){
            if(SecureUtil.md5(email+password).equals(user.getUserPassword())){
                return user;
            }else
                return null;
        }else
            return null;
    }

    @Override
    public User updateUserInfo(User user) {
        if(redisService.getUser(user.getUserEmail()) != null)
            redisService.deleteUser(user.getUserEmail());
        userDao.updateUserInfo(user);
        User new_user = userDao.getUserByEmail(user.getUserEmail());
        redisService.insertUser(new_user);
        return new_user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = redisService.getUser(email);
        if(user != null)
            return user;
        else{
            User user1 = userDao.getUserByEmail(email);
            if (user1 != null)
                redisService.insertUser(user1);
            return user1;
        }
    }

    @Override
    public void updateImage(String image, String email) {
        if(redisService.getUser(email) != null)
            redisService.deleteUser(email);
        userDao.updateImage(image,email);
        User new_user = userDao.getUserByEmail(email);
        redisService.insertUser(new_user);
    }

    @Override
    public User findByUserPtaId(String pta_id) {
        return userRepository.findByUserPtaId(pta_id);
    }
}
