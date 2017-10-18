package com.crazylabs.jogobookingapp.DataModels;

/**
 * Created by eldho on 17-10-2017.
 */

public class VenueDataModel {
    private String venue_location;
    private int venue_thumbnail;

    public VenueDataModel() {
    }

    public VenueDataModel(String venue_location, int venue_thumbnail) {
        this.venue_location = venue_location;
        this.venue_thumbnail = venue_thumbnail;
    }

    public String getVenue_location() {
        return venue_location;
    }

    public void setVenue_location(String venue_location) {
        this.venue_location = venue_location;
    }

    public int getVenue_thumbnail() {
        return venue_thumbnail;
    }

    public void setVenue_thumbnail(int venue_thumbnail) {this.venue_thumbnail = venue_thumbnail;}
}
