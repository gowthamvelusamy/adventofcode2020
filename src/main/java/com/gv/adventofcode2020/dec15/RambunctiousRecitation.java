package com.gv.adventofcode2020.dec15;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class RambunctiousRecitation {

    public static void main(String[] args) {
        RambunctiousRecitation rambunctiousRecitation = new RambunctiousRecitation();
        String sampledata = "7,14,0,17,11,1,2";

        Long start = System.currentTimeMillis();
        rambunctiousRecitation.solution(sampledata, 30000000L);
        Long stop = System.currentTimeMillis();
        System.out.println(stop - start);

        start = System.currentTimeMillis();
        rambunctiousRecitation.solution(sampledata, 30000000L);
        stop = System.currentTimeMillis();
        System.out.println(stop - start);
    }


    private void solution(String data, Long turnCount) {
        Long[] numbers = new Long[StringUtils.countMatches(data, ",") + 1];
        int index = 0;
        for (String numberString : data.split(",")) {
            numbers[index++] = Long.valueOf(numberString);
        }
        Map<Long, Map<String, Long>> turnData = new HashMap<>();
        Long turnIdx = 0L;
        long recentNumber = 0L;
        for (Long num : numbers) {
            turnIdx++;
            Map<String, Long> turns = turnData.get(num);
            if (turns == null) {
                turns = new HashMap<>();
                turns.put("F", turnIdx);
                turnData.put(num, turns);
            }
            recentNumber = num;
        }
        while (turnIdx < turnCount) {
            turnIdx++;
            Map<String, Long> turns = turnData.get(recentNumber);
            if (turns.get("S") == null) {
                recentNumber = 0;
            } else {
                Long prevOcc = turns.get("S");
                Long prevBeforeOcc = turns.get("F");
                recentNumber = prevOcc - prevBeforeOcc;
            }
            turns = turnData.get(recentNumber);
            if (turns == null) {
                turns = new HashMap<>();
                turns.put("F", turnIdx);
                turnData.put(recentNumber, turns);
            } else if (turns.get("S") == null) {
                turns.put("S", turnIdx);
                turnData.put(recentNumber, turns);
            } else {
                turns.put("F", turns.get("S"));
                turns.put("S", turnIdx);
            }
        }
        System.out.println(recentNumber);
    }


}
