package com.mycompany.app.backend.propAss;


public class neighbourhood {
    private final String neighbourId;
    private final String neighbourhood;
    private final String ward;
    //neighbourhood(row[5],row[6],row[7])
    public neighbourhood(String neighbourId, String neighbourhood, String ward) {
        this.neighbourId = validate_neighbourId(neighbourId);
        this.neighbourhood = validate_neighbourhood(neighbourhood);
        this.ward = ward;
    }
    private String validate_neighbourhood(String neighbourhood) {
        if(neighbourhood.compareTo("") ==0) {
            throw new IllegalArgumentException("Neighbourhood must not be empty");
        }
        return neighbourhood;
    }
    private String validate_neighbourId(String neighbourId) {
        if(neighbourId.compareTo("") ==0) {
            return neighbourId;
        }
        else if (neighbourId.matches("[0-9]+")){
            return neighbourId;
        }else {
            throw new IllegalArgumentException("Neighbourhood must contain an integer");
        }
    }
    public Integer getNeighbourId() {
        if (neighbourId.compareTo("")==0){
            return -1;
        }
        else
            return Integer.parseInt(neighbourId);
    }

    public String getHood() {
       return neighbourhood;
    }

    public String getWard() {
        return ward;
    }
    @Override
    public String toString() {
        if (ward.compareTo("")==0) {
            return "Neighbourhood = "+neighbourhood;
        }
        else {
            return "Neighbourhood = " + neighbourhood + " Ward = (" + ward + ")";
        }
    }
}
