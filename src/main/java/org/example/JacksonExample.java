//package de.lmu.ifi.dbs.sysdev.examples.jackson;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class JacksonExample {
//    public static void main(String[] args) throws IOException {
//        // Create once, re-use
//        final ObjectMapper mapper = new ObjectMapper();
//
//        // Read JSON
//        // from file
//        final var values = mapper
//                .readValue(
//                        new File("/home/strauss/temp/sysdev/test.json"),
//                        new TypeReference<List<TestJsonPojo>>() {});
//
//        final var values2 = mapper
//                .readValue(
//                        new File("/home/strauss/temp/sysdev/test.json"),
//                        TestJsonPojo[].class);
//
//        int a = 3;
//    }
//
//}
