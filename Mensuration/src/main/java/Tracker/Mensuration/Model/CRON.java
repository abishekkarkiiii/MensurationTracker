package Tracker.Mensuration.Model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CRON {

    public String convertToCron(int day, int hour, int minute) {
        LocalDateTime adjustedDate = adjustToFutureDay(day, hour, minute);

        // Set default values for seconds (*) and day of the week (*), and month (*)
        String seconds = "0"; // 0 second mark
        String month = "*"; // Every month
        String dayOfWeek = "*"; // Every day of the week

        // Construct the CRON expression without specifying the month
        String cronExpression = String.format("%s %d %d %d %s %s", seconds, adjustedDate.getMinute(),
                adjustedDate.getHour(), adjustedDate.getDayOfMonth(), month, dayOfWeek);

        return cronExpression;
    }

    private LocalDateTime adjustToFutureDay(int day, int hour, int minute) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime specifiedDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), day, hour, minute);

        // If the specified day/time is in the past, move to the next month
        if (specifiedDateTime.isBefore(now) || specifiedDateTime.equals(now)) {
            specifiedDateTime = specifiedDateTime.plusMonths(1);
        }

        return specifiedDateTime;
    }
}
