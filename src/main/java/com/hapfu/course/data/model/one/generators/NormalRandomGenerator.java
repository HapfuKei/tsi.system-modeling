package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;

import java.math.BigDecimal;

public class NormalRandomGenerator implements RandomGenerator {
    private final double sigma;
    private final double mu;
    private final RandomService randomService;

    public NormalRandomGenerator(double sigma, double mu, RandomService randomService) {
        this.sigma = sigma;
        this.mu = mu;
        this.randomService = randomService;
    }

    @Override
    public BigDecimal generate() {
        return randomService.getNormal(sigma, mu);
    }

}
