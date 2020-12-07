package org.adventofcode2020.dec07;

import org.adventofcode2020.utils.InputReader;

import java.util.*;

public class HandyHaversacks {

    public static void main(String[] args) {
        final HandyHaversacks handyHaversacks = new HandyHaversacks();
        final InputReader inputReader = new InputReader();
        final List<String> dataList = inputReader.readData("dec-07-input.txt");
        final Map<String, Map<String, Integer>> bagRelationshipsWithFrequency = handyHaversacks.rules(dataList, "bags contain", ",", "bag", "no other bag");
        handyHaversacks.possibleHoldingBags(bagRelationshipsWithFrequency, "shiny gold");
        handyHaversacks.possibleChildBags(bagRelationshipsWithFrequency, "shiny gold");
    }

    private void possibleHoldingBags(Map<String, Map<String, Integer>> rules, String searchBag) {
        Set<String> possibleParents = new HashSet<>();
        for (String searchPathStartBag : rules.keySet()) {
            if (!searchPathStartBag.equals(searchBag)) {
                possibleParents = breadthFirstTraversal(searchPathStartBag, searchBag, rules, possibleParents);
            }
        }
        System.out.println(possibleParents.size());
    }

    private void possibleChildBags(Map<String, Map<String, Integer>> rules, String searchBag) {
        depthFirstTraversal(searchBag, rules);
    }

    private void depthFirstTraversal(final String bagName,
                                     final Map<String, Map<String, Integer>> bagRelationshipsWithFrequency) {

        int childBagCount = 0;
        int multiplier = 1;
        final Stack<String> stack = new Stack<>();
        final Map<String, Integer> multiplierMap = new HashMap<>();
        stack.push(bagName);
        while (!stack.isEmpty()) {
            final String currentBag = stack.pop();
            if (!currentBag.equals(bagName)) {
                multiplier = multiplierMap.get(currentBag);
            }
            final Map<String, Integer> childBagMap = bagRelationshipsWithFrequency.get(currentBag);
            for (final String childBag : childBagMap.keySet()) {
                childBagCount += multiplier * childBagMap.get(childBag);
                stack.push(childBag);
                multiplierMap.put(childBag, multiplier * childBagMap.get(childBag));
            }
        }
        System.out.println(childBagCount);
    }

    private Set<String> breadthFirstTraversal(final String bagName,
                                              final String searchBag,
                                              final Map<String, Map<String, Integer>> bagRelationshipsWithFrequency,
                                              final Set<String> possibleParents) {
        final Set<String> visited = new LinkedHashSet<>();
        final Queue<String> queue = new LinkedList<>();
        queue.add(bagName);
        //visited.add(bagName);
        while (!queue.isEmpty()) {
            final String currentBag = queue.poll();
            for (final String childrenBag : bagRelationshipsWithFrequency.get(currentBag).keySet()) {
                //if (!visited.contains(childrenBag)) {
                if (childrenBag.equals(searchBag)) {
                    possibleParents.add(bagName);
                    return possibleParents;
                }
                visited.add(childrenBag);
                queue.add(childrenBag);
                //}
            }
        }
        return possibleParents;
    }

    private Map<String, Map<String, Integer>> rules(final List<String> dataList,
                                                    final String relationshipToken,
                                                    final String occurrenceSeparator,
                                                    final String commonNodeQualifier,
                                                    final String leafNodeQualifier) {

        final Map<String, Map<String, Integer>> bagRelationshipsWithFrequency = new HashMap<>();

        for (final String data : dataList) {
            final String keyBag = data.substring(0, data.indexOf(relationshipToken));
            final String candidateBagsString = data.substring(data.indexOf(relationshipToken) + relationshipToken.length() + 1);
            final Map<String, Integer> candidateBags = new HashMap<>();
            if (!candidateBagsString.contains(leafNodeQualifier)) {
                final String[] candidateRules = candidateBagsString.split(occurrenceSeparator);
                for (String candidateRule : candidateRules) {
                    final Integer quantity = Integer.parseInt(candidateRule.trim()
                            .substring(0, candidateRule.trim().indexOf(" ")));
                    String bag = null;
                    if (candidateRule.contains(commonNodeQualifier + "s")) {
                        bag = candidateRule.trim()
                                .substring(candidateRule.trim().indexOf(" "), candidateRule.trim().indexOf(commonNodeQualifier + "s"));
                    } else {
                        bag = candidateRule.trim()
                                .substring(candidateRule.trim().indexOf(" "), candidateRule.trim().indexOf(commonNodeQualifier));
                    }
                    candidateBags.put(bag.trim(), quantity);
                }
            }
            bagRelationshipsWithFrequency.put(keyBag.trim(), candidateBags);
        }
        return bagRelationshipsWithFrequency;
    }
}
