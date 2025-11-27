package com.mycompany.app.backend.fruit;

public class CityLocation {
    private String Neighbourhood;
    private String Location;
    private location coordinates;
    //=================================================================
    //=================================================================
    public CityLocation(String Neighbourhood, String Location,Double Latitude,Double Longitude) {
        this.Neighbourhood = Neighbourhood;
        this.Location = Location;
        this.coordinates = new location(Latitude,Longitude);
    }
    //=================================================================
    //=================================================================
    public String getNeighbourhood() {
        return Neighbourhood;
    }
    //=================================================================
    //=================================================================
    public String getLocation() {
        return Location;
    }
    //=================================================================
    //=================================================================
    public location getCoordinates() {
        return coordinates;
    }
    //=================================================================
    //=================================================================
    @Override
    public String toString() {
        return "neighbourhood = "+this.Neighbourhood+"\nlocation = "+this.Location+this.getCoordinates().toString();
    }
}

