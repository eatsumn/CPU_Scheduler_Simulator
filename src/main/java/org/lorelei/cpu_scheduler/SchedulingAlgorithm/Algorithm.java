package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    GanttChart ganttChart = new GanttChart();
    double averageWaitingTime;
    double averageTurnaroundTime;
    double totalWaitingTime;
    double totalTurnaroundTime;
    ArrayList<Process> results = new ArrayList<>();


    public GanttChart getGanttChart() {
        return ganttChart;
    }
    public List<Process> getResults() { return results; }

    public void setResults(ArrayList<Process> results) {
        this.results = results;
    }

    public double getAverageWaitingTime() { return averageWaitingTime; }
    public double getAverageTurnaroundTime() { return averageTurnaroundTime; }

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public double getTotalTurnaroundTime() {
        return totalTurnaroundTime;
    }
}
