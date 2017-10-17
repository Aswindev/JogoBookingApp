package com.crazylabs.jogobookingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazylabs.jogobookingapp.DataModels.DaysDataModel;
import com.crazylabs.jogobookingapp.R;

import java.util.List;

import static com.crazylabs.jogobookingapp.Fragments.BookingFragment.selectedPosition;

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

        if (selectedPosition==i) {
            daysViewHolder.vRelativeLayout.setBackgroundResource(R.drawable.circle_black);
            daysViewHolder.vDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            daysViewHolder.vDay.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            daysViewHolder.vMonth.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            daysViewHolder.vRelativeLayout.setBackgroundResource(R.drawable.circle);
            daysViewHolder.vDate.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            daysViewHolder.vDay.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            daysViewHolder.vMonth.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
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
        protected RelativeLayout vRelativeLayout;

        public DaysViewHolder(View v) {
            super(v);
            vDate = (TextView) v.findViewById(R.id.fragment_day_circle_date_textview);
            vDay = (TextView) v.findViewById(R.id.fragment_day_circle_day_textview);
            vMonth = (TextView) v.findViewById(R.id.fragment_day_circle_month_textview);
            vRelativeLayout= (RelativeLayout) v.findViewById(R.id.fragment_day_circle_container_relative_layout);
        }
    }
}

