package com.crazylabs.jogobookingapp.Adapters;

/**
 * Created by eldho on 19-10-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.R;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<CartDataModel> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cartLocation;
        public TextView cartDate;
        public TextView cartDay;
        public TextView cartTime;
        public TextView cartAmount;
        public TextView cartGround;
        public LinearLayout cartDelete;


        public MyViewHolder(View view) {
            super(view);
            cartLocation = (TextView) view.findViewById(R.id.cart_location);
            cartDate = (TextView) view.findViewById(R.id.cart_date);
            cartDay = (TextView) view.findViewById(R.id.cart_day);
            cartTime = (TextView) view.findViewById(R.id.cart_time);
            cartAmount = (TextView) view.findViewById(R.id.cart_amount);
            cartGround = (TextView) view.findViewById(R.id.cart_ground);
            cartDelete = (LinearLayout) view.findViewById(R.id.cart_delete);
        }
    }


    public CartAdapter(Context mContext, List<CartDataModel> carteList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CartDataModel album = cartList.get(position);
        holder.cartLocation.setText(album.getBooking_location());
        holder.cartDate.setText((CharSequence) album.getBooking_date());
        holder.cartDay.setText(album.getBooking_day());
        holder.cartGround.setText(album.getBooking_ground());
        holder.cartTime.setText((CharSequence) album.getBooking_time());
        holder.cartAmount.setText(String.valueOf(album.getBooking_amount()));


        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Delete Cart", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
