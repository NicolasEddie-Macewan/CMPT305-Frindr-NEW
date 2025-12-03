package com.mycompany.app;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.mycompany.app.backend.fruit.Complete_tree;
import com.mycompany.app.backend.fruit.Date;
import com.mycompany.app.ui.MenuBuilder;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final AnchorPane rootPane;
    private final HBox topButtonBar;
    private final MenuBuilder menuBuilder = new MenuBuilder();
    private VBox filtersMenu;
    private VBox settingsMenu;
    private final Complete_tree trees = new Complete_tree();
    private Complete_tree filteredTrees = trees;
    private ArcGISMap map;

    // The currently visible menu (filters or settings)
    private VBox activeMenu;
    private Pane overlay;

    public MainController(AnchorPane rootPane, HBox topButtonBar, ArcGISMap map) throws IOException {
        this.rootPane = rootPane;
        this.topButtonBar = topButtonBar;
        this.map = map;
        initialize();
    }

    public void initialize() {
        filtersMenu = createFiltersMenu();
        settingsMenu = createSettingsMenu();
        FeatureLayerHandler featureLayerHandler = new FeatureLayerHandler(filteredTrees);
        map.getOperationalLayers().add(featureLayerHandler.getFeatureLayer());
    }

    private VBox createFiltersMenu() {
        TitledPane fruitFilters = menuBuilder.createFruitFilters();
        fruitFilters.setId("fruitFilters");



        ComboBox<String> neighbourhoodSearch = menuBuilder.createNeighbourhoodSearch(trees);
        neighbourhoodSearch.setId("neighbourhoodSearch");
        TextField dateInput = new TextField();
        dateInput.setId("Date");
        dateInput.setPromptText("YYYY-MM-DD");
        HBox dateHBox = menuBuilder.comparisonFilters();
        dateHBox.setId("dateHBox");
        CheckBox likelyBearsFruit = new CheckBox("Likely Bearing Fruit");
        likelyBearsFruit.setId("likelyBearsFruit");
        Button clearFilters = new Button("Clear Filters");
        clearFilters.setOnAction(e -> clearFilters());
        Button applyFilters = new Button("Apply Filters");
        applyFilters.setOnAction(e -> applyFilters());
        return createMenu("Filters",
                fruitFilters,
                neighbourhoodSearch,
                dateInput,
                dateHBox,
                likelyBearsFruit,
                clearFilters,
                applyFilters
        );
    }

    private VBox createSettingsMenu() {
        return createMenu("Settings",
                new Button("S Test 1"),
                new Button("S Test 2"),
                new Button("S Test 3")
        );
    }

    public void showFiltersMenu() {
        showMenu(filtersMenu);
    }

    public void showSettingsMenu() {
        showMenu(settingsMenu);
    }

    // Creates a generic VBox menu
    private VBox createMenu(String title, Node... items) {
        VBox menu = new VBox(10);
        menu.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 4;");

        Label heading = new Label(title);
        heading.setStyle("-fx-font-weight: bold");

        menu.getChildren().add(heading);
        menu.getChildren().addAll(items);

        menu.setPrefWidth(rootPane.getWidth() * 0.25);
        menu.setMaxWidth(Region.USE_PREF_SIZE);

        // Responsive width
        rootPane.widthProperty().addListener((obs, oldV, newV) ->
                menu.setPrefWidth(newV.doubleValue() * 0.25)
        );

        return menu;
    }

    // Shows menu with animations
    private void showMenu(VBox newMenu) {

        // If a menu is already active, close it first
        if (activeMenu != null) {
            hideActiveMenu(() -> showMenu(newMenu)); // callback to show a new menu
            return;
        }

        topButtonBar.setVisible(false);

        // Create a clickable overlay
        overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.0);");
        overlay.setPickOnBounds(true);
        overlay.setOnMouseClicked(e -> hideActiveMenu(null)); // clicking outside closes

        AnchorPane.setTopAnchor(overlay, 0.0);
        AnchorPane.setRightAnchor(overlay, 0.0);
        AnchorPane.setLeftAnchor(overlay, 0.0);
        AnchorPane.setBottomAnchor(overlay, 0.0);

        rootPane.getChildren().add(overlay);

        // Start menu off-screen
        newMenu.setTranslateX(newMenu.getPrefWidth());
        newMenu.setOpacity(0);

        AnchorPane.setTopAnchor(newMenu, 8.0);
        AnchorPane.setRightAnchor(newMenu, 8.0);

        rootPane.getChildren().add(newMenu);

        activeMenu = newMenu;

        // --- ANIMATIONS ---
        TranslateTransition slide = new TranslateTransition(Duration.millis(250), newMenu);
        slide.setFromX(newMenu.getPrefWidth());
        slide.setToX(0);

        FadeTransition fade = new FadeTransition(Duration.millis(250), newMenu);
        fade.setFromValue(0);
        fade.setToValue(1);

        new ParallelTransition(slide, fade).play();
    }

    // Hide the current menu (animated)
    // Optionally run a callback after closing (used to switch menus)
    private void hideActiveMenu(Runnable afterClose) {
        if (activeMenu == null) {
            if (afterClose != null) afterClose.run();
            return;
        }

        double width = activeMenu.getPrefWidth();

        TranslateTransition slide = new TranslateTransition(Duration.millis(250), activeMenu);
        slide.setFromX(0);
        slide.setToX(width);

        FadeTransition fade = new FadeTransition(Duration.millis(250), activeMenu);
        fade.setFromValue(1);
        fade.setToValue(0);

        ParallelTransition anim = new ParallelTransition(slide, fade);

        anim.setOnFinished(e -> {
            rootPane.getChildren().remove(activeMenu);
            rootPane.getChildren().remove(overlay);

            activeMenu = null;
            overlay = null;
            topButtonBar.setVisible(true);

            if (afterClose != null) afterClose.run();
        });

        anim.play();
    }

    private void uncheckBoxes(VBox menu) {
        ObservableList<Node> children = menu.getChildren();
        for (Node child : children) {
            if (child instanceof CheckBox) {
                ((CheckBox) child).setSelected(false);
            }
        }
    }

    private void uncheckButtons(HBox menu) {
        ObservableList<Node> children = menu.getChildren();
        for (Node child : children) {
            if (child instanceof RadioButton) {
                ((RadioButton) child).setSelected(false);
            }
        }
    }

    private void clearFilters() {
        TitledPane fruitFilters = (TitledPane) filtersMenu.lookup("#fruitFilters");
        uncheckBoxes((VBox) fruitFilters.getContent());
        ComboBox<String> neighbourhoodSearch = (ComboBox<String>) filtersMenu.lookup("#neighbourhoodSearch");
        neighbourhoodSearch.getSelectionModel().clearSelection();
        neighbourhoodSearch.setValue(null);
        CheckBox likelyBearsFruit = (CheckBox) filtersMenu.lookup("#likelyBearsFruit");
        likelyBearsFruit.setSelected(false);
        TextField date = (TextField) filtersMenu.lookup("#Date");
        date.setText("");
        HBox datebox = (HBox) filtersMenu.lookup("#dateHBox");
        uncheckButtons(datebox);
        applyFilters();
    }

    private void applyFilters(){
        filteredTrees = trees;
        //System.out.println(trees.getFruitNamesStreams());
        TitledPane fruitFilters = (TitledPane) filtersMenu.lookup("#fruitFilters");
        ComboBox<String> neighbourhoodSearch = (ComboBox<String>) filtersMenu.lookup("#neighbourhoodSearch");
        CheckBox likelyBearsFruit = (CheckBox) filtersMenu.lookup("#likelyBearsFruit");
        TextField date = (TextField) filtersMenu.lookup("#Date");
        HBox datebox = (HBox) filtersMenu.lookup("#dateHBox");


        if(likelyBearsFruit.isSelected()){
            filteredTrees = filteredTrees.canBearFruit();
        }
        List<String> fruits = getCheckBoxes((VBox) fruitFilters.getContent());
        if (!fruits.isEmpty()) {
            filteredTrees = filteredTrees.getFruitListStream(fruits);
        }
        if(neighbourhoodSearch.getValue() != null){
            filteredTrees = filteredTrees.getNeighbourhood(neighbourhoodSearch.getValue());
        }

        try{
            if(!date.getText().isEmpty()){
                System.out.println(checkBox(datebox));
                switch(checkBox(datebox))
                {
                    case -1:break;
                    case 0: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.lessThan);break;
                    case 1: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.equals);break;
                    case 2: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.greaterThan);break;
                }
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in applying Filters");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Use Date format YYYY-MM-DD instead of " +  date.getText());
            alert.showAndWait();
        }
        System.out.println("fruits count: " + filteredTrees.getCount());

        FeatureLayerHandler featureLayerHandler = new FeatureLayerHandler(filteredTrees);
        map.getOperationalLayers().set(0, featureLayerHandler.getFeatureLayer());
    }

    private List<String> getCheckBoxes(VBox menu) {
        ObservableList<Node> children = menu.getChildren();
        List<String> checkboxes = new ArrayList<>();
        for  (Node child : children) {
            if (child instanceof CheckBox) {
                if (((CheckBox) child).isSelected()) {
                    checkboxes.add(((CheckBox) child).getText());
                }
            }
        }
        return checkboxes;
    }
    private int checkBox(HBox menu) {
        int index = 0;
        for (Node child : menu.getChildren()) {
            if (child instanceof RadioButton) {
                if (((RadioButton) child).isSelected()) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }
}
