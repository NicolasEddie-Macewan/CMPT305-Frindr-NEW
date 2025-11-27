package com.mycompany.app.backend.fruit;

public class Tree {
    private AboutTree aboutTree;
    private CityLocation citylocation;
    //=================================================================
    //=================================================================
    public Tree(AboutTree aboutTree,CityLocation citylocation){
        this.aboutTree=aboutTree;
        this.citylocation=citylocation;
    }
    //=================================================================
    //=================================================================
    public AboutTree getAboutTree() {
        return aboutTree;
    }
    //=================================================================
    //=================================================================
    public CityLocation getCitylocation() {
        return citylocation;
    }

    //this is purely for testing purposes by me
    @Override
    public String toString() {
        return aboutTree.getSpeciesCommon()+"\n"+citylocation.getNeighbourhood()+"\n"+aboutTree.getFruitType();
    }
}

