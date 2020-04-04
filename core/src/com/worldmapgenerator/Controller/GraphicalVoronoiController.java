package com.worldmapgenerator.Controller;

import com.worldmapgenerator.Input.CommandType;
import com.worldmapgenerator.Input.SimplestKeyboardInput;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;
import com.worldmapgenerator.Model.ControllerInterface.VoronoiMapController;
import com.worldmapgenerator.Model.VoronoiMapModel;
import com.worldmapgenerator.View.GraphicalView;
import com.worldmapgenerator.View.SimplestView;

public class GraphicalVoronoiController implements GenericController, GraphicalController, VoronoiMapController {

    private VoronoiMapModel model;
    private final GraphicalView view;
    private final SimplestKeyboardInput input;

    private GraphicalVoronoiController(final GraphicalView view) {
        model = new VoronoiMapModel();
        this.view = view;
        input = new SimplestKeyboardInput();
    }

    public static GraphicalVoronoiController simplestVoronoiController() {
        return new GraphicalVoronoiController(new SimplestView());
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
            case MOVE_LEFT:
                view.moveLeft();
                break;
            case MOVE_RIGHT:
                view.moveRight();
                break;
            case MOVE_DOWN:
                view.moveDown();
                break;
            case MOVE_UP:
                view.moveUp();
                break;
            case SCALE_DOWN:
                view.decreaseScale();
                break;
            case SCALE_UP:
                view.increaseScale();
                break;
        }
    }

}
