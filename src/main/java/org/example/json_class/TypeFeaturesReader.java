package org.example.json_class;

import java.util.ArrayList;

public class TypeFeaturesReader {
    public String type;
    public ArrayList<TypePropertiesSubReader> features;

    public TypeFeaturesReader(String type, ArrayList<TypePropertiesSubReader> features) {
        this.type = type;
        this.features = features;
    }

    public TypeFeaturesReader() {
    }
}


