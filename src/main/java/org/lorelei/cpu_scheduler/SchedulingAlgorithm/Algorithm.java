package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

public class Algorithm {
    GanttChart ganttChart = new GanttChart();
    double averageWaitingTime;
    double averageTurnaroundTime;

    public GanttChart getGanttChart() {
        return ganttChart;
    }

    public double getAverageWaitingTime() { return averageWaitingTime; }
    public double getAverageTurnaroundTime() { return averageTurnaroundTime; }
}
