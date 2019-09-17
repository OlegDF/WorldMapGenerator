package com.worldmapgenerator.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.worldmapgenerator.Model.ControllerInterface.VoronoiMapController;
import com.worldmapgenerator.Model.ControllerInterface.GenericController;
import com.worldmapgenerator.Model.VoronoiMapModel;
import com.worldmapgenerator.View.SimplestView;

public class SimplestVoronoiController implements GenericController, SimplestController, VoronoiMapController {

    private VoronoiMapModel model;
    private SimplestView view;

    public SimplestVoronoiController() {
        model = new VoronoiMapModel();
        view = new SimplestView();
    }

    public void drawMap() {
        view.render(model.getInfo());
    }

    public void update(double tickDuration) {
        drawMap();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            model = new VoronoiMapModel();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
           view.switchPoints();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            view.switchConnections();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            view.switchPolygons();
        }
    }

    public void dispose() {
    }

}
