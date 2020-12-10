package com.gv.adventofcode2020.dec08;

import com.gv.adventofcode2020.utils.InputReader;

import java.util.List;

public class HandheldHalting {


    public static void main(String[] args) {
        final HandheldHalting handheldHalting = new HandheldHalting();
        final InputReader inputReader = new InputReader();
        final List<String> dataList = inputReader.readData("dec-08-input.txt");
        //PART -1
        handheldHalting.findCyclicFault(dataList);
        //PART - 2
        handheldHalting.faultFixing(dataList);
    }

    private void findCyclicFault(final List<String> dataList){
        Node[] nodes = buildOperationsArray(dataList);
        processor(nodes,false);
    }

    private void faultFixing(final List<String> dataList) {
        Node[] nodes = buildOperationsArray(dataList);
        for(int i=0;i<nodes.length;i++){
            for(int j =0;j<nodes.length;j++){
                nodes[j].setVisited(false);
            }
            Node flipCandidate = nodes[i];
            if(flipCandidate.getOperation().equals("nop") && !flipCandidate.isFlipped()){
                nodes[i].setFlipped(true);
                nodes[i].setOperation("jmp");
                if(processor(nodes,true)){
                    break;
                }else{
                    nodes[i].setOperation("nop");
                }
            }else if(flipCandidate.getOperation().equals("jmp") && !flipCandidate.isFlipped()){
                nodes[i].setFlipped(true);
                nodes[i].setOperation("nop");
                if(processor(nodes,true)){
                    break;
                }else{
                    nodes[i].setOperation("jmp");
                }
            }
        }
    }

    private Node[] buildOperationsArray(List<String> dataList) {
        Node[] nodes = new Node[dataList.size()];
        int index =0;
        for (String data : dataList) {
            String[] operations = data.split(" ");
            String op = operations[0];
            String freq = operations[1];
            String sign = freq.substring(0, 1);
            Integer frequency = Integer.parseInt(freq.substring(1));
            Node node = new Node(op,frequency,sign);
            nodes[index++] = node;
        }
        return nodes;
    }

    private boolean processor(Node[] nodes,boolean isGracefulEndNeeded) {
        boolean isTerminated = false;
        int index;
        int accumulator = 0;
        index =0;
        while(index<nodes.length){
            final Node current = nodes[index];
            if(current.isVisited()){
                break;
            }
            current.setVisited(true);
            int manipulationFreq = current.getFrequency();
            switch (current.operation){
                case "acc":
                    index = index+1;
                    if(current.getSign().equals("+")){
                        accumulator = accumulator+manipulationFreq;
                    }else{
                        accumulator = accumulator - manipulationFreq;
                    }
                    break;
                case "nop":
                    index = index+1;
                    break;
                case "jmp":
                    if(current.getSign().equals("+")){
                        index = index+manipulationFreq;
                    }else{
                        index = index - manipulationFreq;
                    }
                    break;
            }
        }

        if(isGracefulEndNeeded){
            //PART 2
            if(index == nodes.length){
                System.out.println(accumulator);
                isTerminated = true;
            }
        }else{
            //PART 1
            System.out.println(accumulator);
        }

        return isTerminated;
    }


    public class Node {
        boolean isVisited;
        String operation;
        Integer frequency;
        String sign;
        boolean isFlipped;

        public boolean isFlipped() {
            return isFlipped;
        }

        public void setFlipped(boolean flipped) {
            isFlipped = flipped;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public Integer getFrequency() {
            return frequency;
        }

        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public Node(String operation,
                    Integer frequency,
                    String sign) {
            this.frequency = frequency;
            this.sign = sign;
            this.operation = operation;
        }

    }


}
