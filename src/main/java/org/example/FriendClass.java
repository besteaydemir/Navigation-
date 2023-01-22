package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendClass {
    public String name;
    public int value;
    public List<FriendClass> friends;


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        FriendClass[] names = mapper.readValue(new File("C:\\Users\\Beste Aydemir\\IdeaProjects\\SysDevProjectTest\\src\\main\\java\\org\\example\\test.json"), FriendClass[].class);

        System.out.println(names);
        List<FriendClass>  friends = mapper.readValue(new File("C:\\Users\\Beste Aydemir\\IdeaProjects\\SysDevProjectTest\\src\\main\\java\\org\\example\\test.json"), new TypeReference<List<FriendClass>>() {});


        Class1 sch = mapper.readValue(new File("src/main/java/org/example/schleswig-holstein.json"), Class1.class);
//        String jsonString = mapper.writeValueAsString(sch);
//        System.out.println(jsonString);

        //List<List<Double>> coordinates = new ArrayList<>();
        ArrayList<ArrayList<Double>> coordinates = sch.features.get(0).geometry.coordinates;


        Class4 c4 = new Class4();
        c4.maxspeed = 0;
        Class3 c3 = new Class3();
        c3.type = "";
        c3.coordinates = coordinates;
        Class2 c2 = new Class2();
        c2.type = "";
        c2.geometry = c3;
        c2.properties = c4;
        ArrayList<Class2> features = new ArrayList<Class2>();
        features.add(c2);
        Class1 c1 = new Class1();
        c1.type = "";
        c1.features = features;


        String jsonString = mapper.writeValueAsString(c1);
        System.out.println(jsonString);



    }
}

