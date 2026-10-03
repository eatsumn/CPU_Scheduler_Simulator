package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class RandomNext extends Algorithm{

    public RandomNext(ArrayList<Process> inputArray){
        this.inputProcessList = new ArrayList<Process>(inputArray);
        this.processWaitList = new ArrayList<Process>(inputArray);

        double currentTime = 0.0;
        Random random = new Random();


        while(!processWaitList.isEmpty()||!processReadyList.isEmpty()){
            Process selected;
            ArrayList<Process> checked = ProcessList.checkUnderTime(processWaitList, currentTime);

            if(checked.isEmpty()){
                Double nextTime = processWaitList.get(ProcessList.lowestAT(processWaitList)).arrivalTime;
                ganttChart.addCell(new GanttCell(currentTime, nextTime));
                currentTime = nextTime;
                continue;
            }else{
                Process randomSelected = checked.get(random.nextInt(0, checked.size()));
                selected = randomSelected;
                processWaitList.remove(selected);
            }

            processReadyList.add(selected);
            selected.setStartTime(currentTime);
            currentTime += selected.getBurstTime();
            selected.setCompleteTime(currentTime);
            selected.setWaitingTime(selected.getStartTime() - selected.getArrivalTime());
            totalWaitingTime += selected.getWaitingTime();
            totalTurnaroundTime += selected.getTurnaroundTime();
            ganttChart.addCell(new GanttCell(selected.getStartTime(), selected.getCompleteTime(), selected));
            processReadyList.remove(selected);
            processCompletedList.add(selected);

            averageTurnaroundTime = totalTurnaroundTime/(processCompletedList.toArray().length);
            averageWaitingTime = totalWaitingTime/(processCompletedList.toArray().length);
            ganttChart.setLastListData(processWaitList,processReadyList,processCompletedList);

        }
        ganttChart.setResult(processCompletedList);

    }
}
