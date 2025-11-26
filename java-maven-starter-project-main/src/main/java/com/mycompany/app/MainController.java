package com.mycompany.app;

import com.mycompany.app.ui.MenuBuilder;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainController {

    private final AnchorPane rootPane;
    private final HBox topButtonBar;
    private final MenuBuilder menuBuilder = new MenuBuilder();
    private VBox filtersMenu;
    private VBox settingsMenu;

    // The currently visible menu (filters or settings)
    private VBox activeMenu;
    private Pane overlay;

    public MainController(AnchorPane rootPane, HBox topButtonBar) {
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
        TitledPane neighbourhoodFilters = menuBuilder.createNeighbourhoodFilters();
        CheckBox likelyBearsFruit = new CheckBox("Likely Bearing Fruit");
        return createMenu("Filters",
                fruitFilters,
                neighbourhoodFilters,
                likelyBearsFruit
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


    // ----------------------------------------------------------
    // CREATE A MENU VBOX (Generic)
    // ----------------------------------------------------------
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


    // ----------------------------------------------------------
    // SHOW MENU WITH SLIDE + FADE IN
    // ----------------------------------------------------------
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


    // ----------------------------------------------------------
    // HIDE THE CURRENT MENU (Slide and Fade OUT)
    // Optionally run a callback after closing (used to switch menus)
    // ----------------------------------------------------------
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
}
