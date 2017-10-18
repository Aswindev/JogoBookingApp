package com.crazylabs.jogobookingapp.DataModels;

/**
 * Created by aswin on 18/10/2017.
 */

public class SelectedSlotDataModel {
    public int time;
    public int year, date;
    public String day, month;
    public Boolean timeSelected=false;
    public int price;



    public SelectedSlotDataModel(DaysDataModel daysDataModel, int time) {
        this.year=daysDataModel.year;
        this.date=daysDataModel.date;
        this.day=daysDataModel.day;
        this.month=daysDataModel.month;
        this.time=time;    
    }

    public void setTimeSelected(Boolean timeSelected) {
        this.timeSelected = timeSelected;
    }
    @Override
    public boolean equals (Object o) {
        if (this.date == ((SelectedSlotDataModel) o).date && this.time==((SelectedSlotDataModel) o).time) return true;
        return false;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
