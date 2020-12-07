package com.gv.adventofcode2020.dec06;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.*;

public class CustomCustoms {

    private void commonQuestionAnswered(List<String> dataList) {
        int commonQuestionAnswered = 0;
        List<String> groupAnswersList = parseGroupAnswers(dataList," ");
        for (String groupAnswer : groupAnswersList) {
            String[] individualAnswers = groupAnswer.split(" ");
            Map<Character,Integer> yesAnsweredQuestionsFrequency = new HashMap<>();
            for (char firstPersonYesAnsweredQuestions : individualAnswers[0].toCharArray()) {
                yesAnsweredQuestionsFrequency.put(firstPersonYesAnsweredQuestions,
                        yesAnsweredQuestionsFrequency.getOrDefault(firstPersonYesAnsweredQuestions,0)+1);
            }
            if (individualAnswers.length > 1) {
                for (int i = 1; i < individualAnswers.length; i++) {
                    for (char firstPersonYesAnsweredQuestion : individualAnswers[i].toCharArray()) {
                        yesAnsweredQuestionsFrequency.put(firstPersonYesAnsweredQuestion,
                                yesAnsweredQuestionsFrequency.getOrDefault(firstPersonYesAnsweredQuestion,0)+1);
                    }
                }
            }
            for(Character yesAnsweredQuestion:yesAnsweredQuestionsFrequency.keySet()){
                if(yesAnsweredQuestionsFrequency.get(yesAnsweredQuestion) == individualAnswers.length){
                    commonQuestionAnswered+=1;
                }
            }
        }
        System.out.println(commonQuestionAnswered);
    }

    private void uniqueQuestionsAnswered(List<String> dataList){

        int questionsUnique = 0;
        List<String> groupAnswersList = parseGroupAnswers(dataList,"");
        Set<Character> uniqueQuestionAnswered = new HashSet<>();
        for(String groupAnswer:groupAnswersList){
            for(char questionAnswered:groupAnswer.toCharArray()){
                uniqueQuestionAnswered.add(questionAnswered);
            }
            questionsUnique = questionsUnique + uniqueQuestionAnswered.size();
            uniqueQuestionAnswered = new HashSet<>();
        }
        System.out.println(questionsUnique);
    }


    private List<String> parseGroupAnswers(List<String> dataList,String concatenate) {
        List<String> groupAnswers = new ArrayList<>();
        String answer = "";
        for (String data : dataList) {
            if (data.trim().equals("")) {
                groupAnswers.add(answer.trim());
                answer = "";
            } else {
                answer = answer + concatenate + data;
            }
        }
        groupAnswers.add(answer.trim());
        return groupAnswers;
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        List<String> dataList = inputReader.readData("dec-06-input.txt");
        new CustomCustoms().uniqueQuestionsAnswered(dataList);
        new CustomCustoms().commonQuestionAnswered(dataList);
    }
}
