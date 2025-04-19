package com.example.salesmanagement.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : "";
    }
    
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return dateTimeStr != null && !dateTimeStr.isEmpty() 
                ? LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER) 
                : null;
    }
    
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }
    
    public static LocalDateTime startOfDay(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate().atStartOfDay() : null;
    }
    
    public static LocalDateTime endOfDay(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate().atTime(23, 59, 59) : null;
    }
} 