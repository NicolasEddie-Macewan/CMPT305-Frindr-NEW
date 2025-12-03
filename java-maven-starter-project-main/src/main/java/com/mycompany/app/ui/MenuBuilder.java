package com.mycompany.app.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class MenuBuilder {
    public TitledPane createFruitFilters() {
        List<String> fruit = List.of(
                "Acorn",
                "Crabapple",
                "RussianOlive",
                "Cherry",
                "Chokecherry",
                "Hawthorn",
                "Apple",
                "Pear",
                "Plum",
                "Hackberry",
                "Coffeetreepod",
                "Caraganaflower/pod",
                "Butternut",
                "Juniper",
                "Saskatoon",
                "Walnut");

        List<CheckBox> checkBoxes = fruit.stream()
                .map(CheckBox::new)
                .toList();
        VBox content = new VBox();
        content.getChildren().addAll(checkBoxes);

        TitledPane fruitFilters = new TitledPane("Fruit Filters", content);
        fruitFilters.setExpanded(false);
        return fruitFilters;
    }


    //public ComboBox<String> createNeighbourhoodSearch(Complete_tree data) {
    public ComboBox<String> createNeighbourhoodSearch(List<String> dataset) {

        //List<String> neighbourhoodList = data.getAllNeighbourhoodNames();
        //ObservableList<String> neighbourhoods = javafx.collections.FXCollections.observableArrayList(neighbourhoodList);

        ObservableList<String> neighbourhoods = javafx.collections.FXCollections.observableArrayList(dataset);
        FilteredList<String> filteredNeighbourhoods = new FilteredList<>(neighbourhoods, p -> true);

        ComboBox<String> comboBox = new ComboBox<>(filteredNeighbourhoods);
        comboBox.setEditable(true);
        comboBox.setPromptText("Search Neighbourhood...");

        // Flag to avoid recursive updates
        final boolean[] ignore = {false};

        // ---- 1. Filter when the user types ----
        comboBox.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (ignore[0]) {
                return;
            }

            // Defer filtering until after JavaFX finishes updating caret/selection
            Platform.runLater(() -> {
                String filter = (newText == null) ? "" : newText.trim().toLowerCase();

                if (filter.isEmpty()) {
                    filteredNeighbourhoods.setPredicate(s -> true);
                } else {
                    filteredNeighbourhoods.setPredicate(city -> city.toLowerCase().startsWith(filter));
                }

                // Only reopen popup when user is typing text (not when selecting)
                if (!comboBox.isShowing() && !filter.isEmpty()) {
                    try {
                        comboBox.show();
                    } catch (Exception ignored) {
                        // Ignore internal selection exceptions safely
                    }
                }
            });
        });

        // ---- 2. When a city is selected, safely update the editor ----
        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                return;
            }

            ignore[0] = true;
            Platform.runLater(() -> {
                comboBox.getEditor().setText(newVal);
                ignore[0] = false;
            });
        });
        return comboBox;
    }

    public HBox comparisonFilters(){
        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("<\t");
        button1.setToggleGroup(group);
        RadioButton button2 = new RadioButton("=\t");
        button2.setToggleGroup(group);
        RadioButton button3 = new RadioButton(">\t");
        button3.setToggleGroup(group);
        return new HBox(button1, button2, button3);
    }


}

