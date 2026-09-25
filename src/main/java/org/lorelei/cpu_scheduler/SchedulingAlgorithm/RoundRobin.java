package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class RoundRobin {
    ArrayList<Process> inputProcessList = new ArrayList<Process>();
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();


    float completeTime;




    public RoundRobin(ArrayList<Process> inputArray, float timeQuantum){
        GanttChart ganttChart = new GanttChart();
        this.inputProcessList = inputArray;
        this.completeTime = ProcessList.burstTimeTotal(inputArray);
        this.processWaitList = new ArrayList<Process>(inputProcessList);
        float currentTime = 0;

        int temp = 0;

        while(!processReadyList.isEmpty()||!processWaitList.isEmpty()){
            ArrayList<Process> tempSelected = new ArrayList<Process>(checkUnderTime(processWaitList, currentTime));
            processReadyList.addAll(tempSelected);
            processWaitList.removeAll(tempSelected);

            System.out.println("\niteration #" + temp);
            System.out.println("\ntemporary list | " + tempSelected);
            System.out.println("\nwait list | " + processWaitList);
            System.out.println("\nready list | " + processReadyList);



            if(processReadyList.isEmpty()){
                float nextTime = processWaitList.get(ProcessList.lowestAT(processWaitList)).arrivalTime;
                ganttChart.addCell(new GanttCell(currentTime, nextTime));
                currentTime = nextTime;
                continue;
            }

            Process selected = processReadyList.getFirst();

            float computedExecutionTime = Math.min(timeQuantum, selected.burstTime);
            selected.setBurstTime(selected.burstTime - computedExecutionTime);

            ganttChart.addCell(new GanttCell(currentTime, currentTime + computedExecutionTime, new Process(selected.processNumber, selected.arrivalTime, selected.burstTime)));

            currentTime += computedExecutionTime;

            tempSelected = new ArrayList<Process>(checkUnderTime(processWaitList, currentTime));

            processReadyList.addAll(tempSelected);
            if(selected.burstTime > 0) processReadyList.add(processReadyList.getFirst());
            processReadyList.removeFirst();
            processWaitList.removeAll(tempSelected);

            System.out.println("\npost process gannt chart | " + ganttChart);
            System.out.println("\nready list post gannt chart | " + processReadyList);

            temp++;
        }
        System.out.println("\nFINAL: " +ganttChart);



    }



    ArrayList<Process> checkUnderTime(ArrayList<Process> inputArray, float currentTime) {
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
