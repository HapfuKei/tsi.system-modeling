package com.hapfu.course.services.model.one;

import com.hapfu.course.data.model.one.dataset.DatasetImpl;
import com.hapfu.course.data.model.one.dataset.DatasetPresentation;
import com.hapfu.course.data.model.one.simulation.EventType;
import com.hapfu.course.data.model.one.simulation.SimulationRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class Model1Service {


    private final DatasetService datasetService;

    public LinkedList<SimulationRecord> runSimulation(DatasetPresentation datasetPresentation,BigDecimal simulationTime) {
        DatasetImpl dataset = datasetService.mapToImpl(datasetPresentation);
        LinkedList<SimulationRecord> simulationRecordsResult = new LinkedList<>();

        BigDecimal initJ1 = dataset.getI1().generate();
        BigDecimal initJ2 = dataset.getI2().generate();
        SimulationRecord initRecord = SimulationRecord.builder()
                .event(EventType.START)
                .currentTime(BigDecimal.ZERO)
                .J1(initJ1)
                .J2(initJ2)
                .ST(BigDecimal.valueOf(501))
                .N(0)
                .build();
        simulationRecordsResult.add(initRecord);

        BigDecimal currentTime = BigDecimal.ZERO;
        BigDecimal J1NextTime = currentTime.add(initJ1);
        BigDecimal J2NextTime = currentTime.add(initJ2);
        BigDecimal endProcessingTime = simulationTime;
        Queue<EventType> q = dataset.getQ();
        boolean serverLock = false;

        while (true) {
            if (J1NextTime.min(J2NextTime).min(endProcessingTime).equals(J1NextTime)) {

                if (!serverLock) {
                    endProcessingTime = currentTime.add(dataset.getP1().generate());
                    serverLock = true;
                }
                currentTime = J1NextTime;
                J1NextTime = currentTime.add(dataset.getI1().generate());
                q.add(EventType.J1);
                simulationRecordsResult.add(createSimulationRecord(EventType.J1, currentTime, J1NextTime, endProcessingTime, serverLock, q));
            } else if (J2NextTime.min(J1NextTime).min(endProcessingTime).equals(J2NextTime)) {

                if (!serverLock) {
                    endProcessingTime = currentTime.add(dataset.getP2().generate());
                    serverLock = true;
                }

                currentTime = J2NextTime;
                J2NextTime = currentTime.add(dataset.getI2().generate());
                q.add(EventType.J2);
                simulationRecordsResult.add(createSimulationRecord(EventType.J2, currentTime, J2NextTime, endProcessingTime, serverLock, q));
            } else if (endProcessingTime.min(J1NextTime).min(J2NextTime).equals(endProcessingTime)) {
                if (J1NextTime.min(J2NextTime).min(endProcessingTime).compareTo(simulationTime) >= 0) {
                    break;
                }
                q.poll();
                endProcessingTime = simulationTime;
                serverLock = false;
                simulationRecordsResult.add(createSimulationRecord(EventType.E, currentTime, null, endProcessingTime, serverLock, q));
            }
        }

        return simulationRecordsResult;
    }

    private SimulationRecord createSimulationRecord(EventType event, BigDecimal currentTime, BigDecimal nextArrivalTime, BigDecimal processingTime, boolean serverLock, Queue<EventType> q) {
        switch (event) {
            case J1 -> {
                return SimulationRecord.builder()
                        .event(event)
                        .currentTime(currentTime)
                        .J1(nextArrivalTime)
                        .ST(processingTime)
                        .S(serverLock ? 1 : 0)
                        .N(q.size())
                        .Q(q.toString())
                        .build();
            }
            case J2 -> {
                return SimulationRecord.builder()
                        .event(event)
                        .currentTime(currentTime)
                        .J2(nextArrivalTime)
                        .ST(processingTime)
                        .S(serverLock ? 1 : 0)
                        .N(q.size())
                        .Q(q.toString())
                        .build();
            }
            case E -> {
                return SimulationRecord.builder()
                        .event(event)
                        .currentTime(currentTime)
                        .ST(processingTime)
                        .S(serverLock ? 1 : 0)
                        .N(q.size())
                        .Q(q.toString())
                        .build();
            }
            default -> throw new IllegalStateException("Unexpected value: " + event);
        }
    }


}
