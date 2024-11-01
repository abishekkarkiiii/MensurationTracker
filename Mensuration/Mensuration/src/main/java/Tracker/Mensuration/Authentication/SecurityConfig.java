package Tracker.Mensuration.Authentication;

import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserRepo userRepo;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/Animated-Login-and-Signup-Form-Leaf/**", "/useraccount/**", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/Animated-Login-and-Signup-Form-Leaf/index.html")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();
                            String id = userRepo.findByUsername(username).getId().toString();
                            HttpSession session = request.getSession();
                            session.setAttribute("userId", id);
                            Cookie cookie = new Cookie("userId", id);
                            cookie.setMaxAge(7 * 24 * 60 * 60); // Set for 7 days
                            cookie.setHttpOnly(false); // Optional: Prevents JavaScript access to the cookie
                            cookie.setPath("/"); // Cookie accessible for entire domain
                            response.addCookie(cookie);
                            response.setContentType("application/json");
                            String jsonResponse = String.format("{\"message\": \"Logged in successfully\", \"id\": \"%s\"}", id);
                            response.getWriter().write(jsonResponse);

                            response.setStatus(200);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Authentication failed. Invalid credentials.\"}");
                        })
                        .permitAll()
                )
                .rememberMe(rememberMeConfigurer ->
                        rememberMeConfigurer
                                .tokenRepository(persistentTokenRepository())
                                .tokenValiditySeconds(1209600) // 14 days
                                .key("uniqueAndSecret") // Secret key for hashing tokens
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .deleteCookies("userId")// Clear session and remember-me cookies
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl(); // Stores tokens in memory (for production, use a database-backed repository)
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new Authentication();
    }
}
