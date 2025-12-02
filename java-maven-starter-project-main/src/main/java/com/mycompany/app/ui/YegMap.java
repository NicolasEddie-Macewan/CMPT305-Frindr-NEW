package com.mycompany.app.ui;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.scene.layout.StackPane;

/**
 * YegMap is a reusable map component for the app.
 *
 * - Centers the map on Edmonton when created.
 * - Provides a graphics overlay for tree pins.
 * - Exposes a JavaFX Node (StackPane) that can be added to any layout.
 */
public class YegMap {

    private final MapView mapView;
    private final GraphicsOverlay treeOverlay;
    private final StackPane root;

    public YegMap() {
        // Create the MapView
        mapView = new MapView();

        // Create the map with a basemap style
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_LIGHT_GRAY);
        mapView.setMap(map);

        // Center the map on Edmonton
        // latitude = 53.5461, longitude = -113.4938
        Viewpoint edmontonViewpoint = new Viewpoint(53.5461, -113.4938, 100000);
        mapView.setViewpoint(edmontonViewpoint);

        // Create a graphics overlay for tree pins
        treeOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(treeOverlay);

        // Root node that you can add to any JavaFX layout
        root = new StackPane(mapView);
    }

    /**
     * Returns the root node containing the map view.
     * Add this to your scene graph (e.g., BorderPane.setCenter(yegMap.getRoot())).
     */
    public StackPane getRoot() {
        return root;
    }

    /**
     * Add a single tree pin at the given latitude/longitude.
     * Call this in a loop once you have your list of tree coordinates.
     */
    public void addTreePin(double latitude, double longitude) {
        // ArcGIS Point uses (x = longitude, y = latitude)
        Point point = new Point(longitude, latitude, SpatialReferences.getWgs84());

        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(
                SimpleMarkerSymbol.Style.CIRCLE,
                0xFF00AA00,  // colour = opaque green
                8            // size in pixels
        );

        Graphic graphic = new Graphic(point, symbol);
        treeOverlay.getGraphics().add(graphic);
    }

    public void dispose() {
        mapView.dispose();
    }
}