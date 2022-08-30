package com.telegramqbot.qbotsbot.service;

import com.telegramqbot.qbotsbot.dto.User;
import com.telegramqbot.qbotsbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
