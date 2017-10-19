package com.crazylabs.jogobookingapp.Adapters;

/**
 * Created by eldho on 17-10-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.bumptech.glide.Glide;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.MainActivity;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;
import com.crazylabs.jogobookingapp.VenueDetailActivity;

import java.util.List;

import static java.security.AccessController.getContext;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.MyViewHolder> {

    private Context mContext;
    private List<VenueDataModel> venueList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView venueLocation;
        public ImageView venueThumbnail;
        public LinearLayout viewDetails;

        public MyViewHolder(View view) {
            super(view);
            venueLocation = (TextView) view.findViewById(R.id.venue_location);
            venueThumbnail = (ImageView) view.findViewById(R.id.venue_thumbnail);
            viewDetails = (LinearLayout) view.findViewById(R.id.view_details);
        }
    }


    public VenueAdapter(Context mContext, List<VenueDataModel> venueList) {
        this.mContext = mContext;
        this.venueList = venueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VenueDataModel album = venueList.get(position);
        holder.venueLocation.setText(album.getVenue_location());


        // loading album cover using Glide library
        Glide.with(mContext).load(album.getVenue_thumbnail()).into(holder.venueThumbnail);

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.viewDetails);
//                Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();


//                ItemClickSupport.addTo(recListteam).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        Log.d("Reclisten", String.valueOf(position));
//                        TopDataModel clickedPlayer=result.get(position);
//                        Log.d("Reclisten", clickedPlayer.getUid());
//
//                        Intent k = new Intent(getContext(), SearchTeamActivity.class);
//                        k.putExtra("tid",clickedPlayer.getUid());
//                        startActivity(k);
//                    }
//                });

                Intent k = new Intent(view.getContext(),VenueDetailActivity.class);
                k.putExtra("vloc",album.getVenue_location());
                k.putExtra("vpic",album.getVenue_thumbnail());
                view.getContext().startActivity(k);

            }
        });
    }



    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }
//
//    /**
//     * Click listener for popup menu items
//     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }
}
