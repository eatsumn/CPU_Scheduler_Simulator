package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttChart {
    float currentTime;
    float totalTime;
    ArrayList<GanttCell> chart = new ArrayList<GanttCell>();

    public void addCell(GanttCell cell){
        chart.add(cell);
    }

    public void setLastListData(ArrayList<Process> waitList, ArrayList<Process> readyList, ArrayList<Process> completedList){
        chart.getLast().setProcessCompleteList(new ArrayList<Process>(completedList));
        chart.getLast().setProcessReadyList(new ArrayList<Process>(readyList));
        chart.getLast().setProcessWaitList(new ArrayList<Process>(waitList));
    }

    public GanttCell getLastCell(){
        return chart.getLast();
    }

    public ArrayList<GanttCell> getChart() {
        return chart;
    }

    @Override
    public String toString() {
        return "GanttChart -->\n" + chart;
    }
}
