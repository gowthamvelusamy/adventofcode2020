package org.adventofcode2020.dec03;

import org.adventofcode2020.utils.InputReader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TobogganTrajectory {

    private int treeFinder(char[][] dataMatrix,int columnJump,int rowJump){
        int trees =0;
        int row=0;
        int column=0;
        while(row<dataMatrix.length-rowJump){
            column = column+columnJump;
            row = row+rowJump;
            if(dataMatrix[row][column] == '#'){
                trees++;
            }
        }
        return trees;
    }

    private int forestCreator(List<String> dataList,int columnJump,int rowJump){
        int columnsRep= (dataList.size()-1)*columnJump;
        int timesrep = (columnsRep/dataList.get(0).length())+1;
        final List<String> replicated = new ArrayList<>();
        for(String data:dataList){
            String repString = String.join("", Collections.nCopies(timesrep, data));
            replicated.add(repString);
        }
        char[][] repDataMatrix = new char[replicated.size()][replicated.get(0).length()];
        int row =0;
        int column = 0;
        for(final String data :replicated){
            for(char character: data.toCharArray()){
                repDataMatrix[row][column++] = character;
            }
            column =0;
            row++;
        }
        return treeFinder(repDataMatrix,columnJump,rowJump);
    }



    public static void main(String[] args) {
        final TobogganTrajectory tobogganTrajectory = new TobogganTrajectory();
        final InputReader inputReader = new InputReader();
        final List<String> dataList = inputReader.readData("dec-03-input.txt");
        System.out.println(new BigInteger(""+(tobogganTrajectory.forestCreator(dataList,1,1)))
                .multiply(new BigInteger(""+(tobogganTrajectory.forestCreator(dataList,3,1))))
                .multiply(new BigInteger(""+(tobogganTrajectory.forestCreator(dataList,5,1))))
                .multiply(new BigInteger(""+(tobogganTrajectory.forestCreator(dataList,7,1))))
                .multiply(new BigInteger(""+(tobogganTrajectory.forestCreator(dataList,1,2)))));
    }
}
