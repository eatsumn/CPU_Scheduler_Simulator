package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttCell{
    boolean isIdle;
    Double startTime;
    Double completeTime;
    Process process;
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();


    GanttCell(Double startTime, Double completeTime, Process process){
        this.startTime = startTime;
        this.completeTime = completeTime;
        this.process = process;
        this.isIdle = false;
    }

    GanttCell(Double startTime, Double completeTime){
        this.completeTime = completeTime;
        this.startTime = startTime;
        this.isIdle = true;
    }

    public void setCompleteTime(Double completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String toString() {
        if(!isIdle){
            return process + " | TAIL: " + startTime + " | HEAD: " + completeTime + "\n";
        }else{
            return "IDLE | TAIL: " + startTime + " | HEAD: " + completeTime + "\n";
        }
    }
}
