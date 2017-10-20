package com.crazylabs.jogobookingapp.DataModels;

import java.sql.Time;
import java.util.Date;

/**
 * Created by aswin on 18/10/2017.
 */

public class SelectedSlotDataModel {
    public int time;
    public int year, date;
    public String day, month;
    public int price;
    public Date fullDate;

    public String location;
    public int locationCode;
    public int groundType;




    public SelectedSlotDataModel(DaysDataModel daysDataModel, int time, String location, int groundType) {
        this.year=daysDataModel.year;
        this.date=daysDataModel.date;
        this.day=daysDataModel.day;
        this.month=daysDataModel.month;
        this.time=time;
        this.location=location;
        this.groundType=groundType;
    }

    @Override
    public boolean equals (Object o) {
        if (this.date == ((SelectedSlotDataModel) o).date && this.time==((SelectedSlotDataModel) o).time) return true;
        return false;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public void setLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }
}
