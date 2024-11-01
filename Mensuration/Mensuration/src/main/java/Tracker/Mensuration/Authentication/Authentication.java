package Tracker.Mensuration.Authentication;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.UserAccountModel;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class Authentication implements AuthenticationProvider {

    @Autowired
    UserDetails userDetails;

    @Autowired
    UserAccountModel userAccountModel;

    @Autowired
    UserRepo userRepo;

    @Override
    public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) throws AuthenticationException {
        String Username = authentication.getName();
        String Password = (String) authentication.getCredentials();

        // If no username and password provided, check cookies
        if (Username.isEmpty() && Password.isEmpty()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userIdFromCookie = getCookieValue(request, "userId");

            if (userIdFromCookie != null) {
                Optional<User> userChecker = userRepo.findById(new ObjectId(userIdFromCookie));

                if (userChecker.isPresent()) {
                    User user = userChecker.get();
                    Username = user.getUsername(); // Get username from the User object
                    Password = user.getPassword(); // Get password from the User object
                } else {
                    System.out.println("User session expired");
                    throw new BadCredentialsException("User session expired.");
                }
            }
        }

        // Fetch user details
         org.springframework.security.core.userdetails.UserDetails user= userDetails.loadUserByUsername(Username);
        if (user != null && user.getPassword().equals(Password)) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("username or password wrong");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    // Helper method to retrieve cookie value by name
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
