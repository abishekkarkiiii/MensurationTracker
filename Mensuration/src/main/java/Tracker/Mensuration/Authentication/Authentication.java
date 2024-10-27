package Tracker.Mensuration.Authentication;

import Tracker.Mensuration.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

public class Authentication implements AuthenticationProvider {

    //For Authentication

    //This is for Encryption
//    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
@Autowired
UserDetails userDetails;

    @Override
    public org.springframework.security.core.Authentication authenticate(org.springframework.security.core.Authentication authentication) throws AuthenticationException {
        String Username = authentication.getName();
        System.out.println(Username);
        org.springframework.security.core.userdetails.UserDetails user=userDetails.loadUserByUsername(Username);
        String Password=(String)authentication.getCredentials();
        System.out.println(Username+"  "+Password);
        if (user.getUsername().equals(Username)&&user.getPassword().equals(Password)) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),user.getAuthorities());
        } else {
            throw new BadCredentialsException("username or password wrong");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
