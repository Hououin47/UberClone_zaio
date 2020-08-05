public class Driver extends Person {

    private Car car;
    private String licenseID;

    Driver (Car car, String licenseID, String name, String surname,
           String phone_number, int cash) {
        super(name,surname,phone_number,cash);
        this.car = car;
        this.licenseID = licenseID;
    }

    public void setCar(Car car){
        this.car = car;
    }

    public void setlicenseID(String licenseID){
        this.licenseID = licenseID;
    }

    public Car getCar(){
        return this.car;
    }

    public String getlicenseID(){
        return  this.licenseID;
    }

    public String toString(){
        return super.toString() + " and I am a driver";
    }
}
