package com.crazylabs.jogobookingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazylabs.jogobookingapp.R;

/**
 * Created by aswin on 14/10/2017.
 */

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.TopViewHolder> {
    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_day_circle_layout, viewGroup, false);
        return new DaysRecyclerViewAdapter.TopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }


    public static class TopViewHolder extends RecyclerView.ViewHolder {
        protected TextView vYear;
        protected TextView vMonth;
        protected TextView vDate;
        protected TextView vDay;
        protected TextView vTime;

        public TopViewHolder(View v) {
            super(v);
            vDate = (TextView) v.findViewById(R.id.fragment_day_circle_date_textview);
            vDay = (TextView) v.findViewById(R.id.fragment_day_circle_day_textview);
        }
    }
}
