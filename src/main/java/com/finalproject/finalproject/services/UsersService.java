package com.finalproject.finalproject.services;

import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.UserStatus;
import com.finalproject.finalproject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> allUsers() {
        return new ArrayList<>(usersRepository.findAll());
    }
    public Optional<Users> findUserById(Long id) {
        return usersRepository.findById(id);
    }
    public void deleteUser(Long id) {
        Users user = usersRepository.findById(id).orElse(null);
        assert user != null;
        user.setStatus(UserStatus.INACTIVE);
        updateUser(user);
    }
    public void updateUser(Users user) {
        usersRepository.save(user);
    }
    public Users findByEmail(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);
        return user.orElse(null);
    }
}
