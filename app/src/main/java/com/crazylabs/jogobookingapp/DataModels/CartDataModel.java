package com.crazylabs.jogobookingapp.DataModels;

import java.sql.Time;
import java.util.Date;

/**
 * Created by eldho on 17-10-2017.
 */

public class CartDataModel {
    private String booking_location;
    private int booking_date, booking_year;
    private String booking_day, booking_month;
    private int booking_time;
    private int booking_amount;
    private int booking_groundType;

    private Date fullDate;

    public CartDataModel() {
    }

    public CartDataModel( String booking_location, int booking_date, String booking_month,int booking_year, String booking_day, int booking_time, int booking_amount,int booking_groundType) {
        this.booking_location = booking_location;
        this.booking_date = booking_date;
        this.booking_month=booking_month;
        this.booking_year=booking_year;
        this.booking_day = booking_day;
        this.booking_time = booking_time;
        this.booking_groundType = booking_groundType;
        this.booking_amount = booking_amount;

    }

    public String getBooking_location() {
        return booking_location;
    }

    public void setBooking_location(String booking_location) {
        this.booking_location = booking_location;
    }

    public int getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(int booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_month() {
        return booking_month;
    }

    public void setBooking_month(String booking_month) {
        this.booking_month = booking_month;
    }

    public int getBooking_year() {
        return booking_year;
    }

    public void setBooking_year(int booking_year) {
        this.booking_year = booking_year;
    }

    public String getBooking_day() {
        return booking_day;
    }

    public void setBooking_day(String booking_day) {
        this.booking_day = booking_day;
    }

    public int getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(int booking_time) {
        this.booking_time = booking_time;
    }

    public int getBooking_amount() {
        return booking_amount;
    }

    public void setBooking_amount(int booking_amount) {
        this.booking_amount = booking_amount;
    }

    public int getBooking_groundType() {
        return booking_groundType;
    }

    public void setBooking_groundType(int booking_groundType) {
        this.booking_groundType = booking_groundType;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public Date getFullDate() {
        return fullDate;
    }
}
