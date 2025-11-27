package com.mycompany.app.backend.tests;

import com.mycompany.app.backend.fruit.Complete_tree;
import com.mycompany.app.backend.fruit.Date;
import com.mycompany.app.backend.fruit.Tree;
import com.mycompany.app.backend.fruit.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestingFile {
    public static void main(String[] args) {
        Complete_tree tree;
        try {
            tree = new Complete_tree();
        } catch (IOException e) {
            System.out.println("invalid file bozo");
            System.out.println(e.getMessage());
            return;
        }
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("1:quit\n2:check if fruiting\n3:Stream Testing\n4:List Filter Testing\n5:Date Filter Testing\n6:location Testing\n7:diameter Filter Testing\n8:Condition Filter Testing\n9:location test City Centre\nselection: ");
                int inp = Integer.parseInt(input.nextLine());
                switch (inp) {
                    case 1: return;
                    case 2: checkFruitBearable(tree);break;
                    case 3: streamTest(tree);break;
                    case 4: listTest(tree);break;
                    case 5: dateTest(tree);break;
                    case 6: StringlocationTest(tree);break;
                    case 7: diameterTest(tree);break;
                    case 8: conditionCheck(tree);break;
                    case 9: LocationLocationTest(tree);break;
                }

            } catch (NumberFormatException e) {
                System.out.println("please enter one of the available numbers");
            }
        }
    }

//=================================================================
// this shows how to use the stream methods
// =================================================================
    public static void streamTest(Complete_tree tree) {
        List<String> Fruits = tree.getFruitNamesStreams();
        for (String fruit : Fruits) {
            System.out.println("\n" + fruit);
            Complete_tree selectFruit = tree.getFruitStream(fruit);

            List<String> bioNames = selectFruit.getSpeciesAdvancedStream();
            for (String bion : bioNames) {
                System.out.println(bion);
            }

            List<String> normName = selectFruit.getSpeciesCommonStream();
            for (String norm : normName) {
                System.out.println("\t" + norm);
            }
        }
    }

//=================================================================
// this shows how to use the get FruitListStream method used when the number of active fruit filters > 1
// =================================================================
    public static void listTest(Complete_tree tree) {
        List<String> testSelection = new ArrayList<>();

        testSelection.add("Saskatoon"); // hitting the filter button adds / removes an item from the list
        testSelection.add("Plum");

        Complete_tree listTest = tree.getFruitListStream(testSelection);

        System.out.println(listTest.getFruitNamesStreams());
        System.out.println(listTest.getCount());

        for (Tree tree1 : listTest.getTrees()) {
            System.out.println("\n" + tree1);
        }
    }

    //=================================================================
// this shows how to use the date filter method
// =================================================================
    public static void dateTest(Complete_tree tree) {
        Date queryDate;

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("please enter a date in the formant YYYY/MM/DD or leave blank for default\nDate: ");
            String inp = input.nextLine();
            if (inp.isEmpty()) {
                queryDate = new Date("1990/01/01");
                break;
            } else {
                try {
                    queryDate = new Date(inp);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        Complete_tree dateTest;

        // ideally in the filters menu we have a buttong to select the enum value
        dateTest = tree.dateFilter(queryDate, Complete_tree.mode.lessThan);
        System.out.println(dateTest.getFruitNamesStreams());
        System.out.println(dateTest.getCount());

        dateTest = tree.dateFilter(queryDate, Complete_tree.mode.equals);
        System.out.println(dateTest.getFruitNamesStreams());
        System.out.println(dateTest.getCount());

        dateTest = tree.dateFilter(queryDate, Complete_tree.mode.greaterThan);
        System.out.println(dateTest.getFruitNamesStreams());
        System.out.println(dateTest.getCount());
    }
//=================================================================
// this shows how to use the Location filter method
// =================================================================
    public static void StringlocationTest(Complete_tree tree) {
        Scanner input = new Scanner(System.in);
        double radius;
        while (true) {
            System.out.print("please enter Number for a radius in KM or leave blank for default\nRadius: ");
            String inp = input.nextLine();
            if (inp.isEmpty()) {
                radius = 10.0;break;
            } else {
                try {radius = Double.parseDouble(inp);
                    if (radius<0){throw new IllegalArgumentException("radius must be a positive number");}
                    break;
                } catch (Exception e) {System.out.println(e.getMessage());}
            }
        }
        String address;
        System.out.print("please enter an Address or leave blank for default\nAddress: ");
        String inp = input.nextLine();
        if (inp.isEmpty()) {
            address = "1713,ROBERTSON PLACE SW"; // this is the first element in the csv file
        } else {
            address = inp;
        }
        Complete_tree inRad;

        try {
            inRad = tree.InRadiusNeighbourhood(radius, address);
            System.out.println("There are " + inRad.getCount() + " trees with edible fruit within " + radius + "km of " + address);
        } catch (Exception e) {System.out.println("\n" + e.getMessage() + "\n");}
    }
//=================================================================
// this shows how to use the Diameter filter method
// =================================================================
    public static void diameterTest(Complete_tree tree) {
        int diameter;
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("enter a diameter in m: ");
                diameter = Integer.parseInt(input.nextLine());
                if (diameter <= 0) {
                    throw new IllegalArgumentException("diameter must be a positive number");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Diameter must be a positive Integer");
            }
        }
        // ideally in the filters menu we have a buttong to select the enum value
        System.out.println(tree.getDiameters());
        Complete_tree diamTest;
        System.out.println("Less than " + diameter);
        diamTest = tree.diameterCheck(diameter, Complete_tree.mode.lessThan);
        System.out.println(diamTest.getCount()+"\n");

        System.out.println("Equals " + diameter);
        diamTest = tree.diameterCheck(diameter, Complete_tree.mode.equals);
        System.out.println(diamTest.getCount()+"\n");

        System.out.println("Greater than " + diameter);
        diamTest = tree.diameterCheck(diameter, Complete_tree.mode.greaterThan);
        System.out.println(diamTest.getCount()+"\n");
    }
//=================================================================
// this shows how to use the condition filter method
// =================================================================
    public static void conditionCheck(Complete_tree tree) {
        int condition;
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("enter a Condition out of 100: ");
                condition = Integer.parseInt(input.nextLine());
                if (condition <= 0 || condition >= 100) {
                    throw new IllegalArgumentException("diameter must be a positive number less than 101");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Diameter must be a positive Integer");
            }
        }
        System.out.println(tree.getConditions()+"\n");

        // ideally in the filters menu we have a buttong to select the enum value
        Complete_tree conditionTest;

        System.out.println("Less than");
        conditionTest = tree.conditionCheck(condition, Complete_tree.mode.lessThan);
        System.out.println(conditionTest.getCount()+"\n");

        System.out.println("Greater than");
        conditionTest = tree.conditionCheck(condition, Complete_tree.mode.greaterThan);
        System.out.println(conditionTest.getCount()+"\n");

        System.out.println("Equals");
        conditionTest = tree.conditionCheck(condition, Complete_tree.mode.equals);
        System.out.println(conditionTest.getCount()+"\n");
    }
//=================================================================
// this shows how to use system time for bearing fruit
// =================================================================
    public static void checkFruitBearable(Complete_tree tree) {
        Complete_tree fruitingTrees = tree.canBearFruit();
        System.out.println("\n"+fruitingTrees.getCount()+"\n");
        for (Tree tre :  fruitingTrees.getTrees()) {
            System.out.println(tre.getAboutTree().getPlanted());
        }
    }

    public static void LocationLocationTest(Complete_tree tree) {
        Scanner input = new Scanner(System.in);
        double radius;
        while (true) {
            System.out.print("please enter Number for a radius in KM or leave blank for default\nRadius: ");
            String inp = input.nextLine();
            if (inp.isEmpty()) {
                radius = 10.0;break;
            } else {
                try {radius = Double.parseDouble(inp);
                    if (radius<0){throw new IllegalArgumentException("radius must be a positive number");}
                    break;
                } catch (Exception e) {System.out.println(e.getMessage());}
            }
        }
        location address = new location(53.5439, -113.4923); // city centre co-ordinates

        Complete_tree inRad;
        try {
            inRad = tree.InRadius(radius, address);
            System.out.println(inRad.getCount()+"\n");
            System.out.println(inRad.getTrees().getFirst().toString()+"\n");
        }
        catch (Exception e){System.out.println(e.getMessage());}
    }
// =================================================================
} // end of program
// =================================================================






