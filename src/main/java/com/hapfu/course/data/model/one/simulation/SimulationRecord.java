package com.hapfu.course.data.model.one.simulation;

import lombok.Data;

import java.util.List;

@Data
public class SimulationRecord {

    String Event;
    Long CurrentTime; // current time in the model
    Long J1ArrivalTime; // the arrival time of the next job of type 1
    Long J2; // the arrival time of the next job of type 2
    Long ST; // the time, then processing of the job will be completed
    Long S; //– status of the server (0 – free, 1- busy)
    Long N; // the queue length
    List<String> Q; // the queue content. J1 - job of type 1, J2 - job of type 2.

}
