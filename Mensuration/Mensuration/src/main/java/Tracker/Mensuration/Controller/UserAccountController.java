package Tracker.Mensuration.Controller;


import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.DateUpdater;
import Tracker.Mensuration.Model.UserAccountModel;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("useraccount")
public class UserAccountController {
    @Autowired
    DateUpdater dateUpdater;
    @Autowired
    UserAccountModel userAccountModel;
    @Autowired
    UserRepo userRepo;

public  String id;
    @PostMapping("createAccount")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        System.out.println(user);
        if (userAccountModel.accountCreation(user)) {
            id=user.getUsername();
            // Simply return a success response
            return ResponseEntity.ok(user.getUsername()); // Use 200 OK for successful account creation
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unsuccessful");
        }
    }

    @GetMapping("/updateDelay")
    public void updateDelay() {
        dateUpdater.dateCreator(new ObjectId("672494eaf0f0010ebfb896ad"),"3");

    }



    @PostMapping("/registerToken")
    public void getFCMToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String username = payload.get("id");
        System.out.println(token + username+"                  hello I am token");
        userAccountModel.setToken(token,username);

    }



}
