package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.UpdateTripStatus;
import com.finalproject.finalproject.entity.Employer;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.services.EmployerService;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/employer")
@RestController
public class EmployerController {
    @Autowired
    private EmployerService employerService;
    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<Object> addEmployer(@RequestParam Long userId) {
       try {
           Map<String,Integer> errors = new HashMap<>();
           Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
           Users users = usersService.findByEmail(authentication.getName());
           if(users == null) {
               errors.put("not found",404);
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
           }
           if(users.getRole() != Roles.MANAGER) {
               errors.put("access denied",403);
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
           }
           Employer employer = employerService.saveEmployer(userId);
           return new ResponseEntity<Object>(employer, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteEmployer(@RequestParam Long id) {
        Map<String,Integer> errors = new HashMap<>();
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            employerService.deleteEmployer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SignUpException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }

    @PutMapping("/trip-status-update")
    public ResponseEntity<Object> updateTripStatus(@RequestBody UpdateTripStatus updateTripStatus) {
        Map<String,Integer> errors = new HashMap<>();
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.EMPLOYER) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            employerService.updateEmployer(updateTripStatus.getUserId(), updateTripStatus.getTripStatus());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SignUpException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }

}
