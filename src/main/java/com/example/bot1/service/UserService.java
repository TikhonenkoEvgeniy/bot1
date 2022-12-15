package com.example.bot1.service;

import com.example.bot1.entity.User;
import com.example.bot1.exception.NotFoundRecordException;
import com.example.bot1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    UserService(@Autowired UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isExistById(Long id) {
        return userRepo.existsById(id);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundRecordException(id));
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteById(Long userId) {
        if (isExistById(userId)) { userRepo.deleteById(userId); }
    }
}
