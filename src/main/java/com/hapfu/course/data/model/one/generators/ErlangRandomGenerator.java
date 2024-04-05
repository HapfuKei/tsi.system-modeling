package com.hapfu.course.data.model.one.generators;

import com.hapfu.course.services.model.one.RandomService;

import java.math.BigDecimal;

public class ErlangRandomGenerator implements RandomGenerator {

    private final int l;
    private final double lambda;
    private final RandomService randomService;

    public ErlangRandomGenerator(int l, double lambda, RandomService randomService) {
        this.l = l;
        this.lambda = lambda;
        this.randomService = randomService;
    }

    @Override
    public BigDecimal generate() {
        return randomService.getErlang(l, lambda);
    }

}
