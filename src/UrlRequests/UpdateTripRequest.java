package UrlRequests;

import Enums.AttractionType;

import java.util.ArrayList;

/**
 * Created by Omer Haimovich on 5/5/2017.
 */
public class UpdateTripRequest implements IUrlRequest{

    public String TripId;

    public ArrayList<AttractionType> AttractionTypes;

    @Override
    public String getURLSuffix() {
        return "trips/UpdateTrip";
    }
}
