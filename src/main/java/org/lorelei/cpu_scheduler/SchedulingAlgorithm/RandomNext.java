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
            processReadyList.add(processWaitList.remove(random.nextInt( 0,processWaitList.size())));
            processReadyList.getFirst().setStartTime(currentTime);
            System.out.println("CURRENT TIME:" + currentTime);
            currentTime +=  processReadyList.getFirst().getBurstTime();
            System.out.println("TAIL TIME:" + currentTime);

            processReadyList.getFirst().setCompleteTime(currentTime);
            processReadyList.getFirst().setWaitingTime(processReadyList.getFirst().startTime - processReadyList.getFirst().arrivalTime);
            totalWaitingTime += processReadyList.getFirst().waitingTime;
            totalTurnaroundTime += processReadyList.getFirst().getTurnaroundTime();
            ganttChart.addCell(new GanttCell(processReadyList.getFirst().startTime, processReadyList.getFirst().completeTime, processReadyList.getFirst()));
            processCompletedList.add(processReadyList.removeLast());
        }
        System.out.println("RANDOMJOBNEXT GANNT CHART DETAILS -------------" + ganttChart);
        ganttChart.setResult(processCompletedList);
    }
}
