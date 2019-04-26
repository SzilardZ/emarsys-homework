package com.szilard;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;


public class DueDateCalculator {

    public static void main(String[] args) {

        Calendar calendar = setDate(2019, 4, 25, 10, 20);

        int turnaroundTime = 5;

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Bug report date: " + calendar.getTime().toString());
        calculateDueDate(calendar, turnaroundTime);
        System.out.println("----------------------------------------------------------");
        System.out.println("THE DUE DATE:    " + calendar.getTime().toString());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    private static Calendar setDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    private static void calculateDueDate(Calendar date, int turnaroundTime) {

        int actualHour = date.get(HOUR_OF_DAY);
        int numberOfFullDays = turnaroundTime / 8;
        int remainingHours = turnaroundTime % 8;

        if (!isWorkingDay(date)) {
            getNextWorkingDay(date);
            setTimeToFirstWorkingHour(date);
        }

        if (!isWorkingHour(date) && date.get(HOUR_OF_DAY) < 9 ) {
            setTimeToFirstWorkingHour(date);
        } else if (date.get(HOUR_OF_DAY) >= 17) {
            setTimeToFirstWorkingHour(date);
            getNextWorkingDay(date);
        }

        if (actualHour + remainingHours >= 17) {
            remainingHours = ((actualHour + remainingHours) - 17);
            date.set(HOUR_OF_DAY, (9 + remainingHours));
        } else {
            date.set(HOUR_OF_DAY, (actualHour + remainingHours));
        }

        for (int i = 0; i < numberOfFullDays; i++) {
            getNextWorkingDay(date);
        }

    }

    private static void getNextWorkingDay(Calendar date) {

        switch (date.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.FRIDAY:
                date.add(DAY_OF_MONTH, 3);
                break;
            case Calendar.SATURDAY:
                date.add(DAY_OF_MONTH, 2);
                break;
            default:
                date.add(DAY_OF_MONTH, 1);
                break;
        }

    }

    private static int getDayOfWeek(Calendar date) {
        return date.get(Calendar.DAY_OF_WEEK);
    }

    private static boolean isWorkingDay(Calendar date) {
        int dayOfWeek = getDayOfWeek(date);
        return dayOfWeek != 7 && dayOfWeek != 1;
    }

    private static boolean isWorkingHour(Calendar date) {
        int hour = date.get(HOUR_OF_DAY);
        return hour >= 9 && hour < 17;
    }

    private static void setTimeToFirstWorkingHour(Calendar date) {
        date.set(HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
    }

}
