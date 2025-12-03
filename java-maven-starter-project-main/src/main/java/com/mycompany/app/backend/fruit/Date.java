package com.mycompany.app.backend.fruit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {
    private Integer year;
    private Integer month;
    private Integer day;
    //Pattern datePattern = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$"); // (4digits / 2 digits /2 digits)
    Pattern datePattern1 = Pattern.compile("\\d{4}(-|/)(0?[1-9]|1[012])(-|/)(0?[1-9]|[12][0-9]|3[01])$");
    // format YYYY-MM-DD
    public Date(String date) {
//        Matcher matcher = datePattern.matcher(date);
        Matcher matcher1 = datePattern1.matcher(date);
        if (matcher1.find()) {
            String[] dateArr = date.split("/|-");
            this.year = Integer.parseInt(dateArr[0]);
            this.month = Integer.parseInt(dateArr[1]);
            this.day = Integer.parseInt(dateArr[2]);
        }
//        else if (matcher1.find()) {
//            String[] dateArr = date.split("/");
//            this.year = Integer.parseInt(dateArr[0]);
//            this.month = Integer.parseInt(dateArr[1]);
//            this.day = Integer.parseInt(dateArr[2]);

        else{throw new IllegalArgumentException("Invalid date format YYYY-MM-DD");}
    }


    public Integer getYear() {
        return year;
    }
    public Integer getMonth() {
        return month;
    }
    public Integer getDay() {
        return day;
    }
    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }

    public Integer compare(Date o) {
        int compval =  this.compareYear(o);
        if (compval < 0 ){return -1;}
        if (compval > 0 ){return 1;}
        compval =  this.compareMonth(o);
        if (compval < 0 ){return -1;}
        if (compval > 0 ){return 1;}
        compval =  this.compareDay(o);
        if (compval < 0 ){return -1;}
        if (compval > 0 ){return 1;}
        return 0;
    }
    private Integer compareYear(Date o){
        int compare =  this.year- o.getYear();
        return Integer.compare(compare, 0);
    }
    private Integer compareMonth(Date o){
        int compare = this.month-o.getMonth();
        return Integer.compare(compare, 0);
    }
    private Integer compareDay(Date o){
        int compare = this.day- o.getDay();
        return Integer.compare(compare, 0);
    }
}

