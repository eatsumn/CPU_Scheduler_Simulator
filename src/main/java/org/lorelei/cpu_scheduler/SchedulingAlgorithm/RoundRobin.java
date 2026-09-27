package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class RoundRobin extends Algorithm {
    ArrayList<Process> inputProcessList = new ArrayList<Process>();
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();
    ArrayList<Process> processCompletedList = new ArrayList<Process>();


    float completeTime;

    public RoundRobin(ArrayList<Process> inputArray, double timeQuantum){
        this.inputProcessList = new ArrayList<Process>(inputArray);
        this.completeTime = ProcessList.burstTimeTotal(inputArray);
        this.processWaitList = new ArrayList<Process>(inputProcessList);
        ArrayList<Process> inputArrayListInitial = new ArrayList<Process>();
        HashMap<Integer, Double> startTimeInitialList = new HashMap<>();

        for(Process i: inputProcessList){
            inputArrayListInitial.add(new Process(i));
        }

        Double currentTime = 0.0;
        int temp = 0;

        System.out.println("INPUT ARRAY: " + inputArrayListInitial);

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
            Double startTimeHold = currentTime;
            tempProcess.completeTime = currentTime + computedExecutionTime;
            Double completeTimeHold = currentTime + computedExecutionTime;
            GanttCell tempGanttCell = new GanttCell(currentTime, currentTime + computedExecutionTime, tempProcess);
            ganttChart.addCell(tempGanttCell);

            startTimeInitialList.putIfAbsent(
                    processReadyList.getFirst().processNumber,
                    currentTime
            );

            currentTime += computedExecutionTime;

            tempSelected = new ArrayList<Process>(checkUnderTime(processWaitList, currentTime));
            processReadyList.addAll(tempSelected);


            if(selected.burstTime > 0) processReadyList.add(processReadyList.getFirst());
            if(selected.burstTime == 0){
                for(Process i: inputArrayListInitial){
                    if(i.processNumber==selected.processNumber) {
                        Process placeHolderProcess = new Process(tempProcess.processNumber, tempProcess.arrivalTime, i.burstTime);
                        placeHolderProcess.startTime = startTimeInitialList.get(i.processNumber);
                        System.out.println("HASHHHH MMAAAAAAAAAAAAPPPPPPPPPP" + startTimeInitialList);
                        placeHolderProcess.completeTime = completeTimeHold;
                        placeHolderProcess.waitingTime = placeHolderProcess.getTurnaroundTime() - placeHolderProcess.getBurstTime();
                        processCompletedList.add(placeHolderProcess);
                        System.out.println("LETCHE");
                    }
                }

            }
            processReadyList.removeFirst();
            processWaitList.removeAll(tempSelected);


            ganttChart.setLastListData(processWaitList,processReadyList, processCompletedList);



            temp++;
        }


        ganttChart.setResult(processCompletedList);


        totalWaitingTime = 0;
        totalTurnaroundTime = 0;

        for(Process i: processCompletedList){
            totalWaitingTime += i.getWaitingTime();
            totalTurnaroundTime += i.getTurnaroundTime();
        }

        averageTurnaroundTime = totalTurnaroundTime/(processCompletedList.toArray().length);
        averageWaitingTime = totalWaitingTime/(processCompletedList.toArray().length);

        System.out.println("\nFINAL: " +ganttChart);
        System.out.println("\nCompleted List: " + ganttChart.getLastCell().getProcessCompleteList());

        System.out.println("INPUT ARRAY: " + inputArrayListInitial);

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




}
