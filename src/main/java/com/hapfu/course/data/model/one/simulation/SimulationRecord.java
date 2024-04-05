package com.hapfu.course.data.model.one.simulation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;

@Data
@Builder
public class SimulationRecord {

    EventType event;
    BigDecimal currentTime; // current time in the model
    BigDecimal J1; // the arrival time of the next job of type 1
    BigDecimal J2; // the arrival time of the next job of type 2
    BigDecimal ST; // the time, then processing of the job will be completed
    int S; //– status of the server (0 – free, 1- busy)
    int N; // the queue length
    String Q; // the queue content. J1 - job of type 1, J2 - job of type 2.

}
