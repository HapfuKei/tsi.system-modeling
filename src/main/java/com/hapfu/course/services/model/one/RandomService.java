package com.hapfu.course.services.model.one;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.SplittableRandom;
import java.util.stream.IntStream;

@Service
public class RandomService {

    private static final SplittableRandom random = new SplittableRandom(12345);
    private final MathContext mathContext = new MathContext(4); // Define the precision level

    private BigDecimal getBaseRandom() {
        return BigDecimal.valueOf(random.nextDouble());
    }

    public BigDecimal getExponential(double lambda) {
        BigDecimal u = getBaseRandom();
        if (u.compareTo(BigDecimal.ZERO) == 0) u = BigDecimal.valueOf(Double.MIN_VALUE);
        return BigDecimal.valueOf(-Math.log(u.doubleValue()))
                .divide(BigDecimal.valueOf(lambda), mathContext);
    }

    public BigDecimal getErlang(int l, double lambda) {
        return IntStream.range(0, l)
                .mapToObj(operand -> getExponential(lambda))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getNormal(double sigma, double mu) {
        BigDecimal sum = IntStream.range(0, 12)
                .mapToObj(i -> getBaseRandom())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal n = BigDecimal.valueOf(12);
        BigDecimal z = sum.subtract(n.divide(new BigDecimal(2), mathContext))
                .divide(BigDecimal.valueOf(Math.sqrt(n.doubleValue() / 12)), mathContext);

        BigDecimal result = z.multiply(BigDecimal.valueOf(sigma))
                .add(BigDecimal.valueOf(mu));
        return result.max(BigDecimal.ZERO);
    }
}