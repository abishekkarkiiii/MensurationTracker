package Tracker.Mensuration.Controller;


import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.CRON;
import Tracker.Mensuration.Model.DateUpdater;
import Tracker.Mensuration.Model.Timer;
import Tracker.Mensuration.Model.UserAccountModel;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("useraccount")
public class UserAccountController {
    @Autowired
    DateUpdater dateUpdater;
    @Autowired
    UserAccountModel userAccountModel;
    @PostMapping("createAccount")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        System.out.println(user);
        if (userAccountModel.accountCreation(user)) {
            // Simply return a success response
            return ResponseEntity.ok("Success"); // Use 200 OK for successful account creation
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unsuccessful");
        }
    }

    @GetMapping("/updateDelay")
    public void updateDelay() {
        dateUpdater.dateCreator(new ObjectId("671deb0d37fdcd6b9b782526"),"31");

    }



}
