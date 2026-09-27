package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

public class Process {
    int processNumber;
    String processNumberDisplay;
    Double arrivalTime;
    Double burstTime;
    Double startTime;
    Double completeTime;
    Double waitingTime;

    public Process(int n,Double at,Double bt){
        this.processNumber = n;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.processNumberDisplay ="Process #"+n;
    }

    public Process(Double startTime,Double completeTime){
        this.arrivalTime = startTime;
        this.burstTime = completeTime;
        this.processNumberDisplay ="idle";
    }

    public Process(Process other) {
        this.processNumber = other.processNumber;
        this.arrivalTime = other.arrivalTime;
        this.burstTime = other.burstTime;
    }

    public Double getArrivalTime() {
        return arrivalTime;
    }

    public void setCompleteTime(Double completeTime) {
        this.completeTime = completeTime;
    }

    public Double getCompleteTime() {
        return completeTime;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setWaitingTime(Double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Double getBurstTime() { return burstTime; }
    public int getProcessNumber() { return processNumber; }
    public Double getWaitingTime() { return waitingTime; }
    public Double getTurnaroundTime() { return completeTime == null ? 0 : completeTime - arrivalTime; }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public String getProcessNumberDisplay() {
        return processNumberDisplay;
    }

    public void setArrivalTime(Double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessNumberDisplay(String processNumberDisplay) {
        this.processNumberDisplay = processNumberDisplay;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "Process #" + processNumber +
                " |AT: " + arrivalTime +
                " |BT: " + burstTime +
                " |ST: " + startTime +
                " |CT: " + completeTime+
                " | WT: " + getWaitingTime() +
                " | TAT: " + getTurnaroundTime()
                + " [------] ";
    }
}
