package com.gv.adventofcode2020.dec13;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.*;

public class ShuttleSearch {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        List<String> dataList = inputReader.readData("dec-13-input.txt");
        ShuttleSearch shuttleSearch = new ShuttleSearch();
        shuttleSearch.earliestBus(dataList);
        //shuttleSearch.shuttleContest(dataList);
    }

    private void shuttleContest(List<String> dataList){
        String buses = dataList.get(1);
        String[] timings  = buses.split(",");
        Long aheadTime =-1l;
        Map<Long,Long> busWithAheadTimes = new TreeMap<>(Collections.reverseOrder());
        for(String timing:timings){
            aheadTime =Long.sum(aheadTime,1l);
            if(!timing.equals("x")){
                busWithAheadTimes.put(Long.valueOf(timing),aheadTime);
            }
        }

        //This is brute force. Poor solution that worked based on random guesswork..Need to find a algorithm ( google and reddit says chineese reminder theoram)
        Long multiplier = 400000000000L;
        Boolean found = false;
        Map.Entry<Long,Long> entry = busWithAheadTimes.entrySet().iterator().next();
        Long biggestBusId = entry.getKey();
        Long biggestBusIdLeadTime = entry.getValue();
        while(!found){
            Long testValue = (biggestBusId * multiplier) - biggestBusIdLeadTime;
            if(testValue % 10000000L == 0L){
                System.out.println(testValue);
            }
            found = testCandidateTiming(busWithAheadTimes, testValue);
            multiplier = multiplier+1;
        }
        System.out.println(((multiplier-1)*biggestBusId)-biggestBusIdLeadTime);

    }

    private boolean testCandidateTiming(Map<Long, Long> busWithAheadTimes, Long testValue) {
        for(Long busesKey :busWithAheadTimes.keySet()){
            Long derivedValue = testValue+busWithAheadTimes.get(busesKey);
            Long moduloCandidate = derivedValue % busesKey;
            if(!(moduloCandidate.longValue() == 0l)){
                return false;
            }
        }
        return true;
    }


    private void earliestBus(List<String> dataList){
        Integer earliest = Integer.parseInt(dataList.get(0));
        String buses = dataList.get(1);
        String[] timings  = buses.split(",");
        final List<Integer> slots = new ArrayList<>();
        for(String slot:timings){
            if(!slot.equals("x")){
                slots.add(Integer.parseInt(slot));
            }
        }
        Map<Integer,Integer> waitTimes = new TreeMap<>();
        for(Integer bus:slots){
            Integer leadTime = earliest % bus;
            if(leadTime != 0){
                waitTimes.put( bus-leadTime,bus);
            }else{
                System.out.println("exact bus is "+bus);
            }
        }

        Map.Entry<Integer,Integer> entry = waitTimes.entrySet().iterator().next();
        Integer earliestBusId = entry.getKey();
        Integer earliestBusLeadTime = entry.getValue();
        System.out.println(earliestBusId*earliestBusLeadTime);
    }


}
