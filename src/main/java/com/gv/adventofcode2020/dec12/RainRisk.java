package com.gv.adventofcode2020.dec12;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RainRisk {

    public static void main(String[] args) {
        final InputReader inputReader = new InputReader();
        final RainRisk rainRisk = new RainRisk();
        final List<String> dataList = inputReader.readData("dec-12-input.txt");
        rainRisk.shipLocationMoving(dataList);
        rainRisk.shipLocationMovingWayPoint(dataList);
    }

    private void shipLocationMoving(List<String> dataList) {

        Map<Character, Integer> coOrdinates = new HashMap<>();

        char currentdir = 'E';
        for (final String data : dataList) {
            char action = data.toCharArray()[0];
            Integer magnitude = Integer.parseInt(data.substring(1));
            if (action == 'R' || action == 'L') {
                currentdir = getNewDirection(magnitude, currentdir, action);
            } else if (action == 'F') {
                coOrdinates.put(currentdir, coOrdinates.getOrDefault(currentdir, 0) + magnitude);
            } else {
                coOrdinates.put(action, coOrdinates.getOrDefault(action, 0) + magnitude);
            }
        }

        int manhatDis = (Math.abs(coOrdinates.getOrDefault('N', 0)
                - coOrdinates.getOrDefault('S', 0)))
                + (Math.abs(coOrdinates.getOrDefault('E', 0)
                - coOrdinates.getOrDefault('W', 0)));
        System.out.println(manhatDis);

    }


    private char getNewDirection(int degree, char currdir, char swing) {
        char[] dir = new char[4];
        dir[0] = 'E';
        dir[1] = 'S';
        dir[2] = 'W';
        dir[3] = 'N';

        char newdir = currdir;

        for (int i = 0; i < 4; i++) {
            if (dir[i] == currdir) {
                if (swing == 'R') {
                    newdir = dir[(4 + (i + (degree / 90))) % 4];
                } else {
                    newdir = dir[(4 + (i - (degree / 90))) % 4];
                }
            }
        }
        return newdir;
    }
    private void shipLocationMovingWayPoint(List<String> dataList) {
        Postition currentPostition = new Postition();

        final CoOrdinates wayPointCoordinates = new CoOrdinates();
        wayPointCoordinates.setEw_direction('E');
        wayPointCoordinates.setEw_magnitude(10);
        wayPointCoordinates.setNs_direction('N');
        wayPointCoordinates.setNs_magnitude(1);
        currentPostition.setWaypointPosition(wayPointCoordinates);

        final CoOrdinates shipCoOrdinates = new CoOrdinates();
        shipCoOrdinates.setEw_magnitude(0);
        shipCoOrdinates.setNs_magnitude(0);
        currentPostition.setShipPosition(shipCoOrdinates);

        for (String data : dataList) {
            char action = data.toCharArray()[0];
            final Integer magnitude = Integer.parseInt(data.substring(1));
            currentPostition = findShipPosition(action, magnitude, currentPostition);
        }
        CoOrdinates finalShipPosition = currentPostition.getShipPosition();
        System.out.println(finalShipPosition.getEw_magnitude() + finalShipPosition.getNs_magnitude());

    }

    private Postition findShipPosition(char instruction, Integer magnitude, Postition currentPostition) {
        CoOrdinates currentShipPosition = currentPostition.getShipPosition();
        CoOrdinates currentWaypointPosition = currentPostition.getWaypointPosition();
        CoOrdinates newData = null;
        switch (instruction) {
            case 'F':
                currentShipPosition = calculateShipPosition(magnitude, currentShipPosition, currentWaypointPosition);
                break;
            case 'R':
            case 'L':
                currentWaypointPosition = calcluateWayPointSpin(currentWaypointPosition, instruction, magnitude);
                break;
            case 'N':
                newData = new CoOrdinates();
                newData.setNs_magnitude(magnitude);
                newData.setNs_direction('N');
                currentWaypointPosition = handleNSCoOrdinates(currentWaypointPosition, newData, 1);
                break;
            case 'E':
                newData = new CoOrdinates();
                newData.setEw_magnitude(magnitude);
                newData.setEw_direction('E');
                currentWaypointPosition = handleEWCoOrdinates(currentWaypointPosition, newData, 1);
                break;
            case 'S':
                newData = new CoOrdinates();
                newData.setNs_magnitude(magnitude);
                newData.setNs_direction('S');
                currentWaypointPosition = handleNSCoOrdinates(currentWaypointPosition, newData, 1);
                break;
            case 'W':
                newData = new CoOrdinates();
                newData.setEw_magnitude(magnitude);
                newData.setEw_direction('W');
                currentWaypointPosition = handleEWCoOrdinates(currentWaypointPosition, newData, 1);
                break;
        }
        currentPostition.setWaypointPosition(currentWaypointPosition);
        currentPostition.setShipPosition(currentShipPosition);
        return currentPostition;
    }


    private CoOrdinates calcluateWayPointSpin(CoOrdinates wayPointCoOrdinates, char swing, Integer degree) {


        char[] dir = new char[4];
        dir[0] = 'E';
        dir[1] = 'S';
        dir[2] = 'W';
        dir[3] = 'N';

        char newdirOfEW = wayPointCoOrdinates.getEw_direction();

        for (int i = 0; i < 4; i++) {
            if (dir[i] == wayPointCoOrdinates.getEw_direction()) {
                if (swing == 'R') {
                    newdirOfEW = dir[(4 + (i + (degree / 90))) % 4];
                } else {
                    newdirOfEW = dir[(4 + (i - (degree / 90))) % 4];
                }
            }
        }

        char newdirOfNS = wayPointCoOrdinates.getNs_direction();

        for (int i = 0; i < 4; i++) {
            if (dir[i] == wayPointCoOrdinates.getNs_direction()) {
                if (swing == 'R') {
                    newdirOfNS = dir[(4 + (i + (degree / 90))) % 4];
                } else {
                    newdirOfNS = dir[(4 + (i - (degree / 90))) % 4];
                }
            }
        }

        CoOrdinates newWayPointCoOrdinates = new CoOrdinates();
        if (Arrays.asList('E', 'W').contains(newdirOfEW)) {
            newWayPointCoOrdinates.setEw_direction(newdirOfEW);
            newWayPointCoOrdinates.setEw_magnitude(wayPointCoOrdinates.getEw_magnitude());
        } else {
            newWayPointCoOrdinates.setNs_direction(newdirOfEW);
            newWayPointCoOrdinates.setNs_magnitude(wayPointCoOrdinates.getEw_magnitude());
        }

        if (Arrays.asList('N', 'S').contains(newdirOfNS)) {
            newWayPointCoOrdinates.setNs_direction(newdirOfNS);
            newWayPointCoOrdinates.setNs_magnitude(wayPointCoOrdinates.getNs_magnitude());
        } else {
            newWayPointCoOrdinates.setEw_direction(newdirOfNS);
            newWayPointCoOrdinates.setEw_magnitude(wayPointCoOrdinates.getNs_magnitude());
        }
        return newWayPointCoOrdinates;

    }


    private CoOrdinates calculateShipPosition(Integer magnitude,
                                              CoOrdinates currentShipPosition,
                                              CoOrdinates currentWaypointPosition) {
        currentShipPosition = handleEWCoOrdinates(currentShipPosition, currentWaypointPosition, magnitude);
        currentShipPosition = handleNSCoOrdinates(currentShipPosition, currentWaypointPosition, magnitude);
        return currentShipPosition;
    }


    private CoOrdinates handleNSCoOrdinates(CoOrdinates currentData, CoOrdinates newData, Integer multiplier) {
        Integer additionalMagnitude = multiplier * newData.getNs_magnitude();
        if (currentData.getNs_direction() == newData.getNs_direction()) {
            currentData.setNs_magnitude(currentData.getNs_magnitude() + additionalMagnitude);
        } else {
            if (currentData.getNs_magnitude() < additionalMagnitude) {
                currentData.setNs_direction(newData.getNs_direction());
            }
            currentData.setNs_magnitude(Math.abs(currentData.getNs_magnitude() - additionalMagnitude));
        }
        return currentData;
    }

    private CoOrdinates handleEWCoOrdinates(CoOrdinates currentData, CoOrdinates newData, Integer multiplier) {
        Integer additionalMagnitude = multiplier * newData.getEw_magnitude();
        if (currentData.getEw_direction() == newData.getEw_direction()) {
            currentData.setEw_magnitude(currentData.getEw_magnitude() + additionalMagnitude);
        } else {
            if (currentData.getEw_magnitude() < additionalMagnitude) {
                currentData.setEw_direction(newData.getEw_direction());
            }
            currentData.setEw_magnitude(Math.abs(currentData.getEw_magnitude() - additionalMagnitude));
        }
        return currentData;
    }

    private class Postition {
        CoOrdinates shipPosition;
        CoOrdinates waypointPosition;

        public CoOrdinates getShipPosition() {
            return shipPosition;
        }

        public void setShipPosition(CoOrdinates shipPosition) {
            this.shipPosition = shipPosition;
        }

        public CoOrdinates getWaypointPosition() {
            return waypointPosition;
        }

        public void setWaypointPosition(CoOrdinates waypointPosition) {
            this.waypointPosition = waypointPosition;
        }
    }


    private class CoOrdinates {

        char ew_direction;
        char ns_direction;
        Integer ew_magnitude;
        Integer ns_magnitude;

        public Integer getEw_magnitude() {
            return ew_magnitude;
        }

        public void setEw_magnitude(Integer ew_magnitude) {
            this.ew_magnitude = ew_magnitude;
        }

        public Integer getNs_magnitude() {
            return ns_magnitude;
        }

        public void setNs_magnitude(Integer ns_magnitude) {
            this.ns_magnitude = ns_magnitude;
        }


        public char getEw_direction() {
            return ew_direction;
        }

        public void setEw_direction(char ew_direction) {
            this.ew_direction = ew_direction;
        }

        public char getNs_direction() {
            return ns_direction;
        }

        public void setNs_direction(char ns_direction) {
            this.ns_direction = ns_direction;
        }
    }
}
