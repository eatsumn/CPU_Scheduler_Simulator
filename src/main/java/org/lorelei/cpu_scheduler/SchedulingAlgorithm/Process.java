package org.lorelei.cpu_scheduler.SchedulingAlgorithm;

public class Process {
    int processNumber;
    float arrivalTime;
    float burstTime;
    float startTime;
    float completeTime;

    public Process(int n,float at,float bt){
        this.processNumber = n;
        this.arrivalTime = at;
        this.burstTime = bt;
    }

    public float getArrivalTime() {
        return arrivalTime;
    }

    public float getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(float burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "\nProcess #" + processNumber +
                " |AT: " + arrivalTime +
                " |BT: " + burstTime;
    }
}
