package com.hapfu.course.data.model.one.dataset;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "dataset")
public class DatasetPresentation {

    @Id
    private int variantNumber;
    private String I1; //  inter-arrival time
    private String I2;
    private String P1; // job process time
    private String P2;
    private String Q; //queue type
    private String MoE1; // measures of effectiveness
    private String MoE2;

}
