package com.hapfu.course.views.model.one;


import com.hapfu.course.data.model.one.dataset.Dataset;
import com.hapfu.course.services.model.one.DatasetService;
import com.hapfu.course.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

@PageTitle("Model1")
@Route(value = "model1", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class Model1View extends Div {

    private static final int DEFAULT_VARIANT = 5;
    private final Grid<Dataset> datasetGrid;
    private final Details datasetDetails;
    private final DatasetService datasetService;
    private final List<Dataset> datasetList;
    private Dataset selectedDataset;

    public Model1View(DatasetService datasetService) {
        this.datasetService = datasetService;
        datasetList = datasetService.findAll();
        selectedDataset = datasetList.get(DEFAULT_VARIANT);

        datasetGrid = new Grid<>(Dataset.class, false);

        datasetDetails = new Details();
        datasetDetails.setSummary(createDetailsSummary(selectedDataset.getVariantNumber()));
        datasetDetails.add(createDatasetSelectionLayout());
        datasetDetails.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        datasetDetails.setOpened(false);
        datasetDetails.addOpenedChangeListener(e -> datasetDetails.setSummary(createDetailsSummary(selectedDataset.getVariantNumber())));

        add(datasetDetails);
    }

    private Div createDatasetSelectionLayout() {
        Div layout = new Div();
        Grid<Dataset> selectionGrid = new Grid<>(Dataset.class, false);

        selectionGrid.removeAllColumns();
        selectionGrid.addColumn(Dataset::getVariantNumber).setHeader("Variant Number");
        selectionGrid.addColumn(Dataset::getI1).setHeader("I1");
        selectionGrid.addColumn(Dataset::getI2).setHeader("I2");
        selectionGrid.addColumn(Dataset::getP1).setHeader("P1");
        selectionGrid.addColumn(Dataset::getP2).setHeader("P2");
        selectionGrid.addColumn(Dataset::getQ).setHeader("Q");
        selectionGrid.addColumn(Dataset::getMoE1).setHeader("MoE1");
        selectionGrid.addColumn(Dataset::getMoE2).setHeader("MoE2");
        selectionGrid.setItems(datasetList);
        selectionGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedDataset = event.getValue();
            datasetDetails.setSummary(createDetailsSummary(selectedDataset.getVariantNumber()));
            datasetGrid.setItems(List.of(selectedDataset));
            datasetDetails.setOpened(false);
        });

        layout.add(selectionGrid);
        return layout;
    }

    private Span createDetailsSummary(int variantNumber) {
        Span summary = new Span("Selected dataset NR " + variantNumber);
        summary.getElement().getStyle().set("font-weight", "bold");
        return summary;
    }
}
