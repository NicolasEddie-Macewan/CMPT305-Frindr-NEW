package com.mycompany.app.backend.propAss;//ID 3109665
// name Owen Woollam

public class house {
    private final String suite;
    private final String houseNum;
    private final int garage;
    private final int assessedValue;
    private final String streetName;

    //house(data[1],data[2],data[4],data[8],data[3])
    public house(String suite, String houseNum, int garage, int assessedValue, String streetName) {
        this.suite = suite;
        this.houseNum = houseNum;
        this.garage = check_garage(garage);
        this.assessedValue = check_value(assessedValue);
        this.streetName = streetName;
    }

    private int check_value(int value){
        if(value < 0){
            throw new IllegalArgumentException("Value must be positive");
        }
        return value;
    }
    private int check_garage(int value){
        if (value == 1 || value ==0){
            return value;
        }
        throw new IllegalArgumentException("Garage must 0 or 1");

    }


    public int getSuite() {
        try {
            return Integer.parseInt(suite);
        }catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getHouseNum() {
        return houseNum;
    }

    public int getGarage() {
        return garage;
    }

    public Integer getAssessedValue() {
        return assessedValue;
    }

    public String getStreetName() {
        return streetName;
    }

    @Override
    public String toString() {
        if (suite.compareTo("") == 0) {
            return "address = "+houseNum+" "+streetName+"\nAssessed value = " +assessedValue;

        } else {
            return "address = "+suite+" "+houseNum+" "+streetName+"\nAssessed value = " +assessedValue;
        }
    }
}

