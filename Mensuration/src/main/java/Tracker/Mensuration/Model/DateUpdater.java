package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DateUpdater {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CRON cron;
    @Autowired
    Timer timer;
    private Optional<User> usercheck;
    public boolean dateCreator(ObjectId id,String date){
        usercheck=userRepo.findById(id);
        User virtualuser;
        if(usercheck.isPresent()){
            User user=usercheck.get();
            virtualuser=user;
            if(user.getDate()==null){
                String Calculatedate=Integer.toString(Integer.parseInt(date)-2);
                timer.updateCron(cron.convertToCron(Integer.parseInt(date)-2,16,26));
                user.setDate(Calculatedate);
                userRepo.delete(user);// for test only
                userRepo.save(user);
                return true;
            }else{
                String Calculatedate=Integer.toString(Integer.parseInt(date)-2);
                timer.updateCron(cron.convertToCron(Integer.parseInt(date)-2,16,43),virtualuser);
                user.setDate(Calculatedate);
                userRepo.delete(user);// for test only
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }

    public  boolean dateUpdater(){


        return false;
    }



}
