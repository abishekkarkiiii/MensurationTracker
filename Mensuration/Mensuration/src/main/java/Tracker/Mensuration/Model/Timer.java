package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.TimeZone;

@Component
public class Timer {

    @Autowired
    private UserRepo userRepo;

    private String cronExpression = "0 * * * * *";
    private ScheduledFuture<?> scheduledTask;
    private final TaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private User user;

    @PostConstruct
    public void start() {
        initializeFirebase();
        ((ThreadPoolTaskScheduler) scheduler).initialize();
        scheduleTask();
    }

    private void initializeFirebase() {
        try (FileInputStream serviceAccount = new FileInputStream("D:\\Mensuration\\Mensuration\\src\\main\\resources\\static\\Notification\\notification.json")) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scheduleTask() {
        if (scheduledTask != null) scheduledTask.cancel(true);
        scheduledTask = ((ThreadPoolTaskScheduler) scheduler).schedule(() -> executeTask(),
                new CronTrigger(cronExpression, TimeZone.getDefault()));
    }

    private void executeTask() {
        System.out.println("Task running at: " + System.currentTimeMillis());
        if (user != null) {
            user.getOldDate().add(user.getDate());
            userRepo.save(user);
            sendFCMNotification(user.getFcmToken()); // Send FCM notification
            updateCron(cronExpression, user);
        }
    }

    private void sendFCMNotification(String token) {
        Message message = Message.builder()
                .putData("action", "triggerPrint")
                .setToken(token)
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);
        System.out.println("FCM notification sent to token: " + token);
    }

    public void updateCron(String newCronExpression, User user) {
        this.cronExpression = newCronExpression;
        this.user = user;
        scheduleTask();
        System.out.println("CRON updated to: " + newCronExpression);
    }

    public void updateCron(String newCronExpression) {
        this.cronExpression = newCronExpression;
        scheduleTask();
        System.out.println("CRON updated to: " + newCronExpression);
    }
}
