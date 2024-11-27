package com.finalproject.finalproject.services;
import com.finalproject.finalproject.Exceptions.EmailTakenException;
import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.Exceptions.LoginException;
import com.finalproject.finalproject.dto.LoginRequest;
import com.finalproject.finalproject.dto.RegisterRequest;
import com.finalproject.finalproject.entity.Users;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.enums.UserStatus;
import com.finalproject.finalproject.repositories.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {
    private final UsersRepository userRepository;

    private final UsersService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private static final Pattern email = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );
    private static final Pattern phone = Pattern.compile(
            "^\\+(\\d{1,3})( )?\\d{1,4}( )?\\d{3,4}( )?\\d{3,4}$"
    );
    public AuthenticationService(
            UsersRepository userRepository,
            UsersService userService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users signup(RegisterRequest input) throws EmailTakenException {
        Users user = new Users();
        Map<String, Integer> errors = new HashMap<>();

        if (input.getName() == null || input.getName().length() < 2 || input.getName().length() > 50) {
            errors.put("Invalid name: must be between 2 and 50 characters",400);
        }
        if (input.getSurname() == null || input.getSurname().length() < 2 || input.getSurname().length() > 50) {
            errors.put("Invalid surname: must be between 2 and 50 characters",400);
        }
        if (input.getPassword() == null || input.getPassword().length() < 8) {
            errors.put("Invalid password: must be at least 8 characters long",400);
        }
        if (input.getEmail() == null || !email.matcher(input.getEmail()).matches()) {
            errors.put("Invalid email format",400);
        }
        if (input.getPhoneNumber() == null || !phone.matcher(input.getPhoneNumber()).matches()) {
            errors.put("Invalid phone number format",400);
        }

        // Проверка наличия email в системе
        if (input.getEmail() != null && userService.findByEmail(input.getEmail()) != null) {
            errors.put("Email already taken",409);
        }

        // Если есть ошибки, выбросить исключение с деталями
        if (!errors.isEmpty()) {
            throw new SignUpException("Validation failed", errors);
        }

        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(Roles.USER);
        user.setStatus(UserStatus.ACTIVE);
        Users userSave = userRepository.save(user);
        return userSave;
    }

    public Users authenticate(LoginRequest input) throws LoginException {
        Map<String, Integer> errors = new HashMap<>();

        // Проверка входных данных
        if (input.getEmail() == null || !email.matcher(input.getEmail()).matches()) {
            errors.put("Invalid email format or email is null",400);
        }
        if (input.getPassword() == null) {
            errors.put("Password is required",400);
        }

        // Если есть ошибки, выбросить исключение
        if (!errors.isEmpty()) {
            throw new LoginException("Validation failed", errors);
        }

        // Аутентификация
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (Exception ex) {
            errors.put("Invalid email or password",401);
            throw new LoginException("Authentication failed", errors);
        }

        // Поиск пользователя
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> {
                    errors.put("User not found",404);
                    return new LoginException("User retrieval failed", errors);
                });
    }

}
