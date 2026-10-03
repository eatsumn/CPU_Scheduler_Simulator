package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** First Come First Serve scheduling; ties retain input order. */
public class FirstComeFirstServed extends Algorithm {


    public FirstComeFirstServed(List<Process> input) {
        ArrayList<Process> ordered = new ArrayList<>(input);
        ordered.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double time = 0;
        totalWaitingTime = 0;
        totalTurnaroundTime = 0;
        processWaitList = new ArrayList<Process>(ordered);
        for (Process source : ordered) {
            Process process = new Process(source.processNumber, source.getArrivalTime(), source.getBurstTime());
            if (time < process.getArrivalTime()) {
                ganttChart.addCell(new GanttCell(time, process.getArrivalTime()));
                ganttChart.setLastListData(processWaitList,processReadyList,processCompletedList);
                time = process.getArrivalTime();
            }
            process.startTime = time;
            time += process.getBurstTime();
            process.completeTime = time;
            process.setWaitingTime(process.startTime == null ? 0 : process.startTime - process.arrivalTime);
            totalWaitingTime +=  process.waitingTime;
            totalTurnaroundTime += process.getTurnaroundTime();
            results.add(process);
            ganttChart.addCell(new GanttCell(process.startTime, process.completeTime, process));
            processReadyList.removeAll(processReadyList);
            processReadyList.add(process);
            processCompletedList.addAll(processReadyList);
            processWaitList.removeFirst();

            ganttChart.setLastListData(processWaitList,processReadyList,processCompletedList);

        }
        if (!results.isEmpty()) {
            averageWaitingTime = totalWaitingTime / results.size();
            averageTurnaroundTime = totalTurnaroundTime / results.size();
        }
        ganttChart.setResult(results);
    }

}
