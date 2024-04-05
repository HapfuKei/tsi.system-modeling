package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;

import java.math.BigDecimal;

public class ExponentialRandomGenerator implements RandomGenerator {

    private final double lambda;
    private final RandomService randomService;

    public ExponentialRandomGenerator(double lambda, RandomService randomService) {
        this.lambda = lambda;
        this.randomService = randomService;
    }

    @Override
    public BigDecimal generate() {
        return randomService.getExponential(lambda);
    }

}
