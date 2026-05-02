package kg.attractor.javasharehub.service.impl;

import kg.attractor.javasharehub.dto.UserRegistrationDto;
import kg.attractor.javasharehub.exception.DuplicateEmailException;
import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.UserRepository;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Попытка регистрации на: {}", dto.getEmail());
            throw new DuplicateEmailException("Этот email уже занят!");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("ROLE_USER")
                .enabled(true)
                .build();

        userRepository.save(user);
        log.info("Новый пользователь успешно зарегистрирован: {}", user.getEmail());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Юзер не найден"));
    }

    @Override
    public void processOAuthPostLogin(String username) {
        Optional<User> existUser = userRepository.findByEmail(username);

        if (existUser.isEmpty()) {
            User user = User.builder()
                    .email(username)
                    .password(passwordEncoder.encode("qwerty"))
                    .role("ROLE_USER")
                    .enabled(true)
                    .build();
            userRepository.save(user);
        }

        User user = userRepository.findByEmail(username).get();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        log.info("Статус пользователя {} изменен на: {}", user.getEmail(), user.isEnabled());
    }
}