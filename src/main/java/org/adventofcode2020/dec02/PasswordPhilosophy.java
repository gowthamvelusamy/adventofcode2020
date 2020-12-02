package org.adventofcode2020.dec02;

import org.adventofcode2020.utils.InputReader;

import java.util.List;

public class PasswordPhilosophy {


    private Integer validPasswordsPerLimit(List<String> entries) {
        Integer valid = 0;
        for (final String entry : entries) {
            String[] tokens = entry.split(":");
            final String password = tokens[1].trim();
            final String constraint = tokens[0].trim();
            tokens = constraint.split(" ");
            final char criteriaChar = tokens[1].charAt(0);
            final String limits = tokens[0];
            tokens = limits.split("-");
            final Integer lower = Integer.parseInt(tokens[0]);
            final Integer higher = Integer.parseInt(tokens[1]);
            long actual = password.chars().filter(c -> c == criteriaChar).count();
            if (lower <= actual && higher >= actual) valid++;
        }
        return valid;
    }


    private Integer validPasswordsByOccurrence(List<String> entries) {
        Integer valid = 0;
        for (final String entry : entries) {
            String[] tokens = entry.split(":");
            final String password = tokens[1].trim();
            final String constraint = tokens[0].trim();
            tokens = constraint.split(" ");
            final char criteriaChar = tokens[1].charAt(0);
            final String limits = tokens[0];
            tokens = limits.split("-");
            final Integer lower = Integer.parseInt(tokens[0]);
            final Integer higher = Integer.parseInt(tokens[1]);




            if (password.length() >= lower && password.charAt(lower - 1) == criteriaChar) {
                if (password.length() >= higher  &&  password.charAt(higher - 1) != criteriaChar) {
                    valid++;
                }
            } else if (password.length() >= higher  && password.charAt(higher - 1) == criteriaChar) {
                valid++;
            }
        }
        return valid;
    }


    public static void main(String[] args) {
        List<String> input = new InputReader().readData("dec-02-input.txt");
        //System.out.println(new PasswordPhilosophy().validPasswordsPerLimit(input));
        System.out.println(new PasswordPhilosophy().validPasswordsByOccurrence(input));
    }

}
