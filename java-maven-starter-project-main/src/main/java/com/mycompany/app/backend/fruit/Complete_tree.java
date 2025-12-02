package com.mycompany.app.backend.fruit;

import com.mycompany.app.backend.propAss.propertyAssessments;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Complete_tree {
    private String filename;
    private List<Tree> trees;
    private propertyAssessments assessments = null;

    public Complete_tree() throws IOException {
        this.filename = "Trees_edible.csv";
        trees = readDataList(this.filename);
        assessments = new propertyAssessments("Property_Assessment_Data_2025.csv");
    }

    public Complete_tree(List<Tree> trees){
        this.filename = "";
        this.trees = trees;
    }
    //=================================================================
    //=================================================================
    private static List<Tree> readDataList(String fileName) throws IOException {
        List<Tree> treeList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line+"\n");
                line = line.replaceAll("\"", "");
                String[] values = line.split(",");
                treeList.add(generateTree(values));
            }
        }
        return treeList;
    }
    //=================================================================
    //=================================================================
    private static Tree generateTree(String[] List){
        CityLocation loc = null;
        AboutTree aboutTree = null;
        Tree tree = null;
        if (List.length ==20){
            loc = new CityLocation(List[1],List[2],Double.parseDouble(List[15]),Double.parseDouble(List[16]));
            aboutTree = new AboutTree(List[4],List[5],List[6],List[7],List[13],List[14],List[8],List[9],List[10]);
            tree = new Tree(aboutTree,loc);}
        else if (List.length ==21){
            loc = new CityLocation(List[1],List[2],Double.parseDouble(List[16]),Double.parseDouble(List[17]));
            aboutTree = new AboutTree(List[4],List[5],List[6],List[7],List[8],List[14],List[15],List[9],List[10],List[11]);
            tree = new  Tree(aboutTree,loc);}
        return tree;
    }
    //=================================================================
    //=================================================================
    public Integer getCount(){return trees.size();}
    //=================================================================
    //=================================================================
    public List<Tree> getTrees(){return trees;}
    //=================================================================
    //=================================================================
    private List<String> getStrings(Function<Tree,String> param) {
        return trees.stream()
                .map(param)
                .distinct()
                .filter(t->!t.isEmpty())
                .collect(Collectors.toList());
    }

    public List<String> getFruitNamesStreams(){return getStrings(t -> t.getAboutTree().getFruitType());}

    public List<String> getSpeciesAdvancedStream(){return getStrings(t->t.getAboutTree().getSpeciesBiological());}

    public List<String> getSpeciesCommonStream(){return getStrings(t->t.getAboutTree().getSpeciesCommon());}

    public propertyAssessments getAssessments(){return assessments;}
    //=================================================================
    //=================================================================
    private Complete_tree sortByIntCompare(Function<Tree,Integer> param,Integer comp,mode operand){
        return switch (operand) {
            case lessThan -> new Complete_tree(trees.stream()
                    .filter(t -> param.apply(t) < comp)
                    .collect(Collectors.toList()));
            case equals -> new Complete_tree(trees.stream()
                    .filter(t -> param.apply(t) == comp)
                    .collect(Collectors.toList()));
            case greaterThan -> new Complete_tree(trees.stream()
                    .filter(t -> param.apply(t) > comp)
                    .collect(Collectors.toList()));
        };
    }
    public Complete_tree conditionCheck (Integer compare,mode operand){
        return sortByIntCompare(t->t.getAboutTree().getCondition(),compare,operand);
    }
    public Complete_tree diameterCheck (Integer compare,mode operand){
        return sortByIntCompare(t->t.getAboutTree().getDiameter(),compare,operand);
    }
    //=================================================================
    //=================================================================
    private List<Integer> getInts(Function<Tree,Integer> param){
        return trees.stream()
                .map(param)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Integer> getDiameters(){
        return getInts(t->t.getAboutTree().getDiameter());
    }
    public List<Integer> getConditions(){
        return getInts(t->t.getAboutTree().getCondition());
    }
    //=================================================================
    //=================================================================
    public Complete_tree getFruitStream(String fruitType){
        if(fruitType.isEmpty()){throw new IllegalArgumentException("please enter a fruit");}
        return new Complete_tree(trees.stream()
                .filter(t->t.getAboutTree().getFruitType().equals(fruitType))
                .collect(Collectors.toList()));
    }
    //=================================================================
    //=================================================================
    public Complete_tree getFruitListStream(List<String> fruitType){
        return new Complete_tree(trees.stream()
                .filter(t->fruitType.contains(t.getAboutTree().getFruitType()))
                .collect(Collectors.toList()));
    }
    //=================================================================
    // these functions use lists instead of strings DONT USE THEM
    //=================================================================
//    public Complete_tree getFruit(String fruitType){
//        if(fruitType.isEmpty()){throw new IllegalArgumentException("Please enter a fruit type");}
//        List<Tree> selectedTrees = new ArrayList<>();
//        for  (Tree tree : trees){
//            if(tree.getAboutTree().getFruitType().equals(fruitType)){
//                selectedTrees.add(tree);
//            }
//        }
//        if(selectedTrees.isEmpty()){throw new IllegalArgumentException("invalid fruitType");}
//        return new Complete_tree(selectedTrees);
//    }
//    public List<String> getSpeciesAdvanced() {
//        List<String> species = new ArrayList<>();
//        for  (Tree tree : trees){
//            String selectedTree = tree.getAboutTree().getSpeciesBiological();
//            if(species.contains(selectedTree)){continue;}
//            species.add(selectedTree);
//        }
//        if (species.isEmpty()){throw new IllegalArgumentException("invalid species");}
//        return species;
//    }
//    public List<String> getCommonSpecies() {
//        List<String> commonSpecies = new ArrayList<>();
//        for  (Tree tree : trees){
//            String selectedTree = tree.getAboutTree().getSpeciesCommon();
//            if(commonSpecies.contains(selectedTree)){continue;}
//            commonSpecies.add(selectedTree);
//        }
//        return commonSpecies;
//    }
//    public List<String> getFruitTypes(){
//        List<String> fruits = new ArrayList<>();
//        for (Tree tree : trees){
//            String fruit = tree.getAboutTree().getFruitType();
//            if(fruits.contains(fruit)){continue;}
//            fruits.add(fruit);
//        }
//        return fruits;
//    }
    //=================================================================
    //=================================================================
    public Complete_tree dateFilter(Date date,mode key) {
        return switch (key) {
            case lessThan -> dateCompare(date, -1);
            case equals -> dateCompare(date, -0);
            case greaterThan -> dateCompare(date, 1);
        };
    }
    private Complete_tree dateCompare(Date date,int value){
        return new Complete_tree(trees.stream()
                .filter(t->t.getAboutTree().getPlanted().compare(date)==value)
                .collect(Collectors.toList()));
    }

    public enum mode{
        lessThan,
        equals,
        greaterThan
    }
    //=================================================================
    //=================================================================
    public Complete_tree InRadiusNeighbourhood(Double radius, String uInput) {
        if (radius.equals(0.0)) {
            throw new IllegalArgumentException("Please enter a valid radius value");
        }
        if (uInput.isEmpty()) {
            throw new IllegalArgumentException("Please enter an address");
        }
        location checkpoint = parseLocation(uInput);
        if (checkpoint == null) {throw new IllegalArgumentException("invalid address");}
        return InRadius(radius, checkpoint);
    }

    public Complete_tree InRadius(Double radius,location checkpoint){
        return new Complete_tree(
                trees.stream()
                        .filter(t->radius>= checkpoint.getDistanceHaversine(t.getCitylocation().getCoordinates()))
                        .sorted(Comparator.comparing(t->checkpoint.getDistanceHaversine(t.getCitylocation().getCoordinates())))
                        .collect(Collectors.toList())
        );
    }
    //=================================================================
    //=================================================================
    private location parseLocation(String uInput){
        /* if(assessments ==null) {
            try {
                assessments = new propertyAssessments("Property_Assessment_Data_2025.csv");
            } catch (IOException e) {
                System.out.println(e.getMessage() + "does not exist");
            }
        } */
        uInput = uInput.toUpperCase();
        String[] userInputs = uInput.split(",");
        if (userInputs.length != 2){throw new IllegalArgumentException("Format:House Number,Street Name");}
        if (!assessments.getStreetNamesStream().contains(userInputs[1])){
            throw new IllegalArgumentException("Street doesn't exist");
        }
        propertyAssessments street = assessments.checkStreetStream(userInputs[1]);
        return street.findHouseNumber(userInputs[0]);
    }
                                        // change this value depending on years to bear fruit
    public Complete_tree canBearFruit(){           //    |
        LocalDate currentDate = LocalDate.now();   //    v
        Date checkDate =  new Date(currentDate.getYear()-5 +"/"+currentDate.getMonthValue()+"/"+ currentDate.getDayOfMonth());
        return  dateFilter(checkDate,mode.lessThan);
    }

    public List<String> getAllNeighbourhoodNames() {
        return trees.stream()
                .map(t -> t.getCitylocation().getNeighbourhood())
                .distinct()
                .filter(n -> !n.isEmpty())
                .toList();
    }
}

