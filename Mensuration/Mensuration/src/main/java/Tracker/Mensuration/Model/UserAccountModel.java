package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Component
public class UserAccountModel {

    @Autowired
    UserRepo userRepo;

    public boolean accountCreation(User user){
        if(this.userRepo.save(user)!=null){
            return true;
        }else{
            return false;
        }
    }

   public void setToken(String token,String username){

        User user=userRepo.findByUsername((username));
        user.setFcmToken(token);
        userRepo.delete(user);
        userRepo.save(user);
   }





}
