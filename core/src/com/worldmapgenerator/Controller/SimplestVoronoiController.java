package com.worldmapgenerator.Controller;

import com.worldmapgenerator.Input.CommandType;
import com.worldmapgenerator.Input.SimplestKeyboardInput;
import com.worldmapgenerator.Model.ControllerInterface.VoronoiMapController;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;
import com.worldmapgenerator.Model.VoronoiMapModel;
import com.worldmapgenerator.View.SimplestView;

public class SimplestVoronoiController implements GenericController, SimplestController, VoronoiMapController {

    private VoronoiMapModel model;
    private final SimplestView view;
    private final SimplestKeyboardInput input;

    public SimplestVoronoiController() {
        model = new VoronoiMapModel();
        view = new SimplestView();
        input = new SimplestKeyboardInput();
    }

    public void drawMap() {
        view.render(model.getInfo());
    }

    public void update(final double tickDuration) {
        drawMap();
        input.listenForInput(this);
    }

    public void dispose() {
    }

    public void receiveCommand(final CommandType command) {
        switch (command) {
            case GENERATE_NEW_MAP:
                model = new VoronoiMapModel();
                break;
            case RELAX_MAP:
                model.relaxMap();
                break;
            case HIDE_POINTS:
                view.switchPoints();
                break;
            case HIDE_CONNECTIONS:
                view.switchConnections();
                break;
            case HIDE_POLYGONS:
                view.switchPolygons();
                break;
        }
    }

}
