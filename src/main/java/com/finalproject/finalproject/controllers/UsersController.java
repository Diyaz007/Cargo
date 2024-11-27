package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/users")
@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/me")
    public ResponseEntity<Users> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users currentUser = (Users) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = usersService.findUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        try {
            usersService.deleteUser(id);
            return HttpStatus.OK;
        }catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }



    @GetMapping("/")
    public ResponseEntity<List<Users>> allUsers() {
        try {
            List<Users> users = usersService.allUsers();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
