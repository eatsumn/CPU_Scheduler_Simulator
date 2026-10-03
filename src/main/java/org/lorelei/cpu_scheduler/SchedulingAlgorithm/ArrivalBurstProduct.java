package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ArrivalBurstProduct extends Algorithm {
    ArrayList<Tricell> List = new ArrayList<Tricell>();

    public ArrivalBurstProduct(ArrayList<Process> inputArray){
        double time= 0.0;

        for(Process i: inputArray){
            List.add(new Tricell(i, i.burstTime * i.arrivalTime));
        }

        List.sort(Comparator.comparingDouble(Tricell::getProduct));

        processWaitList = List.stream().map(t -> t.process).collect(Collectors.toCollection(ArrayList::new));

        while(!processWaitList.isEmpty() || !processReadyList.isEmpty()){
            Process process = new Process(processWaitList.removeFirst());
            if (process.arrivalTime > time) {
                ganttChart.addCell(new GanttCell(time, process.arrivalTime)); // idle block
                time = process.arrivalTime;
            }
            Double tempStartTime = time;
            process.setStartTime(tempStartTime);
            Double tempCompleteTime = time + process.burstTime;
            process.setCompleteTime(tempCompleteTime);
            time = tempCompleteTime;
            process.setCompleteTime(tempCompleteTime);
            process.setWaitingTime(process.getTurnaroundTime() - process.burstTime);
            totalTurnaroundTime += process.getTurnaroundTime();
            totalWaitingTime += process.getWaitingTime();

            GanttCell tempGantCell = new GanttCell(process.startTime, process.startTime + process.burstTime, process);
            tempGantCell.setProcessWaitList(processWaitList);
            processCompletedList.add(process);
            tempGantCell.setProcessCompleteList(new ArrayList<Process>(processCompletedList));
            ganttChart.addCell(tempGantCell);
            ganttChart.setLastListData(processWaitList,processReadyList,processCompletedList);

        }


        averageTurnaroundTime = totalTurnaroundTime/(processCompletedList.toArray().length);
        averageWaitingTime = totalWaitingTime/(processCompletedList.toArray().length);


        ganttChart.setResult(processCompletedList);


        System.out.println("OUTPUT OF ABP ALGO: " + ganttChart.result);



    }



}


 class Tricell{
    Process process;
    Double product;


    public Tricell(Process process, Double product) {
        this.process = process;
        this.product = product;
    }

    public Process getProcess() {
        return process;
    }

    public Double getProduct() {
        return product;
    }
}
