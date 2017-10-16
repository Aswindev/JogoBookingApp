package com.crazylabs.jogobookingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazylabs.jogobookingapp.DataModels.DaysDataModel;
import com.crazylabs.jogobookingapp.R;

import java.util.List;

/**
 * Created by aswin on 14/10/2017.
 */

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.DaysViewHolder> {

    private List<DaysDataModel> daysList;
    private Context mContext;

    public DaysRecyclerViewAdapter(List<DaysDataModel> daysList, Context context) {
        this.daysList = daysList;
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    @Override
    public void onBindViewHolder(final DaysRecyclerViewAdapter.DaysViewHolder daysViewHolder, int i) {
        final DaysDataModel ci = daysList.get(i);
        daysViewHolder.vDay.setText(ci.day);
        daysViewHolder.vDate.setText(String.valueOf(ci.date));
        daysViewHolder.vMonth.setText(String.valueOf(ci.month));
    }


    @Override
    public DaysRecyclerViewAdapter.DaysViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_day_circle_layout, viewGroup, false);
        return new DaysRecyclerViewAdapter.DaysViewHolder(itemView);
    }

    public static class DaysViewHolder extends RecyclerView.ViewHolder {
        protected TextView vDate;
        protected TextView vDay;
        protected TextView vMonth;

        public DaysViewHolder(View v) {
            super(v);
            vDate = (TextView) v.findViewById(R.id.fragment_day_circle_date_textview);
            vDay = (TextView) v.findViewById(R.id.fragment_day_circle_day_textview);
            vMonth = (TextView) v.findViewById(R.id.fragment_day_circle_month_textview);
        }
    }
}

