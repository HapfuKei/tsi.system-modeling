package com.hapfu.course.services.model.one;

import com.hapfu.course.data.model.one.simulation.SimulationRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

@Service
public class MetricsCalculator {

    public BigDecimal calculateLoadFactor(LinkedList<SimulationRecord> simulationRecords, BigDecimal simulationTime) {
        BigDecimal totalProcessingTime = simulationRecords.stream()
                .map(SimulationRecord::getST)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalProcessingTime.divide(simulationTime, MathContext.DECIMAL32);
    }

    public BigDecimal calculateMaxQueueLength(LinkedList<SimulationRecord> simulationRecords) {
        return simulationRecords.stream()
                .map(record -> BigDecimal.valueOf(record.getN()))
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal calculateAverageQueueLength(LinkedList<SimulationRecord> simulationRecords) {
        return simulationRecords.stream()
                .map(record -> BigDecimal.valueOf(record.getN()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(simulationRecords.size()), MathContext.DECIMAL32);
    }

    public BigDecimal calculateDowntimeFactor(LinkedList<SimulationRecord> simulationRecords, BigDecimal simulationTime) {
        BigDecimal totalDowntime = BigDecimal.ZERO;
        BigDecimal lastEventTime = BigDecimal.ZERO;

        for (SimulationRecord record : simulationRecords) {
            // Assuming isServerLocked() returns true if the server is busy.
            if (record.getS() == 0) {
                // Server just became free, calculate the downtime since the last event.
                BigDecimal downtime = record.getCurrentTime().subtract(lastEventTime);
                totalDowntime = totalDowntime.add(downtime);
            }
            lastEventTime = record.getCurrentTime();
        }

        // Handle the case where the server is free at the end of the simulation.
        if (simulationRecords.getLast().getS() == 1) {
            totalDowntime = totalDowntime.add(simulationTime.subtract(lastEventTime));
        }

        return totalDowntime.divide(simulationTime, MathContext.DECIMAL128);
    }

}
