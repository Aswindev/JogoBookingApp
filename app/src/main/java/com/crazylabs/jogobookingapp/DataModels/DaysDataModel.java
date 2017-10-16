package com.crazylabs.jogobookingapp.DataModels;

import android.util.Log;

/**
 * Created by aswin on 14/10/2017.
 */

public class DaysDataModel {
    public int year, date;
    public String day, month, time;

    public DaysDataModel(int year,String month,int date, String day){
        this.year=year;
        this.month=month;
        this.date=date;
        this.day=day;
        Log.d("DatesS", "DaysDataModel: "+year+" "+month+" "+date+" "+time+" "+day);
    }

    public void setTime(String time) {
        this.time = time;
        Log.d("DatesS", "DaysDataModel: "+time);
    }
}
