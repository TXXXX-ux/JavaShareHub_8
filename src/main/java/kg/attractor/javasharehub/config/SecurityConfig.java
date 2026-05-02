package kg.attractor.javasharehub.config;

import kg.attractor.javasharehub.security.CustomOAuth2User;
import kg.attractor.javasharehub.security.CustomOAuth2UserService;
import kg.attractor.javasharehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final CustomOAuth2UserService oauthUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/h2-console/**")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register", "/login", "/forgot-password", "/reset-password").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/download/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/profile", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(oauthUserService))
                        .successHandler((request, response, authentication) -> {
                            var oauthUser = (CustomOAuth2User) authentication.getPrincipal();
                            userService.processOAuthPostLogin(oauthUser.getEmail());
                            response.sendRedirect("/");

                        })

                );

        return http.build();
    }
}