package com.mycompany.app.ui;

import com.mycompany.app.backend.fruit.Complete_tree;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MenuBuilder {
    public TitledPane createFruitFilters() {
        List<String> fruit = List.of(
                "Crabapple",
                "Acorn",
                "Chokecherry",
                "Cherry",
                "Hawthorn",
                "Russian Olive",
                "Plum",
                "Pear",
                "Apple",
                "Caragana Flower/Pod",
                "Juniper",
                "Hackberry",
                "Coffeetree Pod",
                "Butternut",
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

    public ComboBox<String> createNeighbourhoodSearch(Complete_tree data) {
        List<String> neighbourhoodList = data.getAllNeighbourhoodNames();
        ObservableList<String> neighbourhoods = javafx.collections.FXCollections.observableArrayList(neighbourhoodList);
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
}
