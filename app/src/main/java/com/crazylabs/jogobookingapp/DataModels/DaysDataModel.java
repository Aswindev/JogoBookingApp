package com.crazylabs.jogobookingapp.DataModels;

import android.util.Log;

import java.util.Date;

/**
 * Created by aswin on 14/10/2017.
 */

public class DaysDataModel {
    public int year, date;
    public String day, month;
    public Date fullDate;

    public DaysDataModel(int year,String month,int date, String day){
        this.year=year;
        this.month=month;
        this.date=date;
        this.day=day;
//        Log.d("DatesS", "DaysDataModel: "+year+" "+month+" "+date+" "+" "+day);
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }
}
