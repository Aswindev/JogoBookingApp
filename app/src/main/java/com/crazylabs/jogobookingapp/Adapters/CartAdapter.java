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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crazylabs.jogobookingapp.CartActivity;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.MainActivity;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.Utils.CartListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<CartDataModel> cartList;
    private CartListener mListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
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


    public CartAdapter(Context mContext, List<CartDataModel> cartList, CartListener cartListener) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.mListener= cartListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        final CartDataModel cart = cartList.get(position);
        Date month=cart.getFullDate();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        String time = null;
        switch (cart.getBooking_time()) {
            case 0: time="5:00AM";
                break;
            case 1: time="6:00AM";
                break;
            case 2: time="7:00AM";
                break;
            case 3: time="8:00AM";
                break;
            case 4: time="9:00AM";
                break;
            case 5: time="10:00AM";
                break;
            case 6: time="11:00AM";
                break;
            case 7: time="12:00PM";
                break;
            case 8: time="1:00PM";
                break;
            case 9: time="2:00PM";
                break;
            case 10: time="3:00PM";
                break;
            case 11: time="4:00PM";
                break;
            case 12: time="5:00PM";
                break;
            case 13: time="6:00PM";
                break;
            case 14: time="7:00PM";
                break;
            case 15: time="8:00PM";
                break;
            case 16: time="9:00PM";
                break;
            case 17: time="10:00PM";
                break;
            case 18: time="11:00PM";
                break;
            case 19: time="12:00AM";
                break;
        }

        holder.cartLocation.setText("Jogo Football Arena - "+cart.getBooking_location());
        holder.cartDate.setText(cart.getBooking_date()+" "+monthFormat.format(month.getTime())+" "+cart.getBooking_year());
        holder.cartDay.setText(dayFormat.format(month.getTime()));
        holder.cartGround.setText(String.valueOf(cart.getBooking_groundType())+"-a-side");
        holder.cartTime.setText(time);
        holder.cartAmount.setText("₹ "+String.valueOf(cart.getBooking_amount()));


        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Item deleted", Toast.LENGTH_SHORT).show();
                cartList.remove(position);
                MainActivity.selectedSlotList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount() - position);

                mListener.itemDeleted();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
