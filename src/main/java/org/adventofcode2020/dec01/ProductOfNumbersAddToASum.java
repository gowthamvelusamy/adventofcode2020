package org.adventofcode2020.dec01;

import org.adventofcode2020.utils.InputReader;

import java.util.Collections;
import java.util.List;

public class ProductOfNumbersAddToASum {


    private Integer findProductOfTwoNumbersAddToSum(List<Integer> numbers, Integer sum) {
        Integer product = 0;
        int left = 0;
        int right = numbers.size() - 1;
        if (numbers.get(left) >= sum) return product;
        while (numbers.get(right) >= sum) {
            right--;
        }
        while (left < right && numbers.get(left) < sum) {
            int leftNum = numbers.get(left);
            int rightNum = numbers.get(right);
            int temp = leftNum + rightNum;
            if (temp == sum) {
                return leftNum * rightNum;
            } else if (temp > sum) {
                right--;
            } else {
                left++;
            }
        }
        return product;
    }

    private Integer findProductOfThreeNumbersAddToSum(List<Integer> numbers, Integer sum) {

        for (int i = 0; i < numbers.size(); i++) {
            int first = numbers.get(i);
            if (first >= sum) return 0;
            final Integer productOfSecondAndThird = findProductOfTwoNumbersAddToSum(numbers.subList(i + 1, numbers.size()), sum - first);
            if (productOfSecondAndThird != 0) {
                return productOfSecondAndThird*first;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        final List<Integer> numbers = new InputReader().readNumbers("dec-01-input.txt");
        Collections.sort(numbers);
        ProductOfNumbersAddToASum productOfNumbersAddToASum = new ProductOfNumbersAddToASum();
        System.out.println(productOfNumbersAddToASum.findProductOfTwoNumbersAddToSum(numbers, 2020));
        System.out.println(productOfNumbersAddToASum.findProductOfThreeNumbersAddToSum(numbers, 2020));

    }
}