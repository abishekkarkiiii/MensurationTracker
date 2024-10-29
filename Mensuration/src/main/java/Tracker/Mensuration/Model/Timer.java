package Tracker.Mensuration.Model;

import Tracker.Mensuration.Entity.User;
import Tracker.Mensuration.Repositry.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.TimeZone;

@Component
@Data
public class Timer {

    @Autowired
    UserRepo userRepo;

    private String cronExpression = "0/5 * * * * *"; // Default CRON expression (every 5 seconds)
    private ScheduledFuture<?> scheduledTask;
    private final TaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private User user;
    @PostConstruct
    public void start() {
        ((ThreadPoolTaskScheduler) scheduler).initialize();
        scheduleTask();
    }

    // Method to schedule the task with a CRON expression
    private void scheduleTask() {
        if (scheduledTask != null) scheduledTask.cancel(true); // Cancel if already running
        scheduledTask = ((ThreadPoolTaskScheduler) scheduler).schedule(()->executeTask(),
                new CronTrigger(cronExpression, TimeZone.getDefault()));
    }

    // The task to be executed
    private void executeTask() {
        System.out.println(this.user);
        try{
            this.user.getOldDate().add(user.getDate());
            userRepo.save(this.user);
            this.updateCron(cronExpression,user);
        }catch (NullPointerException e){
            System.out.println("server starting.....");
        }


        System.out.println("Task running at: " + System.currentTimeMillis());
    }

    // Method to update the CRON expression and reschedule
    public void updateCron(String newCronExpression, User user){
        this.cronExpression = newCronExpression;
        this.user=user;
        scheduleTask();
        System.out.println("CRON updated to: " + newCronExpression);
    }
    public void updateCron(String newCronExpression){
        this.cronExpression = newCronExpression;
        scheduleTask();
        System.out.println("CRON updated to: " + newCronExpression);
    }
}
