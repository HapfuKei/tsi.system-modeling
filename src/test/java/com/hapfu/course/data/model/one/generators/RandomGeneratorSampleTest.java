package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

class RandomGeneratorSampleTest {

    private static final int SAMPLE_SIZE = 500_000;

    RandomService randomService = new RandomService();

    @Test
    public void generateSamples() {
        generateAndSaveData("data.csv");
    }

    public void generateAndSaveData(String fileName) {
        RandomGenerator normalGenerator = new NormalRandomGenerator(3, 1.1, randomService);
        RandomGenerator exponentialGenerator = new ExponentialRandomGenerator(2.4, randomService);
        RandomGenerator erlangGenerator = new ErlangRandomGenerator(3, 7, randomService);
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Normal,Exponential,Erlang\n");  // Header for CSV file
            for (int i = 0; i < SAMPLE_SIZE; i++) {
                double normalSample = normalGenerator.generate().doubleValue();
                double exponentialSample = exponentialGenerator.generate().doubleValue();
                double erlangSample = erlangGenerator.generate().doubleValue();
                writer.append(String.format("%f,%f,%f\n", normalSample, exponentialSample, erlangSample));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}