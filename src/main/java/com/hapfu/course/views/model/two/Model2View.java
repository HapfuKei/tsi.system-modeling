package com.hapfu.course.views.model.two;

import com.hapfu.course.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Model2")
@Route(value = "model2", layout = MainLayout.class)
@Uses(Icon.class)
public class Model2View extends Composite<VerticalLayout> {

    public Model2View() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
    }
}
