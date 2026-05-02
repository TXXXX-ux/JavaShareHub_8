package kg.attractor.javasharehub.service;

import kg.attractor.javasharehub.model.User;
import kg.attractor.javasharehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(), // <-- ВОТ ЭТО И ЕСТЬ НАШ БАН! Спринг проверит эту строчку
                true,             // аккаунт не просрочен
                true,             // пароль не просрочен
                true,             // аккаунт не заблокирован по другим причинам
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}