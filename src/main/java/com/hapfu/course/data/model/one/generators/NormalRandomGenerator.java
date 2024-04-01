package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;

public class NormalRandomGenerator implements RandomGenerator {
    private final int l;
    private final double sigma;
    private final double mu;
    private final RandomService randomService;

    public NormalRandomGenerator(int l, double sigma, double mu, RandomService randomService) {
        this.l = l;
        this.sigma = sigma;
        this.mu = mu;
        this.randomService = randomService;
    }

    @Override
    public double generate() {
        return randomService.getNormal(l, sigma, mu);
    }

}
