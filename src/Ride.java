public abstract class Ride {

    //variables
    String estartingPoint;
    String endingPoint;

    //methods that can be overriden
    public abstract double calculateDistance(String startingPoint, String endingPoint);
    public abstract Driver assignDriver();
    public abstract double calculateCost(String startingPoint, String endingPoint);
    public abstract void completePayment(Driver driver, Passenger passenger);

}
