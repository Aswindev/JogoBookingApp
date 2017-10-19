package com.crazylabs.jogobookingapp.DataModels;

import java.sql.Time;
import java.util.Date;

/**
 * Created by eldho on 17-10-2017.
 */

public class CartDataModel {
    private String booking_location;
    private Date booking_date;
    private String booking_day;
    private Time booking_time;
    private Float booking_amount;
    private String booking_ground;

    public CartDataModel() {
    }

    public CartDataModel( String booking_location, Date booking_date, String booking_day, Time booking_time, Float booking_amount,String booking_ground) {
        this.booking_location = booking_location;
        this.booking_date = booking_date;
        this.booking_day = booking_day;
        this.booking_time = booking_time;
        this.booking_ground = booking_ground;
        this.booking_amount = booking_amount;

    }

    public String getBooking_location() {
        return booking_location;
    }

    public void setBooking_location(String booking_location) {
        this.booking_location = booking_location;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public String getBooking_day() {
        return booking_day;
    }

    public void setBooking_day(String booking_day) {
        this.booking_day = booking_day;
    }

    public Time getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(Time booking_time) {
        this.booking_time = booking_time;
    }

    public Float getBooking_amount() {
        return booking_amount;
    }

    public void setBooking_amount(Float booking_amount) {
        this.booking_amount = booking_amount;
    }

    public String getBooking_ground() {
        return booking_ground;
    }

    public void setBooking_ground(String booking_ground) {
        this.booking_location = booking_ground;
    }

}
