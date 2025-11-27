package com.mycompany.app.backend.propAss;


import com.mycompany.app.backend.fruit.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class propertyAssessments {
    private final List<propertyAssessment> assessmentsList;
    private final String fileName;
    //======================================================================
    //======================================================================
    public propertyAssessments(String filename) throws IOException {
        this.fileName = filename;
        this.assessmentsList = sortList(readDataList(fileName));
        }
    //======================================================================
    //======================================================================
    public propertyAssessments(List<propertyAssessment> assessmentsList_inp)  {
        this.fileName = "";
        this.assessmentsList = sortList(checkList(assessmentsList_inp));
    }
    //======================================================================
    //======================================================================
    private List<propertyAssessment> checkList(List<propertyAssessment> List){
        if(List.isEmpty()){
            throw new IllegalArgumentException("List of assessments is empty");
        }
        return List;
    }
    //======================================================================
    //======================================================================
    private static propertyAssessment generateProperty(String[] row) {
        int garage;
        if (row[4].compareTo("Y") == 0) {
            garage = 1;
        } else {
            garage = 0;
        }
        house house = new house(row[1], row[2], garage, Integer.parseInt(row[8]), row[3]);
        neighbourhood hood = new neighbourhood(row[5], row[6], row[7]);
        location location = new location(Double.parseDouble(row[9]), Double.parseDouble(row[10]));
        assessment aC;
        if (row[14].compareTo("") != 0) {
            aC = new assessment(Integer.parseInt(row[12]), row[15], Integer.parseInt(row[13]), row[16], Integer.parseInt(row[14]), row[17]);
        } else if (row[13].compareTo("") != 0) {
            aC = new assessment(Integer.parseInt(row[12]), row[15], Integer.parseInt(row[13]), row[16]);
        } else {
            aC = new assessment(Integer.parseInt(row[12]), row[15]);
        }
        return new propertyAssessment(Integer.parseInt(row[0]), house, hood, location, aC);
    }
    //======================================================================
    //======================================================================
    private static List<propertyAssessment> readDataList(String fileName) throws IOException {
        ArrayList<propertyAssessment> assessmentsList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                assessmentsList.add(generateProperty(values));
            }
        }
        return assessmentsList;
    }
    //======================================================================
    //======================================================================
    private static List<propertyAssessment> sortList(List<propertyAssessment> assessments) {
        return assessments.stream()
                .sorted(Comparator.comparing(l -> l.getHouse().getAssessedValue()))
                .collect(Collectors.toList());
    }

//        if (assessments.size()==1){
//            return assessments;
//        }
//        assessments.sort(new Comparator<propertyAssessment>() {
//            @Override
//            public int compare(propertyAssessment o1, propertyAssessment o2) {
//                return Integer.compare(o1.getHouse().getAssessedValue(), o2.getHouse().getAssessedValue());
//            }
//        });
//        return assessments;
//    }
    //======================================================================
    //======================================================================
    public int getCount() {
        int count = 0;
        for (propertyAssessment aPa : assessmentsList) {
            count++;
        }
        return count;
    }
    //======================================================================
    //======================================================================
    public int getMean() {
        long mean = 0;
        long index= 0;
        for (propertyAssessment aPa : assessmentsList) {
            mean += aPa.getHouse().getAssessedValue();
            index++;
        }
        return Math.toIntExact(mean / index);
    }
    //======================================================================
    //======================================================================
    public int getMax() {
        int index = getCount();
        return assessmentsList.get(index-1).getHouse().getAssessedValue();
    }
    //======================================================================
    //======================================================================
    public int getMin() {
        return assessmentsList.getFirst().getHouse().getAssessedValue();
    }
    //======================================================================
    //======================================================================
    public int getRange() {
        int index = getCount()-1;
        return assessmentsList.get(index).getHouse().getAssessedValue()-assessmentsList.getFirst().getHouse().getAssessedValue();
    }
    //======================================================================
    //======================================================================
    public int getMedian() {
        int index = getCount();
        int mid = index / 2;
        if (index % 2 == 0) {
            return (assessmentsList.get(mid).getHouse().getAssessedValue() + assessmentsList.get(mid - 1).getHouse().getAssessedValue()) / 2;
        }else{
            return assessmentsList.get(mid).getHouse().getAssessedValue();
        }

    }
    //======================================================================
    //======================================================================
    public propertyAssessments checkNeighbourhood(String input){
        List<propertyAssessment> hoodList = new ArrayList<>();
        input = input.toUpperCase();
        for (propertyAssessment aPa : assessmentsList) {
            if (input.compareTo(aPa.getNeighbourhood().getHood()) == 0) {
                hoodList.add(aPa);
            }
        }
        if (hoodList.isEmpty()){
            throw new IllegalArgumentException("No neighbourhood found");
        }
        return  new propertyAssessments(hoodList);
    }
    //======================================================================
    //======================================================================
    public propertyAssessments checkPropClass(String propClass) {
        ArrayList<propertyAssessment> classList = new ArrayList<>();
        for  (propertyAssessment aPa : assessmentsList) {
            int valid =0;
            for (String classes : aPa.getAssessment().getAssessmentClass()){
                if (classes.compareTo(propClass) == 0) {
                    valid = 1;
                    break;
                }
            }
            if (valid ==1){
                classList.add(aPa);
            }
        }
        if (classList.isEmpty()) {
            throw new IllegalArgumentException("Property Class "+propClass+" Doesn't Exist"); }

        return new propertyAssessments(classList);
    }
    //======================================================================
    //======================================================================
    public propertyAssessment checkAccount(int accnumb) {
        int retval = 0;
        for (propertyAssessment aPa : assessmentsList) {
            if (aPa.getAccountNum() == accnumb) {
                return assessmentsList.get(retval);
            }
            retval++;
        }
        throw new IllegalArgumentException("Account Number "+accnumb+" Doesn't Exist");
    }
    //======================================================================
    //======================================================================
    public boolean equals(propertyAssessments o) {
        if (this.getCount() != o.getCount()){
            return false;
        }
        for(int i =0 ;i<o.getCount();i++){
            int testAccountNumber = this.assessmentsList.get(i).getAccountNum();
            boolean b = testAccountNumber == o.checkAccount(testAccountNumber).getAccountNum();
        }
        return true;
    }
    //======================================================================
    // legacy methods no longer in use
    //======================================================================
    public List<String> getStreetNames() {
        List<String> Streets = new ArrayList<>();
        for (propertyAssessment aPa : assessmentsList) {
            String street = aPa.getHouse().getStreetName();
            if (street.compareTo("") == 0) {continue;}
            if (Streets.contains(street)){continue;}
            Streets.add(street);
        }
        return Streets;
    }
    public propertyAssessments checkStreet(String street) {
        List<propertyAssessment> streetList = new ArrayList<>();
        for (propertyAssessment aPa : assessmentsList) {
            if (street.compareTo(aPa.getHouse().getStreetName()) == 0) {
                streetList.add(aPa);}
        }
        return new propertyAssessments(streetList);
    }

    //======================================================================
    // get street names and street name compare functions
    //======================================================================
    public propertyAssessments checkStreetStream(String street) {
        return new propertyAssessments(assessmentsList.stream()
                .filter(p->p.getHouse().getStreetName().equals(street))
                .collect(Collectors.toList()));
    }

    public List<String> getStreetNamesStream(){
        return assessmentsList.stream()
                .map(propertyAssessment -> propertyAssessment.getHouse().getStreetName())
                .filter(street -> street.compareTo("") != 0)
                .distinct()
                .collect(Collectors.toList());
    }
    //======================================================================
    // Find house numb is only to be used in conjunction with check street
    //======================================================================
    public List<propertyAssessment> findHouseNumb(String houseNum) {
        List<propertyAssessment> houseList = new ArrayList<>();
        for (propertyAssessment aPa : assessmentsList) {
            if(houseNum.compareTo(aPa.getHouse().getHouseNum()) == 0){
                houseList.add(aPa);
            }
        }
        if(houseList.isEmpty()){throw new IllegalArgumentException("HouseNumber "+houseNum+" Doesn't Exist");}
        return houseList;
    }
    public location findHouseNumber(String houseNum) {
        return assessmentsList.stream()
                .filter(p->p.getHouse().getHouseNum().equals(houseNum))
                .map(propertyAssessment::getLocation)
                .findFirst().orElse(null);
    }

    public List<String> getNeighbourhoodNames() {
        return assessmentsList.stream()
                .map(p -> p.getNeighbourhood().getHood())
                .distinct()
                .filter(n -> !n.isEmpty())
                .toList();
    }
}
