/**
 * Copyright 2019 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.mycompany.app;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private MapView mapView;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // set the title and size of the stage and show it
        stage.setTitle("Frindr");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // Note: it is not best practice to store API keys in source code.
        // An API key is required to enable access to services, web maps, and web scenes hosted in ArcGIS Online.
        // If you haven't already, go to your developer dashboard to get your API key.
        // Please refer to https://developers.arcgis.com/java/get-started/ for more information
        String yourApiKey = "AAPTxy8BH1VEsoebNVZXo8HurEeRA5dt5R5UDjTBazcbkD-eMdci6DNQ7rA-6THCxQl1FD2lAtEFDzrufUc0JiksSfiuzqwyawEaPhtwnbVCbDEGZeYSWG9z69UDgPjvB7RDHnRn7P6po_8NenQAvDIydMJrvOivnqtKHrLAFz4MaGBvKGIyX9KTw0RCu2HRVsoQTEq-HIc-W_0tt_Ghg2QBHKCYD5nzPO9XWJCB-ukm_bo.AT1_1l77XPTP";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a MapView to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

        // create an ArcGISMap with an imagery basemap
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_IMAGERY);

        // display the map by setting the map on the map view
        mapView.setMap(map);
        Viewpoint edmontonViewpoint = new Viewpoint(53.5461, -113.4938, 400000);
        mapView.setViewpoint(edmontonViewpoint);

        // ------------------------------------------------------------------
        // UI OVERLAY WITH TOP-RIGHT BUTTONS
        // ------------------------------------------------------------------

        // Transparent overlay on top of the map; only children capture mouse events
        AnchorPane uiOverlay = new AnchorPane();
        uiOverlay.setPickOnBounds(false); // do not block map interaction where there are no controls

        // Top-right button bar
        HBox topButtonBar = new HBox(10);
        topButtonBar.setAlignment(Pos.CENTER_RIGHT);
        topButtonBar.setPadding(new Insets(8));
        topButtonBar.setStyle(
                "-fx-background-color: rgba(255,255,255,0.85); " +
                        "-fx-background-radius: 4; " +
                        "-fx-padding: 4 8 4 8;"
        );

        // Buttons
        Button filtersButton = new Button("Filters");
        Button settingsButton = new Button("Settings");
        topButtonBar.getChildren().addAll(filtersButton, settingsButton);

        // Anchor the bar to the top-right corner
        AnchorPane.setTopAnchor(topButtonBar, 10.0);
        AnchorPane.setRightAnchor(topButtonBar, 10.0);

        uiOverlay.getChildren().add(topButtonBar);

        // Add overlay above the map
        stackPane.getChildren().add(uiOverlay);

        // Hook the overlay and button bar to MainController
        MainController controller = new MainController(uiOverlay, topButtonBar);

        // Wire button actions
        filtersButton.setOnAction(e -> controller.showFiltersMenu());
        settingsButton.setOnAction(e -> controller.showSettingsMenu());
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {

        if (mapView != null) {
            mapView.dispose();
        }
    }
}
