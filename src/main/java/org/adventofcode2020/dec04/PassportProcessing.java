package org.adventofcode2020.dec04;

import org.adventofcode2020.utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PassportProcessing {

    private void solution(final List<String> dataList) {
        List<String> passports = getPassports(dataList);
        int valid = 0;
        for(final String pa:passports){
            String item = pa;
            String[] entries = item.split(" ");
            List<String> keysList = new ArrayList<>();
            for(int i=0;i<entries.length;i++){
                String entry = entries[i];
                String[] pair =entry.split(":");
                String key = pair[0];
                String value = pair[1];
                if(Arrays.asList("ecl","pid","eyr","hcl","byr","iyr","hgt").contains(key)){
                    switch(key){
                        case "byr":
                            if(validateWithLimits( value, "\\d{4}",1920,2002)){
                                keysList.add(key);
                            }
                            break;
                        case "iyr":
                            if(validateWithLimits( value, "\\d{4}",2010,2020)){
                                keysList.add(key);
                            }
                            break;
                        case "eyr":
                            if(validateWithLimits( value, "\\d{4}",2020,2030)){
                                keysList.add(key);
                            }
                            break;
                        case "hgt":
                            String height = value.substring(0,value.length()-2);
                            if(value.endsWith("cm")){
                                if(validateWithLimits( height, "\\d{3}",150,193)){
                                    keysList.add(key);
                                }
                            }else if(value.endsWith("in")){
                                if(validateWithLimits( height, "\\d{2}",59,76)){
                                    keysList.add(key);
                                }
                            }
                            break;
                        case "hcl":
                            if(value.startsWith("#")){
                                String trVal = value.substring(1);
                                if(trVal.length()==6){
                                    if(Pattern.matches("^[a-f0-9]*$", trVal)){
                                        keysList.add(key);
                                    }
                                }
                            }
                            break;
                        case "ecl":
                            if(Arrays.asList("amb","blu","brn","gry","grn","hzl","oth").contains(value)){
                                keysList.add(key);
                            }
                            break;
                        case "pid":
                            if(Pattern.matches("\\d{9}", value)){
                                keysList.add(key);
                            }
                            break;
                    }
                }
            }
            if(keysList.size() == 7) valid++;
        }
        System.out.println(valid);
    }

    private boolean validateWithLimits(String value, String regex,Integer low,Integer high){
        if(Pattern.matches(regex, value)){
            if(Integer.parseInt(value)>=low && Integer.parseInt(value)<=high){
               return true;
            }
        }
        return false;
    }


    private List<String> getPassports(List<String> dataList) {
        List<String> passports = new ArrayList<>();
        String pass = "";
        for (String data : dataList) {

            if (data.trim().equals("")) {
                passports.add(pass.trim());
                pass = "";
            } else {
                pass = pass + " " + data;
            }
        }
        passports.add(pass.trim());
        return passports;
    }


    public static void main(String[] args) {
        final PassportProcessing passportProcessing = new PassportProcessing();
        final InputReader inputReader = new InputReader();
        final List<String> dataList = inputReader.readData("dec-04-input.txt");
        passportProcessing.solution(dataList);
    }
}
