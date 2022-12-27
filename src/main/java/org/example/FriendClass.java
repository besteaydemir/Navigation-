package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
    }
}

