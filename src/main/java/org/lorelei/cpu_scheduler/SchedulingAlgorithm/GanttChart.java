package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttChart {
    ArrayList<GanttCell> chart = new ArrayList<GanttCell>();


    public void addCell(GanttCell cell){
        chart.add(cell);
        cell.setIndex_ID(chart.indexOf(cell)); //fuck
    }

    public void setLastListData(ArrayList<Process> waitList, ArrayList<Process> readyList, ArrayList<Process> completedList){
        chart.getLast().setProcessCompleteList(new ArrayList<Process>(completedList));
        chart.getLast().setProcessReadyList(new ArrayList<Process>(readyList));
        chart.getLast().setProcessWaitList(new ArrayList<Process>(waitList));
    }

    public GanttCell getLastCell(){
        return chart.getLast();
    }

    public GanttCell getChart(int index) {
        return chart.get(index);
    }

    public ArrayList<Process> getGanttChartProcess(){
        ArrayList<Process> outputProcesses = new ArrayList<Process>();
        for(GanttCell i: chart){
            outputProcesses.add(i.getProcess());
            System.out.println("NIGGGGAAAAA" + i.getProcess());
        }
        return outputProcesses;
    }

    public ArrayList<GanttCell> getChart() {
        return chart;
    }

    @Override
    public String toString() {
        return "GanttChart -->\n" + chart;
    }
}
