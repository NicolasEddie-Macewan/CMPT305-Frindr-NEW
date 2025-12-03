package com.mycompany.app;

import com.mycompany.app.backend.fruit.Complete_tree;
import com.mycompany.app.backend.fruit.Date;
import com.mycompany.app.backend.fruit.location;
import com.mycompany.app.backend.propAss.propertyAssessments;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private boolean locationApplied = false ;
    private boolean filtersApplied = false;
    private boolean propLoaded = false;
    private boolean otherLoaded = false;
    // The currently visible menu (filters or settings)
    private VBox activeMenu;
    private Pane overlay;

    public MainController(AnchorPane rootPane, HBox topButtonBar) throws IOException {
        this.rootPane = rootPane;
        this.topButtonBar = topButtonBar;
        initialize();
    }

    public void initialize() {
        filtersMenu = createFiltersMenu();
        settingsMenu = createSettingsMenu();
    }

    private VBox createFiltersMenu() {
        TitledPane fruitFilters = menuBuilder.createFruitFilters();
        fruitFilters.setId("fruitFilters");
        ComboBox<String> neighbourhoodSearch = menuBuilder.createNeighbourhoodSearch(trees.getAllNeighbourhoodNames());
        neighbourhoodSearch.setId("neighbourhoodSearch");

        Text dateFilterText = new Text();
        dateFilterText.setText("Date Filter");
        dateFilterText.setId("dateFilterText");

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
                dateFilterText,
                dateInput,
                dateHBox,
                likelyBearsFruit,
                clearFilters,
                applyFilters
        );
    }

    private VBox createSettingsMenu() {
        Text LL = new Text();
        LL.setId("LL");
        LL.setText("Set Coordinates");

        TextField LLInput = new TextField();
        LLInput.setId("LLInput");
        LLInput.setPromptText("Latitude, Longitude");

        TextField RadiusInput = new TextField();
        RadiusInput.setId("RadiusInput");
        RadiusInput.setPromptText("Radius in KM");

        TextField AddressInput = new TextField();
        AddressInput.setId("AddressInput");
        AddressInput.setPromptText("House Number, Street Name");
        AddressInput.setVisible(false);

        CheckBox useLocation = new CheckBox("Use Address as Anchor");
        useLocation.setId("useLocation");
        useLocation.setOnAction(e -> {setProp();});

        Text warningText = new Text();
        warningText.setText("This feature takes time to load");
        warningText.setId("warningText");


        Text radiusText = new Text();
        radiusText.setText("Set Radius");
        radiusText.setId("radiusText");

        return createMenu("Settings",
                LL,
                LLInput,
                //radiusText,
                RadiusInput,
                useLocation,
                warningText,
//                LocationFilterText,
//                neighbourhoodSearch,
                menuButtons()

        );
    }
    public HBox menuButtons(){
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearLocation());
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> applyLocation());
        Button helpButton = new Button("Help");
        helpButton.setOnAction(e -> helpLocation());
        HBox buttons = new HBox(clearButton, applyButton, helpButton);
        buttons.setSpacing(20);
        return buttons;
    }

    private void setProp() {
        if (propLoaded){return;}
        try {
            trees.setAssessments(new propertyAssessments("Property_Assessment_Data_2025.csv"));
            settingsMenu.getChildren().removeLast();

            Text LocationFilterText = new Text();
            LocationFilterText.setText("Set Address");
            LocationFilterText.setId("locationFilterText");
            settingsMenu.getChildren().add(LocationFilterText);

            ComboBox<String> neighbourhoodSearch = menuBuilder.createNeighbourhoodSearch(trees.getAllNeighbourhoodNames());
            neighbourhoodSearch.setId("neighbourhoodSearch");
            neighbourhoodSearch.onActionProperty().set(e -> {loadStreets(neighbourhoodSearch.getValue());});
            settingsMenu.getChildren().add(neighbourhoodSearch);
            settingsMenu.getChildren().add(menuButtons());
            propLoaded = true;
//            Text locationFilter = (Text) settingsMenu.lookup("#locationFilterText");
//            locationFilter.setVisible(true);
//            ComboBox<String> neighbourhood = (ComboBox<String>) settingsMenu.lookup("#neighbourhoodSearch");
//            neighbourhood.setVisible(true);
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in loading properties");
            alert.setHeaderText("Invalid file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadStreets(String value) {
        if (value==null) {return;}
        settingsMenu.getChildren().removeLast();

        ComboBox<String> test = (ComboBox<String>) settingsMenu.lookup("#streeNames");
        if(!(test ==null)){settingsMenu.getChildren().removeLast();}
        ComboBox<String> streetNames = menuBuilder.createNeighbourhoodSearch(trees.getAssessments().getStreetNamesStream(value));
        streetNames.setPromptText("Street Name");
        streetNames.onActionProperty().set(e -> {loadOther(streetNames.getValue());});
        streetNames.setId("streetNames");
        streetNames.setVisible(true);

        settingsMenu.getChildren().add(streetNames);
        settingsMenu.getChildren().add(menuButtons());
    }

    private void loadOther(String value) {
        if (value==null) {return;}
        settingsMenu.getChildren().removeLast();

        ComboBox<String> test = (ComboBox<String>) settingsMenu.lookup("#other");
        if(!(test ==null)){settingsMenu.getChildren().removeLast();}
        ComboBox<String> other = menuBuilder.createNeighbourhoodSearch(trees.getAssessments().getHouseNumbersStreet(value));
        other.setPromptText("Search Home Numbers");
        other.setId("other");
        other.setVisible(true);

        settingsMenu.getChildren().add(other);
        settingsMenu.getChildren().add(menuButtons());
    }

    private void clearLocation() {
        TextField LL = (TextField) settingsMenu.lookup("#LLInput");
        LL.setText("");
        TextField Radius =  (TextField) settingsMenu.lookup("#RadiusInput");
        Radius.setText("");
        ComboBox<String> address = (ComboBox<String>) settingsMenu.lookup("#neighbourhoodSearch");
        address.getSelectionModel().clearSelection();
        address.setValue(null);

        settingsMenu.getChildren().removeLast(); // removes buttons
        settingsMenu.getChildren().removeLast(); // removes house number
        settingsMenu.getChildren().removeLast(); // removes street Name

        settingsMenu.getChildren().add(menuButtons());
        locationApplied = false;
        applyFilters();
    }

    private void applyLocation(){
        if (!filtersApplied){
            filteredTrees = trees;
        }

        TextField LL = (TextField) settingsMenu.lookup("#LLInput");
        TextField Radius =  (TextField) settingsMenu.lookup("#RadiusInput");

        ComboBox<String> houseNumber = (ComboBox<String>) settingsMenu.lookup("#other");
        ComboBox<String> street = (ComboBox<String>) settingsMenu.lookup("#streetNames");




        try {
            if (!LL.getText().isEmpty() && !Radius.getText().isEmpty()) {
                String[] locArr = LL.getText().split(",");
                filteredTrees = filteredTrees.InRadius(Double.parseDouble(Radius.getText()), new location(Double.parseDouble(locArr[0]), Double.parseDouble(locArr[1])));
                locationApplied = true;
            } else if (!(houseNumber.getValue() == null)   && !(street.getValue() == null) && !Radius.getText().isEmpty()) {
                filteredTrees = filteredTrees.InRadiusNeighbourhood(Double.parseDouble(Radius.getText()),houseNumber.getValue()+","+street.getValue());
                locationApplied = true;
            }
            else{
                if(houseNumber.getValue() == null){throw new IllegalArgumentException("invalid house number");}
                else if(street.getValue() == null){throw new IllegalArgumentException("invalid street Name");}
                else if (Radius.getText().isEmpty()) {throw new IllegalArgumentException("invalid radius");}
                else if (LL.getText().isEmpty()) {throw new IllegalArgumentException("invalid Coordinate location");}
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in applying Filters");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    System.out.println(filteredTrees.getCount());
    }

    private void helpLocation(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Location Filter Help");
        alert.setHeaderText("Location Filter Help");
        alert.setContentText("This feature allows you to set a specified location, either polar coordinates or address, and search for all the trees in a input radius");
        alert.showAndWait();
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
        if(!locationApplied) {
            filteredTrees = trees;
        }
        TitledPane fruitFilters = (TitledPane) filtersMenu.lookup("#fruitFilters");
        ComboBox<String> neighbourhoodSearch = (ComboBox<String>) filtersMenu.lookup("#neighbourhoodSearch");
        CheckBox likelyBearsFruit = (CheckBox) filtersMenu.lookup("#likelyBearsFruit");
        TextField date = (TextField) filtersMenu.lookup("#Date");
        HBox datebox = (HBox) filtersMenu.lookup("#dateHBox");

        if(likelyBearsFruit.isSelected()){
            filteredTrees = filteredTrees.canBearFruit();
            filtersApplied = true;
        }
        List<String> fruits = getCheckBoxes((VBox) fruitFilters.getContent());
        if (!fruits.isEmpty()) {
            filteredTrees = filteredTrees.getFruitListStream(fruits);
            filtersApplied = true;
        }
        if(neighbourhoodSearch.getValue() != null){
            filteredTrees = filteredTrees.getNeighbourhood(neighbourhoodSearch.getValue());
            filtersApplied = true;
        }

        try{
            if(!date.getText().isEmpty()){
                System.out.println(checkBox(datebox));
                switch(checkBox(datebox))
                {
                    case -1:break;
                    case 0: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.lessThan);filtersApplied = true;break;
                    case 1: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.equals);filtersApplied = true;break;
                    case 2: filteredTrees = filteredTrees.dateFilter(new Date(date.getText()), Complete_tree.mode.greaterThan);filtersApplied = true;break;
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
