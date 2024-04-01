package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;

public class ExponentialRandomGenerator implements RandomGenerator {

    private final double lambda;
    private final RandomService randomService;

    public ExponentialRandomGenerator(double lambda, RandomService randomService) {
        this.lambda = lambda;
        this.randomService = randomService;
    }

    @Override
    public double generate() {
        return randomService.getExponential(lambda);
    }

}
