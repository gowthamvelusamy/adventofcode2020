package org.adventofcode2020.dec05;

import org.adventofcode2020.utils.InputReader;

import java.util.List;

public class BinaryBoarding {
    InputReader inputReader = new InputReader();


    private void solution(List<String> dataList,int rows,int columns){
        int high=0;
        int[] seats = new int[rows*columns];
        for(String data:dataList){
            int seatId = 0;
            int startRow = 0;
            int limitRow = rows-1;
            int startColumn =0;
            int limitColumn = columns-1;
            for(char locator:data.toCharArray()){
                if(locator == 'F'){
                    limitRow = ((startRow+limitRow+1)/2) -1;
                }else if(locator == 'B'){
                    startRow = ((startRow+limitRow+1)/2);
                }else if(locator == 'L'){
                    limitColumn = ((startColumn+limitColumn+1)/2) -1;
                }else{
                    startColumn = ((startColumn+limitColumn+1)/2);
                }
            }
            if(startRow==limitRow){
                if(startColumn == limitColumn){
                    seatId = startRow*8+startColumn;
                    seats[seatId-1] = seatId;
                    if(seatId>high){
                        high = seatId;
                    }
                }
            }
        }
        for(int i=50;i<(rows*columns)-50;i++){
            if(seats[i] == 0 && seats[i-1] !=0 && seats[i+1] !=0){
                System.out.println(i+1);
            }
        }
    }

    public static void main(String[] args) {
        BinaryBoarding binaryBoarding = new BinaryBoarding();
        InputReader inputReader = new InputReader();
        List<String> dataList = inputReader.readData("dec-05-input.txt");
        binaryBoarding.solution(dataList,128,8);
    }
}
