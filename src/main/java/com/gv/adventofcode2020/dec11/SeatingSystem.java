package com.gv.adventofcode2020.dec11;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.Arrays;
import java.util.List;

public class SeatingSystem {

    public static void main(String[] args) {
        InputReader reader = new InputReader();
        SeatingSystem seatingSystem = new SeatingSystem();
        List<String> dataList = reader.readData("dec-11-input.txt");
        char[][] seatMatrix = new char[dataList.size()][dataList.get(0).length()];
        int seatRow = 0;
        for (String data : dataList) {
            int seatColumn = 0;
            for (char seat : data.toCharArray()) {
                seatMatrix[seatRow][seatColumn++] = seat;
            }
            seatRow++;
        }

        //PART 1
        seatingSystem.fillSeatsBasedOnNeighbour(seatMatrix, 4, false);
        long start = System.currentTimeMillis();
        // PART 2
        seatingSystem.fillSeatsBasedOnNeighbour(seatMatrix, 5, true);
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
    }

    private void fillSeatsBasedOnNeighbour(char[][] seatMatrix,
                                           int crowdedSeatsThreshold,
                                           boolean isVisiblityMatters) {

        char[][] newSeatMatrix = null;
        while (true) {
            newSeatMatrix = new char[seatMatrix.length][seatMatrix[0].length];
            for (int i = 0; i < seatMatrix.length; i++) {
                for (int j = 0; j < seatMatrix[0].length; j++) {
                    newSeatMatrix[i][j] = seatMatrix[i][j];
                    if (seatMatrix[i][j] != '.') {
                        newSeatMatrix = fillSeatLookAround(i, j, seatMatrix, newSeatMatrix, crowdedSeatsThreshold, isVisiblityMatters);
                    }
                }
            }
            if (Arrays.deepEquals(seatMatrix, newSeatMatrix)) {
                break;
            } else {
                seatMatrix = newSeatMatrix;
            }
        }

        int occupiedAndLockedSeatsCount = 0;
        for (int i = 0; i < seatMatrix.length; i++) {
            for (int j = 0; j < seatMatrix[0].length; j++) {
                if (newSeatMatrix[i][j] == '#') {
                    occupiedAndLockedSeatsCount++;
                }
            }
        }
        System.out.println(occupiedAndLockedSeatsCount);
    }


    private char[][] fillSeatLookAround(int i,
                                        int j,
                                        char[][] seatMatrix,
                                        char[][] newSeatMatrix,
                                        int crowdedSeatsThreshold,
                                        boolean isVisiblityMatters) {

        if (seatMatrix[i][j] == 'L') {
            if ((!isNeighbourFound(i, j + 1, 0, 1, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i, j - 1, 0, -1, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i - 1, j, -1, 0, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i + 1, j, 1, 0, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i - 1, j - 1, -1, -1, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i - 1, j + 1, -1, 1, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i + 1, j - 1, 1, -1, seatMatrix, '#', isVisiblityMatters))
                    && (!isNeighbourFound(i + 1, j + 1, 1, 1, seatMatrix, '#', isVisiblityMatters))
            ) {
                newSeatMatrix[i][j] = '#';
            }
        } else {
            int neighboursFilled = 0;
            if (isNeighbourFound(i, j - 1, 0, -1, seatMatrix, '#', isVisiblityMatters)) {
                neighboursFilled++;
            }
            if (isNeighbourFound(i, j + 1, 0, 1, seatMatrix, '#', isVisiblityMatters)) {
                neighboursFilled++;
            }
            if (isNeighbourFound(i - 1, j, -1, 0, seatMatrix, '#', isVisiblityMatters)) {
                neighboursFilled++;
            }
            if (isNeighbourFound(i + 1, j, 1, 0, seatMatrix, '#', isVisiblityMatters)) {
                neighboursFilled++;
            }
            if ((neighboursFilled < crowdedSeatsThreshold)
                    && (isNeighbourFound(i - 1, j - 1, -1, -1, seatMatrix, '#', isVisiblityMatters))) {
                neighboursFilled++;
            }
            if ((neighboursFilled < crowdedSeatsThreshold)
                    && (isNeighbourFound(i - 1, j + 1, -1, 1, seatMatrix, '#', isVisiblityMatters))) {
                neighboursFilled++;
            }
            if ((neighboursFilled < crowdedSeatsThreshold)
                    && (isNeighbourFound(i + 1, j - 1, 1, -1, seatMatrix, '#', isVisiblityMatters))) {
                neighboursFilled++;
            }
            if ((neighboursFilled < crowdedSeatsThreshold)
                    && (isNeighbourFound(i + 1, j + 1, 1, 1, seatMatrix, '#', isVisiblityMatters))) {
                neighboursFilled++;
            }
            if (neighboursFilled >= crowdedSeatsThreshold) {
                newSeatMatrix[i][j] = 'L';
            }
        }
        return newSeatMatrix;
    }

    private boolean isNeighbourFound(int i,
                                     int j,
                                     int iIncr,
                                     int jIncr,
                                     char[][] seatMatrix,
                                     char searchFor,
                                     boolean isVisiblityMatters) {
        if (i < 0 || i >= seatMatrix.length || j < 0 || j >= seatMatrix[0].length) {
            return false;
        }
        if (seatMatrix[i][j] == searchFor) {
            return true;
        } else if (seatMatrix[i][j] == '.') {
            if (isVisiblityMatters) {
                return isNeighbourFound(i + iIncr, j + jIncr, iIncr, jIncr, seatMatrix, searchFor, isVisiblityMatters);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
