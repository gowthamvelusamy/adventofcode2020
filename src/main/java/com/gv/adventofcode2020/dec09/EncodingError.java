package com.gv.adventofcode2020.dec09;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.*;
import java.util.stream.Collectors;

public class EncodingError {

    private void solution(final List<Long> dataList,Integer preambleSize){
        List<Long> preList = dataList.subList(0,preambleSize);
        for(int i=preambleSize;i<dataList.size();i++){
            Long newData = dataList.get(i);
            if(isTwoArrayNumbersAddToTarget(new ArrayList<>(preList),newData)){
                preList.remove(0);
                preList.add(newData);
            }else{
                System.out.println(newData);
                break;
            }
        }
    }


    private void slidingSummation(final List<Long> dataList,Long targetSum){
        int topIndex = dataList.indexOf(targetSum);
        int bottamIndex = topIndex -1;
        while(topIndex >= 0){
            Long sum = dataList.subList(bottamIndex, topIndex).stream().collect(Collectors.summingLong(Long::longValue));
            if(sum.longValue() == targetSum.longValue()){
               break;
            }else if(sum.longValue() > targetSum.longValue()){
                if(topIndex == bottamIndex+1){
                    bottamIndex = bottamIndex -1;
                }
                topIndex = topIndex -1;
            }else{
                bottamIndex =bottamIndex-1;
            }
        }
        System.out.println(topIndex);
        System.out.println(bottamIndex);
    }




    private boolean isTwoArrayNumbersAddToTarget(ArrayList<Long> numbers, Long sum) {
        Collections.sort(numbers);
        boolean isSum = false;
        int left = 0;
        int right = numbers.size() - 1;
        if (numbers.get(left).longValue() >= sum.longValue()) return isSum;
        while (numbers.get(right).longValue() >= sum.longValue()) {
            right--;
        }
        while (left < right && numbers.get(left) < sum) {
            Long leftNum = numbers.get(left);
            Long rightNum = numbers.get(right);
            Long temp = Long.sum(leftNum , rightNum);
            if (temp.longValue() == sum.longValue()) {
                isSum = true;
                break;
            } else if (temp.longValue() > sum.longValue()) {
                right--;
            } else {
                left++;
            }
        }
        return isSum;
    }


    public static void main(String[] args) {
        EncodingError encodingError = new EncodingError();
        final InputReader inputReader = new InputReader();
        //final List<Long> da = inputReader.readNumbersasLong("dec-09-input.txt");
       // final List<Integer> dataList = inputReader.readNumbers("dec-09-sample.txt");
       // encodingError.solution(dataList,5);
        //System.out.println(da.get(da.size()-1));
        final List<Long> dataList = inputReader.readNumbersasLong("dec-09-input.txt");
        //encodingError.solution(dataList,25);

        //String target ="3199139634";
        //encodingError.contSum(dataList,Long.valueOf("3199139634"));
        System.out.println(dataList.subList(562, 579).stream().collect(Collectors.summingLong(Long::longValue)));
        List<Long> n = new ArrayList<>(dataList.subList(562, 579));
        Collections.sort(n);
        Long low= n.get(0);
        Long high = n.get(n.size()-1);
        System.out.println(Long.sum(low,high));

    }
}
