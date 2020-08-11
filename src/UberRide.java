import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.*;
import java.util.*;

public class UberRide extends Ride {

    //instance variables
    private String startpoint;
    private String endpoint;
    private double price;
    private double rate;
    private Driver driver;
    private Passenger passenger;
    private double distance;


    //constructors
    public UberRide() {}

    public UberRide(String startpoint, String endpoint, Passenger passenger){
        super();
        this.startpoint = startpoint;
        this.endpoint = endpoint;
        this.price = price;
        this.rate = rate;
        this.driver = null;
        this.passenger = passenger;
    }

    //Assign driver to ride this instance
    public Driver assignDriver() {
        Database DB = new Database();
        DB.getDriversArray();
        Driver[] drivers = DB.getArrayByVehicleType("XL");

        Random rand = new Random();
        int randIndex = rand.nextInt(drivers.length-1);

        this.driver = drivers[randIndex];
        return drivers[randIndex];
    }

    //do payment transaction
    public void completePayment(Driver driver, Passenger passenger) {
        double driverBalance = driver.getCash();
        double passengerBalance = passenger.getCash();

        //add cash to driver wallet
        driver.setCash(driverBalance + this.price);
        System.out.println("Adding R" + (int)this.price +
                           " to driver account. Account bal: R" +
                           driver.getCash());

        //deduct cash from passanger wallet
        passenger.setCash(passengerBalance - this.price);
        System.out.println("Deducting R" + (int)this.price +
                           " from passenger account. Account bal: R" +
                           passenger.getCash());
    }

    //Calculate distance between start and end point
    public double calculateDistance(String startingPoint, String endingPoint) {
        double distance = 0.0;
        try {
            //empty cnstructor to use distance functions
            UberRide uberRide = new UberRide();
            distance = uberRide.MyGETRequest(startingPoint, endingPoint);
        }
        catch(IOException ex){
            System.out.println (ex.toString());
        }

        this.distance = distance/1000;
        System.out.println("The distance is: " + this.distance + " km");
        return this.distance;
    }

    public double calculateCost(String startingPoint, String endingPoint) {

        double cost = 0.0;
        UberRide uberRide = new UberRide();
        double distance = uberRide.calculateDistance(startingPoint,
                                                     endingPoint);
        cost = distance * this.driver.getCar().getBaseRate();
        this.price = cost;
        System.out.println("The cost is: R" + (int)this.price);
        return cost;
    }

    //use GET and JSON to access information from url
    public double MyGETRequest(String startingPoint,
                               String endingPoint) throws IOException {
        //create url, multi-line for char limit
        String url = "https://maps.googleapis.com/maps/api/";
        url += "distancematrix/json?origins=";
        url += startingPoint + ",SA&destinations=" + endingPoint;
        url += ",SA&departure_time=now&";
        url += "key=AIzaSyCs2UIPeA_ygj6aDL45ta9ZdJu3Mo1PIOs";
        URL urlForGetRequest = new URL(url);
        
        String readLine = null;
        HttpURLConnection conection =
            (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();

        UberRide uberRide = new UberRide();
        double distance = 0.0;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            } in .close();
            String distanceAsString =
                uberRide.retrieveDistanceAsString(response.toString());
            distance = Double.parseDouble(distanceAsString);
        } else {
            System.out.println("GET NOT WORKED");
        }

        return distance;
    }

    //used in above metgod to get data from JSON object
    public String retrieveDistanceAsString(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        JsonObject jsonObj = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = jsonObj.getAsJsonArray("rows");

        JsonElement je = null;
        Iterator<JsonElement> iterator = jsonArray.iterator();
         while(iterator.hasNext()) {
             je = iterator.next();
             jsonArray = je.getAsJsonObject().getAsJsonArray("elements");
         }

         iterator = jsonArray.iterator();
         while(iterator.hasNext()) {
             je = iterator.next();
             jsonObj = je.getAsJsonObject().get("distance").getAsJsonObject();
         }

         return jsonObj.get("value")+"";


    }
}
