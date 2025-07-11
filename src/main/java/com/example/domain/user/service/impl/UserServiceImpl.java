package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

    /** ユーザ登録 */
    @Override
    public void signup(MUser user) {
        user.setDepartmentId(1);
        user.setRole("ROLE_GENERAL");

        // パスワード暗号化
        String rawPassword = user.getPassword();
        
System.out.println(encoder.encode(rawPassword));        user.setPassword(encoder.encode(rawPassword));

        mapper.insertOne(user);
    }

    @Override
    public List<MUser> getUsers(MUser user) {
        return mapper.findMany(user);
    }

    @Override
    public MUser getUserOne(String userId) {
        return mapper.findOne(userId);
    }

    @Transactional
    @Override
    public void updateUserOne(String userId, String password, String userName) {
        // パスワード暗号化
        String encryptPassword = encoder.encode(password);

        mapper.updateOne(userId, encryptPassword, userName);

        // わざと例外を発生させる
        // int i = 1/0;
    }

    @Override
    public void deleteUserOne(String userId) {
        int count = mapper.deleteOne(userId);
    }
    
    /** ログインユーザ情報取得 */
    @Override
    public MUser getLoginUser(String userId) {
        return mapper.findLoginUser(userId);
    }
}
