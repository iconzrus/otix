package com.yuiybishel.Otix.controller;

import com.yuiybishel.Otix.dto.UserForm;
import com.yuiybishel.Otix.model.User;
import com.yuiybishel.Otix.repository.UserRepository;
import com.yuiybishel.Otix.service.UserDetailsServiceImpl;
import com.yuiybishel.Otix.service.UserService;
import com.yuiybishel.Otix.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserForm userForm) {
        // Создаем нового пользователя
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));

        // Сохраняем пользователя в базе данных
        userRepository.save(user);

        // Возвращаем созданного пользователя
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserForm userForm) {
        // Загружаем пользователя из базы данных
        User user = userRepository.findByUsername(userForm.getUsername());

        // Проверяем, что пароль пользователя совпадает с введенным паролем
        if (!passwordEncoder.matches(userForm.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Возвращаем аутентифицированного пользователя
        return ResponseEntity.ok(user);
    }
}