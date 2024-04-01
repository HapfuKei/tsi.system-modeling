package com.hapfu.course.services.model.one;

import org.springframework.stereotype.Service;

import java.util.SplittableRandom;
import java.util.stream.IntStream;

@Service
public class RandomService {

    private static final SplittableRandom random = new SplittableRandom();

    private static double getBaseRandom() {
        return random.nextDouble(0, 1);
    }

    public double getExponential(double lambda) {
        double u = getBaseRandom();
        if (u == 0.0) u = Double.MIN_VALUE;
        return -Math.log(u) / lambda;
    }


    public double getErlang(int l, double lambda) {
        return IntStream.range(0, l)
                .mapToDouble(operand -> getExponential(lambda))
                .sum();
    }

    public double getNormal(int l, double sigma, double mu) {
        double sum = IntStream.range(0, l)
                .mapToDouble(i -> getBaseRandom())
                .sum();

        double z = (sum - (double) l / 2) / Math.sqrt((double) l / 12);
        return z * sigma + mu;
    }

}
