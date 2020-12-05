package org.adventofcode2020.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputReader {

    public  List<Integer> readNumbers(String file){
        List<Integer> dataList = null;
        URL resource = getClass().getClassLoader().getResource(file);
        try
        {
            Path path = Paths.get(resource.toURI());
            try (Stream<String> stream = Files.lines(path)) {
                dataList = stream.map(Integer :: parseInt).collect(Collectors.toList());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return dataList;
    }

    public  List<String> readData(String file){
        List<String> dataList = null;
        URL resource = getClass().getClassLoader().getResource(file);
        try
        {
            Path path = Paths.get(resource.toURI());
            try (Stream<String> stream = Files.lines(path)) {
                dataList = stream.collect(Collectors.toList());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return dataList;
    }


    public String[] splitter(String data,String key){
        return data.split(key);
    }
}
