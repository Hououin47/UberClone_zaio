public class Passenger extends Person {

    String email;
    
    // constructor
    Passenger (String email, String name, String surname,String phone_number,
               int cash) {
        //call super (person) constructor
        super(name,surname,phone_number, cash);
        this.email = email;
    }

    //setters
    public void setEmail(String email){
        this.email  = email;
    }
    
    //getters
    public String getEmail() {
        return this.email;
    }

    public String toString(){
        return super.toString() + " and I am a passenger";
    }
}
