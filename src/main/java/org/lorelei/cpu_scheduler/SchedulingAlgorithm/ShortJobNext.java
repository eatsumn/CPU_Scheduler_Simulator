package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Non-preemptive Shortest Job Next scheduling. */
public class ShortJobNext extends Algorithm{

    public ShortJobNext(List<Process> input) {
        ArrayList<Process> waiting = new ArrayList<>(input);
        double currentTime = 0.0;

        while (!waiting.isEmpty()) {
            double decisionTime = currentTime;
            Process selected = waiting.stream()
                    .filter(process -> process.getArrivalTime() <= decisionTime)
                    .min(Comparator.comparingDouble(Process::getBurstTime)
                            .thenComparingDouble(Process::getArrivalTime)
                            .thenComparingInt(Process::getProcessNumber))
                    .orElse(null);

            if (selected == null) {
                double nextArrival = waiting.stream()
                        .mapToDouble(Process::getArrivalTime)
                        .min()
                        .orElseThrow();
                ganttChart.addCell(new GanttCell(currentTime, nextArrival));
                currentTime = nextArrival;
                continue;
            }

            waiting.remove(selected);
            Process result = new Process(selected.processNumber,
                    selected.getArrivalTime(), selected.getBurstTime());
            result.setStartTime(currentTime);
            result.setCompleteTime(currentTime + result.getBurstTime());
            result.setWaitingTime(result.getStartTime() - result.getArrivalTime());
            currentTime = result.getCompleteTime();

            results.add(result);
            ganttChart.addCell(new GanttCell(result.getStartTime(), result.getCompleteTime(), result));
            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
        }

        if (!results.isEmpty()) {
            averageWaitingTime = totalWaitingTime / results.size();
            averageTurnaroundTime = totalTurnaroundTime / results.size();
        }
        ganttChart.setResult(results);
    }
}
