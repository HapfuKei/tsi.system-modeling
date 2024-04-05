package com.hapfu.course.views.model.one;


import com.hapfu.course.data.model.one.dataset.DatasetPresentation;
import com.hapfu.course.data.model.one.simulation.SimulationRecord;
import com.hapfu.course.services.model.one.DatasetService;
import com.hapfu.course.services.model.one.MetricsCalculator;
import com.hapfu.course.services.model.one.Model1Service;
import com.hapfu.course.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@PageTitle("Model1")
@Route(value = "model1", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class Model1View extends Div {

    private static final int DEFAULT_VARIANT = 5;
    private static final BigDecimal SIMULATION_TIME = BigDecimal.valueOf(500);

    private final List<DatasetPresentation> datasetPresentationList;
    private final Grid<SimulationRecord> sumulationGrid;
    private final Model1Service model1Service;
    private final MetricsCalculator metricsCalculator;
    private Details datasetDetails;
    private Details runSummary;
    private DatasetPresentation selectedDatasetPresentation;


    public Model1View(DatasetService datasetService, Model1Service model1Service, MetricsCalculator metricsCalculator) {
        this.model1Service = model1Service;
        this.metricsCalculator = metricsCalculator;
        setSizeFull();

        datasetPresentationList = datasetService.findAll();
        selectedDatasetPresentation = datasetPresentationList.get(DEFAULT_VARIANT);

        sumulationGrid = new Grid<>(SimulationRecord.class, false);

        add(createDataSetSelection(), createSimulationResult());
    }

    private Details createDataSetSelection() {
        datasetDetails = new Details();
        datasetDetails.setSummary(createDetailsSummary(selectedDatasetPresentation.getVariantNumber()));
        datasetDetails.add(createDatasetSelectionLayout());
        datasetDetails.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        datasetDetails.setOpened(false);
        datasetDetails.addOpenedChangeListener(e -> datasetDetails.setSummary(createDetailsSummary(selectedDatasetPresentation.getVariantNumber())));
        return datasetDetails;
    }

    private Details createRunSummary() {
        runSummary = new Details();
        HorizontalLayout layout = new HorizontalLayout();
//        layout.add(loadFactor());

        runSummary.setSummary(new Text("Simulation summary/details"));
        runSummary.add(layout);
        runSummary.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        runSummary.setOpened(false);
        return runSummary;
    }

    private Div createDatasetSelectionLayout() {
        Div layout = new Div();
        Grid<DatasetPresentation> datasetGrid = new Grid<>(DatasetPresentation.class, false);

        datasetGrid.removeAllColumns();
        datasetGrid.addColumn(DatasetPresentation::getVariantNumber).setHeader("Variant Number");
        datasetGrid.addColumn(DatasetPresentation::getI1).setHeader("I1");
        datasetGrid.addColumn(DatasetPresentation::getI2).setHeader("I2");
        datasetGrid.addColumn(DatasetPresentation::getP1).setHeader("P1");
        datasetGrid.addColumn(DatasetPresentation::getP2).setHeader("P2");
        datasetGrid.addColumn(DatasetPresentation::getQ).setHeader("Q");
        datasetGrid.addColumn(DatasetPresentation::getMoE1).setHeader("MoE1");
        datasetGrid.addColumn(DatasetPresentation::getMoE2).setHeader("MoE2");
        datasetGrid.setItems(datasetPresentationList);
        datasetGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedDatasetPresentation = event.getValue();
            datasetDetails.setSummary(createDetailsSummary(selectedDatasetPresentation.getVariantNumber()));
            datasetDetails.setOpened(false);
            runSimulation();
        });

        layout.add(datasetGrid);
        return layout;
    }

    private Grid<SimulationRecord> createSimulationResult() {
        sumulationGrid.setSizeFull();
        sumulationGrid.removeAllColumns();
        sumulationGrid.addColumn(SimulationRecord::getEvent).setHeader("Event");
        sumulationGrid.addColumn(SimulationRecord::getCurrentTime).setHeader("Tm");
        sumulationGrid.addColumn(SimulationRecord::getJ1).setHeader("J1");
        sumulationGrid.addColumn(SimulationRecord::getJ2).setHeader("J2");
        sumulationGrid.addColumn(SimulationRecord::getST).setHeader("ST");
        sumulationGrid.addColumn(SimulationRecord::getS).setHeader("S");
        sumulationGrid.addColumn(SimulationRecord::getN).setHeader("N");
        sumulationGrid.addColumn(SimulationRecord::getQ).setHeader("Q");
        sumulationGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        runSimulation();
        return sumulationGrid;
    }


    private void runSimulation() {
        LinkedList<SimulationRecord> simulationRecords = model1Service.runSimulation(selectedDatasetPresentation, SIMULATION_TIME);
        sumulationGrid.setItems(simulationRecords);
    }

    private Span createDetailsSummary(int variantNumber) {
        Span summary = new Span("Selected dataset NR " + variantNumber);
        summary.getElement().getStyle().set("font-weight", "bold");
        return summary;
    }

    private TextField loadFactor(LinkedList<SimulationRecord> simulationRecords) {
        BigDecimal loadFactor = metricsCalculator.calculateLoadFactor(simulationRecords, SIMULATION_TIME);

        TextField textField = new TextField();
        textField.setLabel("Load factor");
        textField.setValue(String.valueOf(loadFactor));
        textField.setClearButtonVisible(false);
        textField.setReadOnly(true);
        return textField;
    }

    private TextField downtimeFactor(LinkedList<SimulationRecord> simulationRecords) {
        BigDecimal downtimeFactor = metricsCalculator.calculateDowntimeFactor(simulationRecords, SIMULATION_TIME);

        TextField textField = new TextField();
        textField.setLabel("Downtime factor");
        textField.setValue(String.valueOf(downtimeFactor));
        textField.setClearButtonVisible(false);
        textField.setReadOnly(true);
        return textField;
    }

    private TextField maxQueue(LinkedList<SimulationRecord> simulationRecords) {
        BigDecimal maxQueueLength = metricsCalculator.calculateMaxQueueLength(simulationRecords);

        TextField textField = new TextField();
        textField.setLabel("Max. of all jobs in queue");
        textField.setValue(String.valueOf(maxQueueLength));
        textField.setClearButtonVisible(false);
        textField.setReadOnly(true);
        return textField;
    }

    private TextField averageQueueLength(LinkedList<SimulationRecord> simulationRecords) {
        BigDecimal averageQueueLength = metricsCalculator.calculateAverageQueueLength(simulationRecords);

        TextField textField = new TextField();
        textField.setLabel("Average of jobs in queue");
        textField.setValue(String.valueOf(averageQueueLength));
        textField.setClearButtonVisible(false);
        textField.setReadOnly(true);
        return textField;
    }

}
