package com.mycompany.app.backend.fruit;

import java.util.ArrayList;
import java.util.List;

public class AboutTree {
        private List<String> speciesCommon = new ArrayList<>(); //4 ||4,5
        private List<String> speciesAdvanced = new ArrayList<>(); // 5,6,7 || 6,7,8
        private String fruitType;
        private Integer count;
        private Integer diameter;   // 8 || 9
        private Integer condition; // 9 || 10
        private Date planted; // 10 || 11
        //=================================================================
        //=================================================================
        public AboutTree(String speciesCommon, String gens, String species, String cultivar, String fruitType,String count,String diameter,String condition,String planted) {
            this.speciesCommon.add(removeSpace(speciesCommon)); //4

            this.speciesAdvanced.add(removeSpace(gens)); //5
            this.speciesAdvanced.add(removeSpace(species)); //6
            this.speciesAdvanced.add(removeSpace(cultivar)); // 7

            this.fruitType = removeSpace(fruitType); // 13

            this.count = checkInput(count,1); //14
            this.diameter = checkInput(diameter,2); // 8
            this.condition = checkInput(condition,3); // 9

            this.planted = new Date(planted); // 10
        }
        //=================================================================
        //=================================================================
        public AboutTree(String speciesCommon,String plantType, String gens, String species, String cultivar, String fruitType,String count,String diameter,String condition,String planted) {
            this.speciesCommon.add(removeSpace(speciesCommon)); //4
            this.speciesCommon.add(removeSpace(plantType)); //5

            this.speciesAdvanced.add(removeSpace(gens)); //6
            this.speciesAdvanced.add(removeSpace(species)); //7
            this.speciesAdvanced.add(removeSpace(cultivar)); //8

            this.fruitType = removeSpace(fruitType); //14

            this.count = checkInput(count,1); //15
            this.diameter = checkInput(diameter,2); //8
            this.condition = checkInput(condition,3); // 9
            this.planted = new Date(planted);
        }
        //=================================================================
        //=================================================================
        public String removeSpace(String value){
            return value.replaceAll(" ","");
        }
        //=================================================================
        //=================================================================
        private Integer checkInput(String value,Integer identifier){
            try{
                return Integer.parseInt(value);
            }catch(Exception e){
                if(identifier == 1){throw new IllegalArgumentException("Invalid Count");}
                else if(identifier == 2){throw new IllegalArgumentException("Invalid Diameter");}
                else if(identifier == 3){throw new IllegalArgumentException("Invalid Condition");}
            }
            throw new IllegalArgumentException();
        }
        //=================================================================
        //=================================================================
        public String getSpeciesCommon() {
            if(speciesCommon.size() == 2){return speciesCommon.get(1) +" "+speciesCommon.get(0);}
            return speciesCommon.getFirst();
        }
        //=================================================================
        //=================================================================
        public String getSpeciesBiological() {
            StringBuilder builder = new StringBuilder(256);
            for (String item : speciesAdvanced){
                if(item.compareTo("x")==0){continue;}
                if(item.compareTo("")==0){continue;}
                builder.append(item);
                builder.append(" ");
            }
            return builder.toString();
        }
        //=================================================================
        //=================================================================
        public String getFruitType() {
            return  fruitType;
        }
        //=================================================================
        //=================================================================
        public Date getPlanted() {
            return planted;
        }
        //=================================================================
        //=================================================================
        public Integer getDiameter() {return diameter;}
        //=================================================================
        //=================================================================
        public Integer getCondition() {return condition;}

        public boolean equals(AboutTree o){
            return this.getSpeciesCommon().equals(o.getSpeciesCommon())
                    && this.getSpeciesBiological().equals(o.getSpeciesBiological())
                    && this.getFruitType().equals(o.getFruitType())
                    && this.getPlanted().equals(o.getPlanted())
                    && this.count.equals(o.count)
                    && this.diameter.equals(o.diameter)
                    && this.condition.equals(o.condition);
        }

    }


