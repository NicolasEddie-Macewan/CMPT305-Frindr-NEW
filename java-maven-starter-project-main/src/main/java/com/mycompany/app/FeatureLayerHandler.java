package com.mycompany.app;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureCollectionTable;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.mycompany.app.backend.fruit.AboutTree;
import com.mycompany.app.backend.fruit.CityLocation;
import com.mycompany.app.backend.fruit.Complete_tree;
import com.mycompany.app.backend.fruit.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureLayerHandler {
    FeatureLayer featureLayer;

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

        featureLayer = new FeatureLayer(table);
    }

    public FeatureLayer getFeatureLayer() {
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
        attrs.put("datePlanted", aboutTree.getPlanted());
        return attrs;
    }

    private Point getTreePoint(Tree tree) {
        CityLocation location = tree.getCitylocation();
        return new Point(location.getCoordinates().getLongitude(), location.getCoordinates().getLatitude(), SpatialReferences.getWgs84());
    }
}
