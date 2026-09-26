package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class RoundRobin implements algorithm {
    ArrayList<Process> inputProcessList = new ArrayList<Process>();
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();
    ArrayList<Process> processCompletedList = new ArrayList<Process>();
    private double averageWaitingTime;
    private double averageTurnaroundTime;
    GanttChart ganttChart = new GanttChart();
    float completeTime;

    public RoundRobin(ArrayList<Process> inputArray, double timeQuantum){
        this.inputProcessList = inputArray;
        this.completeTime = ProcessList.burstTimeTotal(inputArray);
        this.processWaitList = new ArrayList<Process>(inputProcessList);
        Double currentTime = 0.0;
        int temp = 0;

        while(!processReadyList.isEmpty()||!processWaitList.isEmpty()){
            ArrayList<Process> tempSelected = new ArrayList<Process>(checkUnderTime(processWaitList, currentTime));
            processReadyList.addAll(tempSelected);
            processWaitList.removeAll(tempSelected);

            if(processReadyList.isEmpty()){
                Double nextTime = processWaitList.get(ProcessList.lowestAT(processWaitList)).arrivalTime;
                ganttChart.addCell(new GanttCell(currentTime, nextTime));
                currentTime = nextTime;
                continue;
            }

            Process selected = processReadyList.getFirst();

            Double computedExecutionTime = Math.min(timeQuantum, selected.burstTime);
            selected.setBurstTime(selected.burstTime - computedExecutionTime);
            Process tempProcess = new Process(selected.processNumber, selected.arrivalTime, selected.burstTime);
            tempProcess.startTime = currentTime;
            tempProcess.completeTime = currentTime + computedExecutionTime;
            GanttCell tempGanttCell = new GanttCell(currentTime, currentTime + computedExecutionTime, tempProcess);
            ganttChart.addCell(tempGanttCell);

            currentTime += computedExecutionTime;

            tempSelected = new ArrayList<Process>(checkUnderTime(processWaitList, currentTime));

            processReadyList.addAll(tempSelected);
            if(selected.burstTime > 0) processReadyList.add(processReadyList.getFirst());
            if(selected.burstTime == 0) processCompletedList.add(tempProcess);
            processReadyList.removeFirst();
            processWaitList.removeAll(tempSelected);


            ganttChart.setLastListData(processWaitList,processReadyList, processCompletedList);


            temp++;
        }
        System.out.println("\nFINAL: " +ganttChart);
        System.out.println("\nCompleted List: " + ganttChart.getLastCell().getProcessCompleteList());
    }



    ArrayList<Process> checkUnderTime(ArrayList<Process> inputArray, Double currentTime) {
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


    @Override
    public GanttChart getGanttChart() {
        return ganttChart;
    }
}
