package Tracker.Mensuration.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

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
}
