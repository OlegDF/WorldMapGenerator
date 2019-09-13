package com.worldmapgenerator.Controller;

import com.worldmapgenerator.Model.ControllerInterface.DelaunayMapController;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;
import com.worldmapgenerator.Model.DelaunayMapModel;
import com.worldmapgenerator.View.SimplestView;

public class SimplestDelaunayController implements GenericController, SimplestController, DelaunayMapController {

    private DelaunayMapModel model;
    private SimplestView view;

    public SimplestDelaunayController() {
        model = new DelaunayMapModel();
        view = new SimplestView();
    }

    public void drawMap() {
        view.render(model.getInfo());
    }

    public void update() {
        drawMap();
    }

    public void dispose() {}

}
