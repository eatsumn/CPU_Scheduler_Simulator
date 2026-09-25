package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttCell{
    boolean isIdle;
    Double startTime;
    Double completeTime;
    Process process;
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();
    ArrayList<Process> processCompleteList = new ArrayList<Process>();


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

    public ArrayList<Process> getProcessWaitList() {
        return processWaitList;
    }

    public void setProcessWaitList(ArrayList<Process> processWaitList) {
        this.processWaitList = processWaitList;
    }

    public ArrayList<Process> getProcessReadyList() {
        return processReadyList;
    }

    public void setProcessReadyList(ArrayList<Process> processReadyList) {
        this.processReadyList = processReadyList;
    }

    public ArrayList<Process> getProcessCompleteList() {
        return processCompleteList;
    }

    public void setProcessCompleteList(ArrayList<Process> processCompleteList) {
        this.processCompleteList = processCompleteList;
    }

    @Override
    public String toString() {
        String holder = "||||||||||||||||||";
        if(!isIdle){
            holder += process;
        }else{
            holder += "IDLE";
        }
        holder +=  " | TAIL: " + startTime + " | HEAD: " + completeTime + "\n - - - - - Wait List: " + getProcessWaitList() + "\n - - - - - Ready List: " + getProcessReadyList() + "\n - - - - - Complete List: " + getProcessCompleteList() + "\n";
        return holder;
    }
}
