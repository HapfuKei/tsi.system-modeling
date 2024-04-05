package com.hapfu.course.services.model.one;


import com.hapfu.course.data.model.one.dataset.DatasetImpl;
import com.hapfu.course.data.model.one.dataset.DatasetPresentation;
import com.hapfu.course.data.model.one.dataset.DatasetRepository;
import com.hapfu.course.data.model.one.generators.ErlangRandomGenerator;
import com.hapfu.course.data.model.one.generators.ExponentialRandomGenerator;
import com.hapfu.course.data.model.one.generators.NormalRandomGenerator;
import com.hapfu.course.data.model.one.generators.RandomGenerator;
import com.hapfu.course.data.model.one.simulation.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository repository;
    private final RandomService randomService;

    public Queue<EventType> createQueueType(String descriptor) {
        return switch (descriptor.toUpperCase()) {
            case "FIFO" -> new LinkedList<>(); // LinkedList as Queue is FIFO
            case "LIFO" -> Collections.asLifoQueue(new LinkedList<>()); // LinkedList as Deque can be used as LIFO (Stack)
            default -> throw new IllegalArgumentException("Unsupported queue type: " + descriptor);
        };
    }

    public DatasetImpl mapToImpl(DatasetPresentation presentation) {
        DatasetImpl dataset = new DatasetImpl();

        dataset.setI1(parseRandomGenerator(presentation.getI1()));
        dataset.setI2(parseRandomGenerator(presentation.getI2()));
        dataset.setP1(parseRandomGenerator(presentation.getP1()));
        dataset.setP2(parseRandomGenerator(presentation.getP2()));

        dataset.setQ(createQueueType(presentation.getQ()));

        return dataset;
    }

    public List<DatasetPresentation> findAll() {
        return repository.findAll();
    }

    private RandomGenerator parseRandomGenerator(String descriptor) {
        List<Double> params = extractParams(descriptor);
        if (descriptor.startsWith("Exponential")) {
            if (params.size() != 1) {
                throw new IllegalArgumentException("Invalid number of parameters for Exponential distribution.");
            }
            return new ExponentialRandomGenerator(params.get(0), randomService);
        } else if (descriptor.startsWith("Erlang")) {
            if (params.size() != 2) {
                throw new IllegalArgumentException("Invalid number of parameters for Erlang distribution.");
            }
            int l = params.get(0).intValue();
            double lambda = params.get(1);
            return new ErlangRandomGenerator(l, lambda, randomService);
        } else if (descriptor.startsWith("Normal")) {
            if (params.size() != 2) {
                throw new IllegalArgumentException("Invalid number of parameters for Normal distribution.");
            }
            double sigma = params.get(0);
            double mu = params.get(1);
            return new NormalRandomGenerator(sigma, mu, randomService);
        }
        throw new IllegalArgumentException("Unknown generator type: " + descriptor);
    }

    private List<Double> extractParams(String descriptor) {
        Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(descriptor);
        if (matcher.find()) {
            return Arrays.stream(matcher.group(1).split(","))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Invalid descriptor format: " + descriptor);
    }
}

