package Tracker.Mensuration.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@Document(collection = "Users")
public class User {
    //This is for temproray test

    @NonNull
    @Indexed(unique = true)
    private String username;
    private String password;
    private int age;
    private String height;
    private String weight;
    private ObjectId userId;
    private String date=null;
    private List<String>oldDate=new ArrayList<>();
    @Id
    private ObjectId id;
    private  String fcmToken;
}
