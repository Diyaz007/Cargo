package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.config.SecurityConfiguration;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin")
@RestController
public class AdminController {
    @Autowired
    private UsersService usersService;

    @PutMapping("/manager/{id}")
    public ResponseEntity<Users> addManager(@PathVariable Long id) {
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.ADMIN) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Users user = usersService.findUserById(id).get();
            user.setRole(Roles.MANAGER);
            usersService.updateUser(user);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
