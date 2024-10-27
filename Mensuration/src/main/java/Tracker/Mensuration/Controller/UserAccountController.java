package Tracker.Mensuration.Controller;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Model.UserAccountModel;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("useraccount")
public class UserAccountController {

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

}
