package com.gv.adventofcode2020.dec10;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.Collections;
import java.util.List;

public class AdapterArray {

    private void joltDifferenceProduct(List<Integer> dataList) {

        Collections.sort(dataList);
        dataList.add(dataList.get(dataList.size() - 1) + 3);
        Integer position = 0;
        Integer diff_1 = 0;
        Integer diff_2 = 0;
        Integer diff_3 = 0;
        for (Integer data : dataList) {
            if (position + 1 == data) {
                diff_1++;
            } else if (position + 2 == data) {
                diff_2++;
            } else {
                diff_3++;
            }
            position = data;
        }
        System.out.println(diff_1 * diff_3);
    }

    private void distinctWaysToArrangeAdapters(List<Integer> dataList) {

        Collections.sort(dataList);
        dataList.add(dataList.get(dataList.size() - 1) + 3);
        final long[] distinctWays = new long[dataList.get(dataList.size() - 1)+1];
        distinctWays[0] = 0;
        if (dataList.get(0) == 1) {
            distinctWays[1] = 1;
        } else if (dataList.get(0) == 2) {
            distinctWays[2] = 1;
        } else if (dataList.get(0) == 3) {
            distinctWays[3] = 1;
        }
        if(dataList.get(1) == 2){
            distinctWays[2] = distinctWays[1] + 1;
        }else if(dataList.get(1) == 3){
            distinctWays[3] = distinctWays[1]+distinctWays[2]+1;
        }
        if(dataList.get(2) == 3){
            distinctWays[3] = distinctWays[1]+distinctWays[2]+1;
        }

        for (int i = 4; i < dataList.get(dataList.size()-1); i++) {
            if( dataList.contains(i)){
                distinctWays[i] = distinctWays[i-1] + distinctWays[i-2] + distinctWays[i-3];
            }
        }
        System.out.println(distinctWays[dataList.get(dataList.size()-1)-3]);

    }


    public static void main(String[] args) {
        AdapterArray adapterArray = new AdapterArray();
        List<Integer> dataList = new InputReader().readNumbers("dec-10-input.txt");
        adapterArray.joltDifferenceProduct(dataList);
        adapterArray.distinctWaysToArrangeAdapters(dataList);
    }
}
