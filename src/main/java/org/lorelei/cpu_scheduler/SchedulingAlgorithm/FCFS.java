package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** First Come First Serve scheduling; ties retain input order. */
public class FCFS implements algorithm {
    private final GanttChart ganttChart = new GanttChart();
    private final List<Process> results = new ArrayList<>();
    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public FCFS(List<Process> input) {
        List<Process> ordered = new ArrayList<>(input);
        ordered.sort(Comparator.comparingDouble(Process::getArrivalTime));
        double time = 0, totalWaiting = 0, totalTurnaround = 0;
        for (Process source : ordered) {
            Process process = new Process(source.processNumber, source.getArrivalTime(), source.getBurstTime());
            if (time < process.getArrivalTime()) {
                ganttChart.addCell(new GanttCell(time, process.getArrivalTime()));
                time = process.getArrivalTime();
            }
            process.startTime = time;
            time += process.getBurstTime();
            process.completeTime = time;
            totalWaiting += process.getWaitingTime();
            totalTurnaround += process.getTurnaroundTime();
            results.add(process);
            ganttChart.addCell(new GanttCell(process.startTime, process.completeTime, process));
        }
        if (!results.isEmpty()) {
            averageWaitingTime = totalWaiting / results.size();
            averageTurnaroundTime = totalTurnaround / results.size();
        }
    }
    @Override public GanttChart getGanttChart() { return ganttChart; }
    public List<Process> getResults() { return results; }
    public double getAverageWaitingTime() { return averageWaitingTime; }
    public double getAverageTurnaroundTime() { return averageTurnaroundTime; }
}
