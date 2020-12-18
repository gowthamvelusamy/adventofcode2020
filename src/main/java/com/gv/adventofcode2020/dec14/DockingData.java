package com.gv.adventofcode2020.dec14;

import com.gv.adventofcode2020.utils.InputReader;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DockingData {



    public static void main(String[] args) {
        DockingData dockingData = new DockingData();
        InputReader inputReader = new InputReader();
        List<String> dataList = inputReader.readData("dec-14-input.txt");
        dockingData.solution2(dataList);

    }

    private void solution2(List<String> dataList) {
        Map<String, String> memoryContents = new HashMap<>();
        String mask = "";
        for (String data : dataList) {
            String input[] = data.split("=");
            if (data.startsWith("mask")) {
                mask = input[1].trim();
            } else {
                String key = input[0].trim();
                String memKey = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
                String newMemory  = applyMask2(mask, memKey.trim());
                List<String> newMemories  = getNewMemories(newMemory);
                for(String memory:newMemories){
                    memoryContents.put(memory,input[1].trim());
                }
            }
        }
        //System.out.println(memoryContents);
        Long sum = 0L;
        for (String key : memoryContents.keySet()) {
            sum += Long.valueOf(memoryContents.get(key));
        }
        System.out.println(sum);

    }


    private List<String> getNewMemories(String newMemory){
        List<String> newMemories = new ArrayList<>();
        int wildChars = StringUtils.countMatches(newMemory,"X");
        Long numberOfCombos = (long)Math.pow(2, wildChars);
        List<String> floats = new ArrayList<>();
        for(long index=0;index<numberOfCombos;index++){
            floats.add(StringUtils.leftPad(Long.toBinaryString(index),wildChars,"0"));
        }
        //System.out.println(floats);
        char[] newMemoryContent = newMemory.toCharArray();
        for(String floatReplacement:floats){
            int floatIndex =0;
            String newMemoryString = "";
            for(int i=0;i<newMemoryContent.length;i++){
                if (newMemoryContent[i] == 'X') {
                    char replacementChar = floatReplacement.charAt(floatIndex);
                    floatIndex++;
                    newMemoryString = newMemoryString+replacementChar;
                }else{
                    newMemoryString = newMemoryString+newMemoryContent[i];
                }
            }
            newMemories.add(newMemoryString);
        }
        return newMemories;
    }


    private String applyMask2(String mask, String memory) {

        String newString = "";
        String binaryString = Long.toBinaryString(Long.valueOf(memory));
        if (mask.length() < binaryString.length()) {
            mask = StringUtils.leftPad(mask, binaryString.length(), "0");
        } else {
            binaryString = StringUtils.leftPad(binaryString, mask.length(), "0");
        }

        char[] maskData = mask.toCharArray();
        char[] originalData = binaryString.toCharArray();
        for (int index = 0; index < mask.length(); index++) {
            if (maskData[index] == 'X') {
                newString = newString + "X";
            } else if (maskData[index] == '0') {
                newString = newString + originalData[index];
            } else {
                newString = newString + "1";
            }
        }
        return newString;
    }




    private void solution1(List<String> dataList) {


        Map<String, String> memoryContents = new HashMap<>();
        String mask = "";
        for (String data : dataList) {
            String input[] = data.split("=");
            if (data.startsWith("mask")) {
                mask = input[1].trim();
            } else {
                String key = input[0].trim();
                String memKey = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
                memoryContents.put(memKey, applyMask(mask, input[1].trim()));
            }
        }
        //System.out.println(memoryContents);
        Long sum = 0L;
        for (String key : memoryContents.keySet()) {
            sum += Long.parseLong(memoryContents.get(key), 2);
        }
        System.out.println(sum);
    }


    private String applyMask(String mask, String original) {
        String newString = "";
        String binaryString = Long.toBinaryString(Long.valueOf(original));
        if (mask.length() < binaryString.length()) {
            mask = StringUtils.leftPad(mask, binaryString.length(), "0");
        } else {
            binaryString = StringUtils.leftPad(binaryString, mask.length(), "0");
        }
        // System.out.println(original);
        // System.out.println("mask :"+mask);
        // System.out.println("bins :"+binaryString);

        char[] maskData = mask.toCharArray();
        char[] originalData = binaryString.toCharArray();
        for (int index = 0; index < mask.length(); index++) {
            if (maskData[index] == 'X') {
                newString = newString + originalData[index];
            } else if (maskData[index] == '0') {
                newString = newString + "0";
            } else {
                newString = newString + "1";
            }
        }
        return newString;
    }


}
