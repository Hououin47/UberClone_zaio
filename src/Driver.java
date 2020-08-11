public class Driver extends Person {

    private Car car;
    private String licenseID;

    //constructor
    Driver (Car car, String licenseID, String name, String surname,
           String phone_number, int cash) {
        //call super
        super(name,surname,phone_number,cash);
        //add variables specific to child class driver
        this.car = car;
        this.licenseID = licenseID;
    }

    //setters
    public void setCar(Car car) {
        this.car = car;
    }

    public void setlicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    //getters
    public Car getCar() {
        return this.car;
    }

    public String getlicenseID() {
        return  this.licenseID;
    }

    //metthods
    public String toString() {
        return super.toString() + " and I am a driver";
    }
}
