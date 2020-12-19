package com.gv.adventofcode2020.dec16;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.*;

public class TicketTranslation {


    public static void main(String[] args) {
        TicketTranslation ticketTranslation = new TicketTranslation();
        InputReader inputReader = new InputReader();
        List<String> rangesList = inputReader.readData("dec-16-ranges.txt");
        List<String> nearbyList = inputReader.readData("dec-16-nearby.txt");
        //ticketTranslation.solution1(rangesList, nearbyList);
        ticketTranslation.solution2(rangesList, nearbyList);
    }

    private void solution2(List<String> rangesList, List<String> nearbyList) {
        int[] positions = getPositions(rangesList);
        List<String> validNearbyTicketsList = new ArrayList<>();
        for(String nearbyticket : nearbyList){
            if(isValidTicket(nearbyticket,positions)){
                validNearbyTicketsList.add(nearbyticket);
            }
        }
        Map<String, int[]> fieldRanges = getPositions2(rangesList);
        int[][] validTicketMatrix = new int[validNearbyTicketsList.size()][fieldRanges.size()];
        int ticketIndex = 0;
        for(String validTicket:validNearbyTicketsList){
            String[] ticketInfo = validTicket.split(",");
            int colIndex = 0;
            for(String ticketData:ticketInfo){
                validTicketMatrix[ticketIndex][colIndex] = Integer.parseInt(ticketData.trim());
                colIndex++;
            }
            ticketIndex++;
        }
        //System.out.println(validTicketMatrix);
        Map<String,List<Integer>>  mapping = determineMapping(validTicketMatrix,fieldRanges);
        Map<Integer,String> keyWithPossibilities = new TreeMap<>();
        for(String key : mapping.keySet()){
            keyWithPossibilities.put(mapping.get(key).size(),key);
        }
        Map<String,Integer> actualMapping = new HashMap<>();
        Set<Integer> locked = new HashSet<>();
        for(Integer possibiltyKey : keyWithPossibilities.keySet()){
            List<Integer> possibles = mapping.get(keyWithPossibilities.get(possibiltyKey));
            for(Integer possible : possibles){
                if(!locked.contains(possible)){
                    locked.add(possible);
                    actualMapping.put(keyWithPossibilities.get(possibiltyKey),possible);
                }
            }
        }
        long departure = 1L;
        int[] myTicket = new int[]{83,53,73,139,127,131,97,113,61,101,107,67,79,137,89,109,103,59,149,71};
        for(String actualKey: actualMapping.keySet()){
            if(actualKey.startsWith("departure")){
                departure  *= myTicket[actualMapping.get(actualKey)];
            }
        }
        System.out.println(departure);
    }



    private Map<String,List<Integer>>  determineMapping(int[][] validTicketMatrix, Map<String,int[]> fieldRanges) {
        Map<String,List<Integer>> positionMapping = new HashMap<>();
        for(String keys:fieldRanges.keySet()){
            positionMapping.put(keys,determineIndexPosition(fieldRanges.get(keys),validTicketMatrix));
        }
       return positionMapping;
    }

    private List<Integer> determineIndexPosition(int[] classRange, int[][] validTicketMatrix) {
        List<Integer> possiblePositions = new ArrayList<>();
        for(int col=0;col<validTicketMatrix[0].length;col++){
            if (isColumnMatched(classRange, validTicketMatrix, col)){
                possiblePositions.add(col);
            }
        }
        return possiblePositions;
    }

    private boolean isColumnMatched(int[] classRange, int[][] validTicketMatrix, int col) {
        for(int row =0; row<validTicketMatrix.length;row++){
            if(classRange[validTicketMatrix[row][col]] == 0){
                return false;
            }
        }
        return true;
    }


    private boolean isValidTicket(String ticket, int[] validPositions) {
        String[] nearbypositions = ticket.split(",");
        for (int i = 0; i < nearbypositions.length; i++) {
            Integer nearbyPosition = Integer.parseInt(nearbypositions[i].trim());
            if (validPositions[nearbyPosition] == 0) {
                return false;
            }
        }
        return true;
    }


    private Map<String, int[]> getPositions2(List<String> rangesList) {
        Map<String, int[]> fieldRanges = new HashMap<>();
        for (String rangeData : rangesList) {
            int[] positions = new int[1001];
            String[] mappings = rangeData.split(":");
            String range = mappings[1].trim();
            String[] ranges = range.split("or");
            String[] limits = ranges[0].trim().split("-");
            int low = Integer.parseInt(limits[0].trim());
            int high = Integer.parseInt(limits[1].trim());
            Arrays.fill(positions, low, high + 1, 1);
            limits = ranges[1].trim().split("-");
            low = Integer.parseInt(limits[0].trim());
            high = Integer.parseInt(limits[1].trim());
            Arrays.fill(positions, low, high + 1, 1);
            fieldRanges.put(mappings[0].trim(), positions);
        }
        return fieldRanges;
    }

    private void solution1(List<String> rangesList, List<String> nearbyList) {
        int[] positions = getPositions(rangesList);
        int errors = 0;
        for (String nearby : nearbyList) {
            String[] nearbypositions = nearby.split(",");
            for (int i = 0; i < nearbypositions.length; i++) {
                Integer nearbyPosition = Integer.parseInt(nearbypositions[i].trim());
                if (positions[nearbyPosition] == 0) {
                    errors += nearbyPosition;
                }
            }
        }
        System.out.println(errors);
    }

    private int[] getPositions(List<String> rangesList) {
        int[] positions = new int[1001];

        for (String rangeData : rangesList) {
            String range = rangeData.split(":")[1].trim();
            String[] ranges = range.split("or");
            String[] limits = ranges[0].trim().split("-");
            int low = Integer.parseInt(limits[0].trim());
            int high = Integer.parseInt(limits[1].trim());
            Arrays.fill(positions, low, high + 1, 1);

            limits = ranges[1].trim().split("-");
            low = Integer.parseInt(limits[0].trim());
            high = Integer.parseInt(limits[1].trim());
            Arrays.fill(positions, low, high + 1, 1);

        }
        return positions;
    }
}
