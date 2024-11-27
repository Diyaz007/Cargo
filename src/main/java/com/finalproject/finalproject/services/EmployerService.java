package com.finalproject.finalproject.services;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.entity.Employer;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.enums.TripStatus;
import com.finalproject.finalproject.enums.WorkStatus;
import com.finalproject.finalproject.repositories.EmployerRepository;
import com.finalproject.finalproject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private UsersService usersService;

    public Employer saveEmployer(Long userId) {
        Optional<Users> user = usersService.findUserById(userId);
        Employer employer = new Employer();
        employer.setUserId(user.get());
        employer.setTripStatus(TripStatus.WAIT);
        employer.setWorkStatus(WorkStatus.ACTIVE);
        Users user1 = user.get();
        user1.setRole(Roles.EMPLOYER);
        usersService.updateUser(user1);
        return employerRepository.save(employer);
    }
    public void deleteEmployer(Long id) {
        try {
            Employer employer = employerRepository.findById(id).get();
            employer.setWorkStatus(WorkStatus.INACTIVE);
            employerRepository.save(employer);
        }catch (Exception e){
            HashMap<String,Integer> errors = new HashMap<>();
            errors.put("Employer not found",404);
            throw new SignUpException("Delete Employer Error",errors);
        }
    }
    public Employer updateEmployer(Long id, TripStatus tripStatus) {
        try {
            Employer employer = employerRepository.findById(id).get();
            employer.setTripStatus(tripStatus);
            return employerRepository.save(employer);
        }catch (Exception e){
            HashMap<String,Integer> errors = new HashMap<>();
            errors.put("Employer not found",404);
            throw new SignUpException("Update Employer Error",errors);
        }
    }

}
