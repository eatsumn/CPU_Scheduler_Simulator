package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        ArrayList<Process> processList = new ArrayList<Process>();
        int processAmount;


        Scanner scan = new Scanner(System.in);
        System.out.print("Number of process: ");
        processAmount = scan.nextInt();

        for (int i = 0; i < processAmount; i++){
            Double tempAT;
            Double tempBT;

            System.out.printf("\nEnter arrival time for process #%d: ", i + 1);
            tempAT  = scan.nextDouble();



            System.out.printf("\nEnter burst time for process #%d: ", i + 1);
            tempBT = scan.nextDouble();

            processList.add(new Process(i+1,tempAT,tempBT));
        }

        for(Process i : processList){
            System.out.println("process #" + i.processNumber + " | at: "+  i.arrivalTime + " | bt: " + i.burstTime);
        }

        RoundRobin round = new RoundRobin(processList, 2);
    }
}
