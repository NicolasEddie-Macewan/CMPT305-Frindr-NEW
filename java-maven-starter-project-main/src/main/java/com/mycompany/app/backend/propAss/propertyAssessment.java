package com.mycompany.app.backend.propAss;

import com.mycompany.app.backend.fruit.location;

public class propertyAssessment {
    private final int accountNum;
    private final house house;
    private final neighbourhood neighbourhood;
    private final location location;
    private final assessment assessment;
    //private assessment[1] assessment;
    public propertyAssessment(int accountNum,house house, neighbourhood neighbourhood, location location, assessment assessment) {
        this.accountNum = validate(accountNum);
        this.house = house;
        this.neighbourhood = neighbourhood;
        this.location = location;
        this.assessment = assessment;
    }
    private int validate(int value){
        if(value<0){
            throw new IllegalArgumentException("account number must be a positive integer");
        }
        return value;
    }
    public int getAccountNum() {
        return accountNum;
    }
    public house getHouse() {
        return house;
    }
    public neighbourhood getNeighbourhood() {return neighbourhood;}
    public location getLocation() {
        return location;
    }
    public assessment getAssessment() {
        return assessment;
    }
    @Override
    public String toString() {
        return "\naccount number = "+accountNum+"\n"+house.toString()+"\n"+neighbourhood.toString()+"\n"+location.toString()+"\n"+assessment.toString();
    }
    public boolean equals(propertyAssessment o) {
        return (this.getAccountNum() == o.getAccountNum());
    }
    @Override
    public int hashCode(){
        return accountNum;
    }
   }
