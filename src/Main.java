import Enums.AttractionType;
import UrlRequests.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Omer Haimovich on 5/4/2017.
 */
public class Main {

    public static Gson objGson = new Gson();

    public static void main(String[] args)
    {

            try {
                JsonElement user = connectUser("omer.haimovich@gmail.com", 32.075842, 34.889338);
                //UpdateUser("omer.haimovich@gmail.com", false);

                //JsonElement usertrip = createTrip("omer.haimovich@gmail.com", 32.075842, 34.889338);
                JsonElement usertrip = getTrip("590c8fd64fca0f0e6c2db115","omer.haimovich@gmail.com", 32.075842, 34.889338);
                //UpdateTrip("590c8fd64fca0f0e6c2db115", AttractionType.BarsPubs);
                //JsonElement j = GetAttractions("590c8fd64fca0f0e6c2db115", 32.075842, 34.889338);

                //AttractionChosen("590c8fd64fca0f0e6c2db115", "ChIJqSqanqOGJRMR6qElyr7S_0E");
                //AttractionRated("590c8fd64fca0f0e6c2db115", "ChIJqSqanqOGJRMR6qElyr7S_0E", true);

                //AttractionRated("590c8fd64fca0f0e6c2db115", "ChIJseyMKsdLHRURwNM13e99zSc",true);
                //AttractionRated("590c8fd64fca0f0e6c2db115", "ChIJ15hlI51MHRURCLZn_nCoAu0", true);

                //JsonElement usertrip = getTrip("590c8fd64fca0f0e6c2db115","omer.haimovich@gmail.com", 32.075842, 34.889338);

                JsonElement j = GetAttractions("590c8fd64fca0f0e6c2db115", 32.075842, 34.889338);

                JsonElement c = null;

            }
            catch (Exception e)
            {
                e.toString();
            }
    }



public static JsonElement doPostRequest(IUrlRequest urlRequest) throws IOException
    {
        String strUrlPrefix = "http://db.cs.colman.ac.il/trippin/api/";
        strUrlPrefix += urlRequest.getURLSuffix();

        URL objURL = new URL(strUrlPrefix);
        HttpURLConnection objConnection = (HttpURLConnection)objURL.openConnection();

        objConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        objConnection.setRequestProperty("Accept-Charset", "utf-8");
        objConnection.setRequestMethod("POST");
        objConnection.setDoInput(true);
        objConnection.setDoOutput(true);

        OutputStream os = objConnection.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
        writer.write(objGson.toJson(urlRequest));
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(objConnection.getInputStream(), "utf-8"));

        String s = reader.readLine();
        String strResult = "";
        while(s != null)
        {
            strResult  += s;
            s= reader.readLine();
        }

        return objGson.fromJson(strResult, JsonElement.class);
    }

    public static JsonElement doGetRequest(IUrlRequest urlRequest) throws IOException
    {
        String strUrlPrefix = "http://db.cs.colman.ac.il/trippin/api/";
        strUrlPrefix += urlRequest.getURLSuffix();

        URL objURL = new URL(strUrlPrefix);
        HttpURLConnection objConnection = (HttpURLConnection)objURL.openConnection();

        objConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        objConnection.setRequestProperty("Accept-Charset", "utf-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(objConnection.getInputStream(), "utf-8"));

        String s = reader.readLine();
        String strResult = "";
        while(s != null)
        {
            strResult  += s;
            s= reader.readLine();
        }

        return objGson.fromJson(strResult, JsonElement.class);
    }

    public static JsonElement connectUser(String p_strEmail, Double p_dLat, Double p_dLng) throws IOException {

        ConnectUserRequest objConnectUserRequest = new ConnectUserRequest();
        objConnectUserRequest.Email = p_strEmail;
        objConnectUserRequest.Lat = p_dLat;
        objConnectUserRequest.Lng = p_dLng;

        return doPostRequest(objConnectUserRequest);


    }

    public static JsonElement createTrip(String p_strEmail, Double p_dLat, Double p_dLng) throws IOException {

        CreateTripRequest objConnectUserRequest = new CreateTripRequest();
        objConnectUserRequest.UserEmail = p_strEmail;
        objConnectUserRequest.Lat = p_dLat;
        objConnectUserRequest.Lng = p_dLng;

        return doPostRequest(objConnectUserRequest);


    }

    public static JsonElement getTrip(String p_strTripId, String p_strEmail, Double p_dLat, Double p_dLng) throws IOException {
        GetTripRequest tripRequest = new GetTripRequest();
        tripRequest.Lat = p_dLat;
        tripRequest.Lng = p_dLng;
        tripRequest.UserEmail = p_strEmail;
        tripRequest.TripId = p_strTripId;

        return doGetRequest(tripRequest);
    }

    public static void UpdateUser(String Email, Boolean notificationsOn) throws IOException {
        UpdateUserRequest obj = new UpdateUserRequest();
        obj.Email = Email;
        obj.NotificationsOn = notificationsOn;

        doPostRequest(obj);
    }

    public static void UpdateTrip(String TripId, AttractionType... attractionTypes) throws IOException {
        UpdateTripRequest obj = new UpdateTripRequest();
        obj.TripId = TripId;
        obj.AttractionTypes = new ArrayList<>();

        for (AttractionType attractionType : attractionTypes) {
            obj.AttractionTypes.add(attractionType);
        }

        doPostRequest(obj);
    }

    public static JsonElement GetAttractions(String TripId, Double p_dLat, Double p_dLng) throws IOException {
        GetAttractionRequest obj = new GetAttractionRequest();
        obj.TripId = TripId;
        obj.Lat = p_dLat;
        obj.Lng = p_dLng;

        return doGetRequest(obj);
    }

    public static void AttractionChosen(String TripId, String AttractionId) throws IOException {
        AttractionChosenRequest obj = new AttractionChosenRequest();
        obj.AttractionId = AttractionId;
        obj.TripId = TripId;

        doPostRequest(obj);
    }

    public static void AttractionRated(String TripId, String AttractionId, Boolean IsGoodAttraction) throws IOException {
        AttractionRatedRequest obj = new AttractionRatedRequest();
        obj.AttractionId = AttractionId;
        obj.TripId = TripId;
        obj.IsGoodAttraction = true;
        doPostRequest(obj);
    }

}
