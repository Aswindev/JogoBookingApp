package com.crazylabs.jogobookingapp.DataModels;

/**
 * Created by aswin on 14/10/2017.
 */

public class DaysDataModel {
    private int year, month, date, time;
    private String day;

    public DaysDataModel(){

    }

    public DaysDataModel(int year,int month,int date, int time){
        this.year=year;
        this.month=month;
        this.date=date;
        this.time=time;
    }
}
