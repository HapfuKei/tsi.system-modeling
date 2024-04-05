package com.hapfu.course.data.model.one.dataset;

import com.hapfu.course.data.model.one.generators.RandomGenerator;
import com.hapfu.course.data.model.one.simulation.EventType;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
public class DatasetImpl {

    private RandomGenerator I1; //  inter-arrival time
    private RandomGenerator I2;
    private RandomGenerator P1; // job process time
    private RandomGenerator P2;
    private Queue<EventType> Q; //queue type


}
