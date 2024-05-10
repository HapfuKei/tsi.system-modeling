package com.hapfu.course.services.model.one;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.SplittableRandom;

@Service
public class RandomService {

    public static final SplittableRandom RANDOM = new SplittableRandom();

    public BigDecimal getExponential(double lambda) {
        double u = RANDOM.nextDouble();
        return BigDecimal.valueOf(-Math.log(u) / lambda);
    }

    public BigDecimal getErlang(int k, double lambda) {
        BigDecimal sum = new BigDecimal(0L);
        for (int i = 0; i < k; i++) {
            sum = sum.add(getExponential(lambda));
        }
        return sum;
    }

    public BigDecimal getNormal(double mu, double sigma) {
        double sum = RANDOM.doubles(500, 0.0, 1.0).sum();
        double z = (sum - 250) / Math.sqrt((double) 500 / 12);
        return BigDecimal.valueOf(z * sigma + mu);
    }

}