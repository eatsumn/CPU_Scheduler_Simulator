package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ProcessList{

    public static void highestAT(ArrayList<Process> inputList){
        Iterator<Process> checkList = inputList.iterator();
        float highestAT = 0;
        int longestAT = 0;
        while (checkList.hasNext()){
            Process current = checkList.next();
            if(current.arrivalTime >= highestAT) {
                highestAT = current.arrivalTime;
                longestAT = current.processNumber;
            }
        }
        System.out.println("highest is: " + highestAT + " at index:" + longestAT);
    }

    public static int lowestAT(ArrayList<Process> inputList) {
        //returns the index with the smallest AT
        ListIterator<Process> checkList = inputList.listIterator();
        float lowestAT = -1;
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
}
