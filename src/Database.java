import java.io.*;
import java.util.*;

public class Database {

    //instance methods
    private String filePathToCSV;
    private int numberOfXL;
    private int numberOfX;
    private Driver[] drivers;

    Database() {}

    /**
     * Method to count lines of given CSV file
     *
     * returns number of lines (int)
     */
    public int countLinesInCSV(String filename) {
        int line = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String row = null;
            while ((row = br.readLine()) != null) {
                line++;
            }
            br.close();
        }
        catch(Exception e) {
            //catch general exception
            System.out.println("Error: " + e.toString());
        }
        return line;
    }

    /**
     * Method to print the Contents of the drivers array
     * int an array format
     */
    public void printArray(Driver[] drivers) {
        System.out.print("[ ");
        for (Driver d: drivers) {
            System.out.print(d);
            System.out.printf(",%n");
        }
        System.out.print(" ]");
    }

    //getters
    
    /**
     * Method to read CSV file and extract data to populate drivers array.
     * One driver per line
     *
     */
    public void getDriversArray() {

        try {
            FileReader fr = new FileReader("drivers.csv");
            BufferedReader br = new BufferedReader(fr);
            String row = null;
            int i = 0;
            Car c = null;
            Driver d = null;
            this.drivers = new Driver[countLinesInCSV("drivers.csv")-1];
            while ((row = br.readLine()) != null) {
                if (i > 0) {
                    String[] data = row.split(",");
                    c = new Car(data[5].trim(), data[6].trim(),
                                data[7].trim(), data[8].trim());
                    d = new Driver(c, data[0].trim(), data[1].trim(),
                                   data[2].trim(), data[3].trim(),
                                   Integer.parseInt(data[4].trim()));

                    if (data[8].trim().equals("X")) this.numberOfX++;
                    if (data[8].trim().equals("XL")) this.numberOfXL++;

                    this.drivers[i-1] = d;
                }
                i++;
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    /**
     * Get array of only certain type of vehicle.
     *
     */
    public Driver[] getArrayByVehicleType(String vehicleType) {

        Driver[] Xdrivers = new Driver[this.numberOfX];
        Driver[] XLdrivers = new Driver[this.numberOfXL];

        int x = 0, xl = 0;
        for (int i=0; i<this.drivers.length; i++) {
            if (this.drivers[i].getCar().getVehicleType().equals("X"))
                Xdrivers[x++] = this.drivers[i];
            if (this.drivers[i].getCar().getVehicleType().equals("XL"))
                XLdrivers[xl++] = this.drivers[i];
        }

        if (vehicleType.equals("X")) return Xdrivers;
        return XLdrivers;
    }
}
