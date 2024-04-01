package com.hapfu.course.services.model.one;


import com.hapfu.course.data.model.one.dataset.Dataset;
import com.hapfu.course.data.model.one.dataset.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final DatasetRepository repository;

    public List<Dataset> findAll() {
        return repository.findAll();
    }
}

