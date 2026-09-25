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

    public Double getBurstTime() {
        return burstTime;
    }

    public String getProcessNumberDisplay() {
        return processNumberDisplay;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "\nProcess #" + processNumber +
                " |AT: " + arrivalTime +
                " |BT: " + burstTime;
    }
}
