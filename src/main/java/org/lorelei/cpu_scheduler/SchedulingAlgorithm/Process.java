package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

public class Process {
    int processNumber;
    String processNumberDisplay;
    Double arrivalTime;
    Double burstTime;
    Double startTime;
    Double completeTime;

    public Process(int n,Double at,Double bt){
        this.processNumber = n;
        this.arrivalTime = at;
        this.burstTime = bt;
        this.processNumberDisplay ="Process #"+n;
    }

    public Double getArrivalTime() {
        return arrivalTime;
    }

    public Double getBurstTime() { return burstTime; }
    public int getProcessNumber() { return processNumber; }
    public Double getWaitingTime() { return startTime == null ? 0 : startTime - arrivalTime; }
    public Double getTurnaroundTime() { return completeTime == null ? 0 : completeTime - arrivalTime; }

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
