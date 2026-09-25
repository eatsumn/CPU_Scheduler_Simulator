package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttChart {
    float currentTime;
    float totalTime;
    ArrayList<GanttCell> chart = new ArrayList<GanttCell>();

    public void addCell(GanttCell cell){
        chart.add(cell);
    }

    @Override
    public String toString() {
        return "GanttChart -->\n" + chart;
    }
}
