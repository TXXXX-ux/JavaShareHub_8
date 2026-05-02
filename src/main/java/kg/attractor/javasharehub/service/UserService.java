package kg.attractor.javasharehub.service;

import kg.attractor.javasharehub.dto.UserRegistrationDto;
import kg.attractor.javasharehub.model.User;
import java.util.List;

public interface UserService {
    void registerUser(UserRegistrationDto dto);

    User findByEmail(String email);

    void processOAuthPostLogin(String username);

    List<User> getAllUsers();

    void toggleUserStatus(Long id);
}