package com.mycompany.app.backend.propAss;//ID 3109665
// name Owen Woollam

import java.util.ArrayList;
import java.util.List;

public class assessment {
    private final int qty;
    private List<Integer> ratings;
    private List<String> assessmentClasses ;

    private int validate(int value){
        if(value<=100 && value >0){
            return value;
        }
        throw new IllegalArgumentException("Assessment rating must be within the range 0 and 100");
    }
    //12,13,14, 15,16,17
    public assessment(){
        this.ratings = new ArrayList<>();
        this.assessmentClasses = new ArrayList<>();
        this.qty = 0;
    }
    public assessment(int rating, String assClass) {
        this.ratings = new ArrayList<>();
        this.assessmentClasses =  new ArrayList<>();
        this.ratings.add(validate(rating));
        this.assessmentClasses.add(assClass);
        this.qty = 1;
    }

    public assessment(int rating, String assessmentClass, int rating2, String assessmentClass2) {
        this.ratings = new ArrayList<>();
        this.assessmentClasses =  new ArrayList<>();
        this.ratings.add(validate(rating));
        this.ratings.add(validate(rating2));
        this.assessmentClasses.add(assessmentClass);
        this.assessmentClasses.add(assessmentClass2);
        this.qty = 2;
    }

    public assessment(int rating, String assessmentClass, int rating2, String assessmentClass2, int rating3, String assessmentClass3) {
        this.ratings = new ArrayList<>();
        this.assessmentClasses =  new ArrayList<>();
        this.ratings.add(validate(rating));
        this.ratings.add(validate(rating2));
        this.ratings.add(validate(rating3));
        this.assessmentClasses.add(assessmentClass);
        this.assessmentClasses.add(assessmentClass2);
        this.assessmentClasses.add(assessmentClass3);
        this.qty = 3;
    }

    public List<Integer> getRating() {
        return this.ratings;}

    public List<String> getAssessmentClass() {
        return this.assessmentClasses;
    }

    @Override
    public String toString() {
        if (qty == 1) {return "assessment class = ["+ assessmentClasses.getFirst() + " " +ratings.getFirst()+"%]";}
        if (qty == 2) {return "assessment class = ["+assessmentClasses.getFirst()+" "+ratings.getFirst()+"%, "+assessmentClasses.get(1)+" "+ratings.get(1)+"%]";}
        if (qty == 3) {return "assessment class = ["+assessmentClasses.getFirst()+" "+ratings.getFirst()+"%, "+assessmentClasses.get(1)+" "+ratings.get(1)+"%, "+assessmentClasses.get(2)+" "+ratings.get(2)+"%]";}
        return  null;
    }
}