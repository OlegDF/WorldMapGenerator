package com.worldmapgenerator.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public class SimplestView {

    private OrthographicCamera camera;
    private ShapeRenderer shape;

    private boolean pointRenderEnabled = true;
    private boolean connectionsRenderEnabled = true;
    private boolean polygonsRenderEnabled = true;

    public SimplestView() {
        shape = new ShapeRenderer();
    }

    /**
     * Изображает на экране элементы модели в виде сетки из вершин графа, ребер между вершинами и границ многоугольников
     * @param info - данные, принятые от модели
     */
    public void render(GenericVisualInfo info) {
        final int windowWidth = Gdx.graphics.getWidth();
        final int windowHeight = Gdx.graphics.getHeight();
        final int scale = Math.min(windowWidth, windowHeight);
        createCamera(info, windowWidth, windowHeight, scale);
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if (polygonsRenderEnabled) {
            renderPolygons(info, scale);
        }
        if (connectionsRenderEnabled) {
            renderConnections(info, scale);
        }
        if (pointRenderEnabled) {
            renderPoints(info, scale);
        }
        renderBorders(info, scale);
        shape.end();
    }

    private void createCamera(GenericVisualInfo info, int windowWidth, int windowHeight, int scale) {
        camera = new OrthographicCamera(windowWidth, windowHeight);
        camera.translate(scale / 2f * (float)(info.getMapBorderRight() - info.getMapBorderLeft()) +
                        scale * (float)info.getMapBorderLeft(),
                scale / 2f * (float)(info.getMapBorderTop() - info.getMapBorderBottom()) +
                        scale * (float)info.getMapBorderBottom());
        camera.update();
    }

    private void renderPolygons(GenericVisualInfo info, int scale) {
        for (String polygonDescription : info.getPolygonsDescription()) {
            String[] decypheredDescription = polygonDescription.split(";");
            int l = (decypheredDescription.length / 2) * 2;
            shape.setColor(0.16f, 0.66f, 0.16f, 1);
            for (int i = 0; i < l; i += 2) {
                shape.rectLine(Float.parseFloat(decypheredDescription[i % l]) * scale,
                        Float.parseFloat(decypheredDescription[(i + 1) % l]) * scale,
                        Float.parseFloat(decypheredDescription[(i + 2) % l]) * scale,
                        Float.parseFloat(decypheredDescription[(i + 3) % l]) * scale, 3f);
            }
        }
    }

    private void renderConnections(GenericVisualInfo info, int scale) {
        for (String connectionDescription : info.getConnectionsDescription()) {
            String[] decypheredDescription = connectionDescription.split(";");
            shape.setColor(0, 0.33f, 0.66f, 1);
            shape.rectLine(Float.parseFloat(decypheredDescription[0]) * scale,
                    Float.parseFloat(decypheredDescription[1]) * scale,
                    Float.parseFloat(decypheredDescription[2]) * scale,
                    Float.parseFloat(decypheredDescription[3]) * scale, 3f);
        }
    }

    private void renderPoints(GenericVisualInfo info, int scale) {
        for (String pointDescription : info.getPointsDescription()) {
            String[] decypheredDescription = pointDescription.split(";");
            shape.setColor(0.66f, 0, 0.33f, 1);
            shape.circle(Float.parseFloat(decypheredDescription[0]) * scale,
                    Float.parseFloat(decypheredDescription[1]) * scale,
                    5f);
        }
    }

    private void renderBorders(GenericVisualInfo info, int scale) {
        shape.setColor(0, 0, 0, 1);
        shape.rect(scale * (-3 + (float)info.getMapBorderLeft()),
                scale * (-3 + (float)info.getMapBorderBottom()),
                scale * 3, scale * 9);
        shape.rect(scale * (-3 + (float)info.getMapBorderLeft()),
                scale * (-3 + (float)info.getMapBorderBottom()),
                scale * 9, scale * 3);
        shape.rect(scale * (float)info.getMapBorderRight(),
                scale * (-3 + (float)info.getMapBorderBottom()),
                scale * 3, scale * 9);
        shape.rect(scale * (-3 + (float)info.getMapBorderLeft()),
                scale * (float)info.getMapBorderTop(),
                scale * 9, scale * 3);
        shape.setColor(0.16f, 0.66f, 0.16f, 1);
        shape.rectLine(scale * (float)info.getMapBorderLeft(),
                scale * (float)info.getMapBorderBottom(),
                scale * (float)info.getMapBorderLeft(),
                scale * (float)info.getMapBorderTop(), 3f);
        shape.rectLine(scale * (float)info.getMapBorderLeft(),
                scale * (float)info.getMapBorderBottom(),
                scale * (float)info.getMapBorderRight(),
                scale * (float)info.getMapBorderBottom(), 3f);
        shape.rectLine(scale * (float)info.getMapBorderRight(),
                scale * (float)info.getMapBorderBottom(),
                scale * (float)info.getMapBorderRight(),
                scale * (float)info.getMapBorderTop(), 3f);
        shape.rectLine(scale * (float)info.getMapBorderLeft(),
                scale * (float)info.getMapBorderTop(),
                scale * (float)info.getMapBorderRight(),
                scale * (float)info.getMapBorderTop(), 3f);
    }

    public void switchPoints() {
        pointRenderEnabled = !pointRenderEnabled;
    }

    public void switchConnections() {
        connectionsRenderEnabled = !connectionsRenderEnabled;
    }

    public void switchPolygons() {
        polygonsRenderEnabled = !polygonsRenderEnabled;
    }

}
