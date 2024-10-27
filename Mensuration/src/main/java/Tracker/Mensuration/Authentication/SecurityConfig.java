package Tracker.Mensuration.Authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/Animated-Login-and-Signup-Form-Leaf/**","/useraccount/createAccount").permitAll()
                        //For Admin
//                        .requestMatchers("/Admin.html","/Admin/**").hasRole("Admin")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/Animated-Login-and-Signup-Form-Leaf/index.html")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_Admin"));
                            System.out.println(isAdmin);
                            if (isAdmin) {
                                response.sendRedirect("/Admin.html"); // Redirect to admin page
                            } else {
                                response.sendRedirect("/login/test.html"); // Redirect to regular user page
                            }
                            //temprorary
                            response.setStatus(200);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.sendRedirect("/login/index.html");
                        })
                        .permitAll()
                ) .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login.html?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());


        return http.build();
    }


    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new Authentication();
    }
}

