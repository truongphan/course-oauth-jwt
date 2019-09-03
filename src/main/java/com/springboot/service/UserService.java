package com.springboot.service;

import com.springboot.entity.UserEntity;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;
import com.springboot.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        UserEntity entity = userRepository.save(UserUtils.toUserEntity(user));
        return UserUtils.toUserModel(userRepository.findById(entity.getId()).get());
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        List<UserEntity> entities = userRepository.findAll();
        entities.forEach(e -> userList.add(UserUtils.toUserModel(e)));
        return userList;
    }
}
