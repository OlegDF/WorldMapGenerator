package com.worldmapgenerator.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public class SimplestView {

    private ShapeRenderer shape;

    public SimplestView() {
        shape = new ShapeRenderer();
    }

    public void render(GenericVisualInfo info) {
        final int windowWidth = Gdx.graphics.getHeight();
        final int windowHeight = Gdx.graphics.getHeight();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        renderPolygons(info, windowWidth, windowHeight);
        //renderConnections(info, windowWidth, windowHeight);
        renderPoints(info, windowWidth, windowHeight);
        shape.end();
    }

    private void renderPolygons(GenericVisualInfo info, int windowWidth, int windowHeight) {
        for(String polygonDescription : info.getPolygonsDescription()) {
            String[] decypheredDescription = polygonDescription.split(";");
            int l = (decypheredDescription.length / 2) * 2;
            shape.setColor(0.16f, 0.66f, 0.16f, 1);
            for(int i = 0; i < l; i += 2) {
                shape.rectLine(Float.parseFloat(decypheredDescription[i % l]) * windowWidth,
                        Float.parseFloat(decypheredDescription[(i + 1) % l]) * windowHeight,
                        Float.parseFloat(decypheredDescription[(i + 2) % l]) * windowWidth,
                        Float.parseFloat(decypheredDescription[(i + 3) % l]) * windowHeight, 3f);
            }
        }
    }

    private void renderConnections(GenericVisualInfo info, int windowWidth, int windowHeight) {
        for(String connectionDescription : info.getConnectionsDescription()) {
            String[] decypheredDescription = connectionDescription.split(";");
            shape.setColor(0, 0.33f, 0.66f, 1);
            shape.rectLine(Float.parseFloat(decypheredDescription[0]) * windowWidth,
                    Float.parseFloat(decypheredDescription[1]) * windowHeight,
                    Float.parseFloat(decypheredDescription[2]) * windowWidth,
                    Float.parseFloat(decypheredDescription[3]) * windowHeight, 3f);
        }
    }

    private void renderPoints(GenericVisualInfo info, int windowWidth, int windowHeight) {
        for(String pointDescription : info.getPointsDescription()) {
            String[] decypheredDescription = pointDescription.split(";");
            shape.setColor(0.66f, 0, 0.33f, 1);
            shape.circle(Float.parseFloat(decypheredDescription[0]) * windowWidth,
                    Float.parseFloat(decypheredDescription[1]) * windowHeight,
                    5f);
        }
    }

}
