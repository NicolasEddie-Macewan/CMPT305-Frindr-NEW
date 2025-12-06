package com.mycompany.app;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollection;
import com.esri.arcgisruntime.data.FeatureCollectionTable;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureCollectionLayer;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.UniqueValueRenderer;
import com.mycompany.app.backend.fruit.AboutTree;
import com.mycompany.app.backend.fruit.CityLocation;
import com.mycompany.app.backend.fruit.Complete_tree;
import com.mycompany.app.backend.fruit.Tree;

import com.mycompany.app.ui.PinColours;
import javafx.scene.paint.Color;
import java.util.*;
import java.util.List;

public class FeatureLayerHandler {
    FeatureCollectionLayer featureLayer;
    PinColours pinColours = new PinColours();

    public FeatureLayerHandler(Complete_tree data) {
        FeatureCollectionTable table = new FeatureCollectionTable(
                generateFields(),
                GeometryType.POINT,
                SpatialReferences.getWgs84());

        for (Tree tree : data.getTrees()) {
            Map<String, Object> attributes = treeToHashMap(tree);
            Point point = getTreePoint(tree);
            Feature feature = table.createFeature(attributes, point);
            table.addFeatureAsync(feature);
        }

        UniqueValueRenderer renderer = new UniqueValueRenderer();
        renderer.getFieldNames().add("fruit");

        SimpleMarkerSymbol defaultSymbol = new SimpleMarkerSymbol(
                SimpleMarkerSymbol.Style.CIRCLE,
                Color.rgb(75, 175, 75),
                6
        );

        for (Map.Entry<String, Color> entry : pinColours.getPinColours().entrySet()) {
            String fruit = entry.getKey();
            Color color = entry.getValue();

            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(
                    SimpleMarkerSymbol.Style.CIRCLE,
                    color,
                    6
            );

            List<Object> values = new ArrayList<>();
            values.add(fruit);
            UniqueValueRenderer.UniqueValue uniqueValue = new UniqueValueRenderer.UniqueValue(fruit, fruit, symbol, values);
            renderer.getUniqueValues().add(uniqueValue);
        }

        table.setRenderer(renderer);

        FeatureCollection featureCollection = new FeatureCollection();
        featureCollection.getTables().add(table);
        featureLayer = new FeatureCollectionLayer(featureCollection);
    }

    public FeatureCollectionLayer getFeatureLayer() {
        return featureLayer;
    }

    private List<Field> generateFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(Field.createString("speciesBotanical", "Species, Botanical", 50));
        fields.add(Field.createString("speciesCommon", "Species, Common", 50));
        fields.add(Field.createString("fruit", "Fruit", 50));
        fields.add(Field.createString("datePlanted", "Date Planted", 50));
        return fields;
    }

    private Map<String, Object> treeToHashMap(Tree tree) {
        Map<String, Object> attrs = new HashMap<>();
        AboutTree aboutTree = tree.getAboutTree();
        attrs.put("speciesBotanical", aboutTree.getSpeciesBiological());
        attrs.put("speciesCommon", aboutTree.getSpeciesCommon());
        attrs.put("fruit", aboutTree.getFruitType());
        attrs.put("datePlanted", aboutTree.getPlanted().toString());
        return attrs;
    }

    private Point getTreePoint(Tree tree) {
        CityLocation location = tree.getCitylocation();
        return new Point(location.getCoordinates().getLongitude(), location.getCoordinates().getLatitude(), SpatialReferences.getWgs84());
    }
}
