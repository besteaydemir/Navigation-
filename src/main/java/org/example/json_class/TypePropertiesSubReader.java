package org.example.json_class;

public class TypePropertiesSubReader {
    public String type;
    public TypeCoordinatesSubSubReader geometry;
    public TypeSubSubSubReader properties;

    public TypePropertiesSubReader(String type, TypeCoordinatesSubSubReader geometry, TypeSubSubSubReader properties) {
        this.type = type;
        this.geometry = geometry;
        this.properties = properties;
    }

    public TypePropertiesSubReader() {
    }


}
