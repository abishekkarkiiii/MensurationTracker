package Tracker.Mensuration.Authentication;


import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;
@Service
public class UserDetails implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UserRepo user;
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username+"UserDetails");
        if(username!=null){
            User virtualUser=user.findByUsername(username);
            System.out.println(virtualUser+"virtualuser");
            if(virtualUser!=null){
                org.springframework.security.core.userdetails.User.UserBuilder builder=withUsername(username);
                builder.password(virtualUser.getPassword());
                builder.roles("User");
                return builder.build();
            }else {
                System.out.println("UserNotFound");
                return null;
            }
        }else{
            System.out.println("UserNameNotReceived");
                return null;
            }

        }

    }

