package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

import java.util.ArrayList;

public class GanttCell{
    boolean isIdle;
    Double startTime;
    Double completeTime;
    Process process;
    int index_ID;
    ArrayList<Process> processWaitList = new ArrayList<Process>();
    ArrayList<Process> processReadyList = new ArrayList<Process>();
    ArrayList<Process> processCompleteList = new ArrayList<Process>();

    GanttCell(Double startTime, Double completeTime, Process process) {
        this.startTime = startTime;
        this.completeTime = completeTime;
        this.process = process;
        this.isIdle = false;
    }

    GanttCell(Double startTime, Double completeTime) {
        this.completeTime = completeTime;
        this.startTime = startTime;
        this.isIdle = true;
    }

    public boolean isIdle() { return isIdle; }
    public Double getStartTime() { return startTime; }
    public Double getCompleteTime() { return completeTime; }
    public Process getProcess() { return process; }


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

    public int getIndex_ID() {
        return index_ID;
    }

    public void setIndex_ID(int index_ID) {
        this.index_ID = index_ID;
    }

    @Override
    public String toString() {
        String holder = "\n";
        if (!isIdle) {
            holder += process;
        } else {
            holder += "IDLE";
        }
        holder += " | TAIL: " + startTime + " | HEAD: " + completeTime + " index_caller: " + index_ID + "\n";
        return holder;
    }
}
