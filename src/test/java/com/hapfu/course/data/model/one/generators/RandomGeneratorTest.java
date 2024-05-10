package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomGeneratorTest {

    private static final int SAMPLE_SIZE = 500_000;
    private final RandomService randomService = new RandomService();

    @Test
    void testNormalRandomGeneratorKSTest() {
        NormalRandomGenerator generator = new NormalRandomGenerator(3, 1.1, randomService);
        NormalDistribution distribution = new NormalDistribution(3, 1.);
        double[] samples = generateSamples(generator, SAMPLE_SIZE);
        assertKolmogorovSmirnovTest(samples, distribution);
    }

    @Test
    void testExponentialRandomGeneratorKSTest() {
        ExponentialRandomGenerator generator = new ExponentialRandomGenerator(2.4, randomService);
        ExponentialDistribution distribution = new ExponentialDistribution(1 / 2.4);
        double[] samples = generateSamples(generator, SAMPLE_SIZE);
        assertKolmogorovSmirnovTest(samples, distribution);
    }

    @Test
    void testErlangRandomGeneratorKSTest() {
        ErlangRandomGenerator generator = new ErlangRandomGenerator(3, 7, randomService);
        GammaDistribution distribution = new GammaDistribution(3, 7);
        double[] samples = generateSamples(generator, SAMPLE_SIZE);
        assertKolmogorovSmirnovTest(samples, distribution);
    }

    private double[] generateSamples(RandomGenerator generator, int sampleSize) {
        double[] samples = new double[sampleSize];
        for (int i = 0; i < sampleSize; i++) {
            samples[i] = generator.generate().doubleValue();
        }
        return samples;
    }

    private void assertKolmogorovSmirnovTest(double[] samples, AbstractRealDistribution distribution) {
        KolmogorovSmirnovTest ksTest = new KolmogorovSmirnovTest();
        double pValue = ksTest.kolmogorovSmirnovTest(distribution, samples);
        assertTrue(pValue > 0.05);
    }


}