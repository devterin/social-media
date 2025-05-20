package com.devterin.socialmedia.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class DateTimeUtil {

    public static String getTimeDifference(Date createdAt) {
        LocalDateTime createdDateTime = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime currentDateTime = LocalDateTime.now();

        long diffInNano = Duration.between(createdDateTime, currentDateTime).toNanos();
        long diffInSeconds = Duration.between(createdDateTime, currentDateTime).getSeconds();
        long diffInMinutes = Duration.between(createdDateTime, currentDateTime).toMinutes();
        long diffInHours = Duration.between(createdDateTime, currentDateTime).toHours();
        long diffInDays = Duration.between(createdDateTime, currentDateTime).toDays();
        long diffInMonths = diffInDays / 30;
        long diffInYears = diffInDays / 365;

        if (diffInNano > 0 && diffInSeconds <= 0) {
            return "Just now";
        } else if (diffInSeconds < 60) {
            return diffInSeconds + " seconds ago";
        } else if (diffInMinutes < 60) {
            return diffInMinutes + " minutes ago";
        } else if (diffInHours < 24) {
            return diffInHours + " hours ago";
        } else if (diffInDays < 30) {
            return diffInDays + " days ago";
        } else if (diffInMonths < 12) {
            return diffInMonths + " months ago";
        } else {
            return diffInYears + " years ago";
        }
    }
}
