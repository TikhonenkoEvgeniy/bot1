package com.example.bot1.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCalendar {
    private static final int START_YEAR = 2007;
    private static final int DAYS_IN_WEEK = 7;
    private static final int[] CAPACITY = {31, 28, 31, 30, 31, 30, 31, 31, 30 ,31, 30, 31};

    private static final String[] MONTH_NAME = {"Январь", "Февраль", "Март", "Апрель", "Май",
            "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    static MyCalendar myCalendar;

    public static MyCalendar getInstance() {
        if (myCalendar == null) {
            myCalendar = new MyCalendar();
        }
        return myCalendar;
    }

    public List<int[]> getCalendarOfMonth(int year, int month) {
        int startIndex = getFirstWeekOfMonth(year, month);
        int endIndex = getLastWeekOfMonth(year, month);
        int[][] arrayDays = getAllDaysOfYear(year);
        List<int[]> result = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            result.add(arrayDays[i]);
        }
        return result;
    }

    public String getMonthName(int month) {
        return MONTH_NAME[month];
    }

    public int getCurrentYear() {
        return Calendar.getInstance().getTime().getYear() + 1900;
    }

    public int getCurrentMonth() {
        return Calendar.getInstance().getTime().getMonth();
    }

    public int getCurrentDay() {
        return Calendar.getInstance().getTime().getDate();
    }

    public int[] getPrevious(int year, int month) {
        if (month == 0) {
            year--;
            month = CAPACITY.length - 1;
        } else {
            month--;
        }
        return new int[]{year, month};
    }

    public int[] getNext(int year, int month) {
        if (month == CAPACITY.length - 1) {
            year++;
            month = 0;
        } else {
            month++;
        }
        return new int[]{year, month};
    }

    public boolean isPastDate(int year, int month, int day) {
        if (year < getCurrentYear()) { return true; }
        if (month < getCurrentMonth() && year == getCurrentYear()) { return true; }
        if (day < getCurrentDay() && month <= getCurrentMonth() && year <= getCurrentYear()) { return true; }
        return false;
    }

    private int[][] getAllDaysOfYear(int year) {
        int[][] result = new int[getWeeksOfYear(year)][DAYS_IN_WEEK];
        int weekPosition = 0;
        int dayPosition = getFirstDayOfYear(year);
        if (dayPosition > 1) {
            for (int index = 0; index <= dayPosition; index++) {
                result[weekPosition][index] = CAPACITY[0] - (dayPosition - index - 1);
            }
        }
        for (int index = 0; index < CAPACITY.length; index++) {
            int days = getDaysOfMonth(year, index);
            for (int day = 1; day <= days; day++) {
                result[weekPosition][dayPosition] = day;
                dayPosition++;
                if (dayPosition == DAYS_IN_WEEK) {
                    dayPosition = 0;
                    weekPosition++;
                }
            }
        }
        int additionalDays = 0;
        for (int index = 0; index < DAYS_IN_WEEK; index++) {
            if (result[weekPosition][index] == 0) {
                additionalDays++;
                result[weekPosition][index] = additionalDays;
            }
        }
        return result;
    }

    private boolean isLeap(int year) {
        return year % 4 == 0;
    }

    private int getWeeksOfYear(int year) {
        int daysInYear = getFirstDayOfYear(year) + 1;
        for (int i = 0; i < CAPACITY.length; i++) { daysInYear = daysInYear + CAPACITY[i]; }
        if (isLeap(year)) { daysInYear++; }
        if (daysInYear % DAYS_IN_WEEK > 0) { return daysInYear / DAYS_IN_WEEK + 1; }
        else return daysInYear / DAYS_IN_WEEK;
    }

    private int getDaysOfMonth(int year, int month) {
        if (month == 1 && isLeap(year)) {
            return CAPACITY[month] + 1;
        } else {
            return CAPACITY[month];
        }
    }

    private int getFirstDayOfYear(int year) {
        int day = 0;
        for (int index = START_YEAR; index < year; index++) {
            if (isLeap(index)) day = day + 2; else day++;
        }
        return day % DAYS_IN_WEEK;
    }

    private int getFirstWeekOfMonth(int year, int month) {
        if (month == 0) { return month; }
        int daysInPeriod = getFirstDayOfYear(year);
        for (int i = 0; i < month; i++) { daysInPeriod = daysInPeriod + CAPACITY[i]; }
        if (isLeap(year) && month > 1) { daysInPeriod++; }
        return daysInPeriod / DAYS_IN_WEEK;
    }
    private int getLastWeekOfMonth(int year, int month) {
        int daysInPeriod = getFirstDayOfYear(year);
        for (int i = 0; i <= month; i++) { daysInPeriod = daysInPeriod + CAPACITY[i]; }
        if (isLeap(year) && month > 1) { daysInPeriod++; }
        if (daysInPeriod % DAYS_IN_WEEK == 0) { return daysInPeriod / DAYS_IN_WEEK - 1; }
        else { return daysInPeriod / DAYS_IN_WEEK; }
    }
}
