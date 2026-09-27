package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ProcessList{

    public static double highestAT(ArrayList<Process> inputList){
        Iterator<Process> checkList = inputList.iterator();
        Double highestAT = 0.0;
        int longestAT = 0;
        while (checkList.hasNext()){
            Process current = checkList.next();
            if(current.arrivalTime >= highestAT) {
                highestAT = current.arrivalTime;
                longestAT = current.processNumber;
            }
        }
        System.out.println("highest is: " + highestAT + " at index:" + longestAT);
        return highestAT;
    }

    public static double highestBT(ArrayList<Process> inputList){
        Iterator<Process> checkList = inputList.iterator();
        Double highestBT = 0.0;
        int longestBT = 0;
        while (checkList.hasNext()){
            Process current = checkList.next();
            if(current.burstTime >= highestBT) {
                highestBT = current.burstTime;
                longestBT = current.processNumber;
            }
        }
        System.out.println("highest is: " + highestBT + " at index:" + longestBT);
        return highestBT;
    }

    public static int lowestAT(ArrayList<Process> inputList) {
        //returns the index with the smallest AT
        ListIterator<Process> checkList = inputList.listIterator();
        Double lowestAT = -1.0;
        int indexOfSmallest = -1;
        while (checkList.hasNext()) {
            int currentIndex = checkList.nextIndex();
            Process current = checkList.next();


            if (lowestAT == -1) {
               lowestAT = current.arrivalTime;
               indexOfSmallest = currentIndex ;
               continue;
            }

            if(current.arrivalTime == lowestAT){
                continue;
            }

            if (current.arrivalTime < lowestAT) {
                lowestAT = current.arrivalTime;
                indexOfSmallest = currentIndex;
            }

        }
        //System.out.println("index of smallest -> " + indexOfSmallest + " which is" + ((!inputList.isEmpty()) ? inputList.get(indexOfSmallest).processNumber : "empty"));

        return (indexOfSmallest);
    }


    public static float burstTimeTotal(ArrayList<Process> inputList){
        float total = 0;

        for(Process i : inputList){
            total += i.burstTime;
        }

        return total;
    }

    public static ArrayList<Process> checkUnderTime(ArrayList<Process> inputArray, Double currentTime) {
        //list down all process under a certain at
        ArrayList<Process> output = new ArrayList<Process>();
        ArrayList<Process> unsorted = new ArrayList<Process>();
        ArrayList<Process> sorted = new ArrayList<Process>();

        for (Process current : inputArray) {
            if (current.arrivalTime <= currentTime) unsorted.add(current);
        }

        while (!unsorted.isEmpty()){
            int indexSmallest = ProcessList.lowestAT(unsorted);
            System.out.println(indexSmallest);

            sorted.add(unsorted.get(indexSmallest));
            unsorted.remove(unsorted.get(indexSmallest));
        }
        System.out.println(sorted);
        return sorted;
    }
}
