package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountModel {

    @Autowired
    UserRepo user;

    public boolean accountCreation(User user){
        if(this.user.save(user)!=null){
            return true;
        }else{
            return false;
        }
    }






}
