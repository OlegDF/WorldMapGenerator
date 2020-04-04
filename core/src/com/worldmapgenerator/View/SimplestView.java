package com.worldmapgenerator.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public class SimplestView implements GraphicalView {

    private OrthographicCamera camera;
    private final ShapeRenderer shape;
    
    private final CameraParameters cameraParameters;

    private boolean pointRenderEnabled = true;
    private boolean connectionsRenderEnabled = true;
    private boolean polygonsRenderEnabled = true;

    private final float scrollSpeed = 200;

    public SimplestView() {
        shape = new ShapeRenderer();
        cameraParameters = new CameraParameters();
    }

    /**
     * Изображает на экране элементы модели в виде сетки из вершин графа, ребер между вершинами и границ многоугольников
     *
     * @param info - данные, принятые от модели
     */
    public void render(final GenericVisualInfo info) {
        final float scale = getScale();
        createCamera(info, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), scale);
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

    private void createCamera(final GenericVisualInfo info, final int windowWidth, final int windowHeight, final float scale) {
        camera = new OrthographicCamera(windowWidth, windowHeight);
        camera.translate(scale * ((float) (info.getMapBorderRight() - info.getMapBorderLeft()) * (0.5f + cameraParameters.getOffsetX()) +
                        (float) info.getMapBorderLeft()),
                scale * ((float) (info.getMapBorderTop() - info.getMapBorderBottom()) * (0.5f + cameraParameters.getOffsetY()) +
                        (float) info.getMapBorderBottom()));
        camera.update();
    }

    private void renderPolygons(final GenericVisualInfo info, final float scale) {
        for (final Float[] polygonDescription : info.getPolygonsDescription()) {
            final int l = (polygonDescription.length / 2) * 2;
            shape.setColor(0.16f, 0.66f, 0.16f, 1);
            for (int i = 0; i < l; i += 2) {
                shape.rectLine(polygonDescription[i % l] * scale,
                        polygonDescription[(i + 1) % l] * scale,
                        polygonDescription[(i + 2) % l] * scale,
                        polygonDescription[(i + 3) % l] * scale, 3f);
            }
        }
    }

    private void renderConnections(final GenericVisualInfo info, final float scale) {
        for (final Float[] connectionDescription : info.getConnectionsDescription()) {
            shape.setColor(0, 0.33f, 0.66f, 1);
            shape.rectLine(connectionDescription[0] * scale,
                    connectionDescription[1] * scale,
                    connectionDescription[2] * scale,
                    connectionDescription[3] * scale, 3f);
        }
    }

    private void renderPoints(final GenericVisualInfo info, final float scale) {
        for (final Float[] pointDescription : info.getPointsDescription()) {
            shape.setColor(0.66f, 0, 0.33f, 1);
            shape.circle(pointDescription[0] * scale,
                    pointDescription[1] * scale,
                    5f);
        }
    }

    private void renderBorders(final GenericVisualInfo info, final float scale) {
        shape.setColor(0, 0, 0, 1);
        shape.rect(scale * (-30 + (float) info.getMapBorderLeft()),
                scale * (-30 + (float) info.getMapBorderBottom()),
                scale * 30, scale * 90);
        shape.rect(scale * (-30 + (float) info.getMapBorderLeft()),
                scale * (-30 + (float) info.getMapBorderBottom()),
                scale * 90, scale * 30);
        shape.rect(scale * (float) info.getMapBorderRight(),
                scale * (-30 + (float) info.getMapBorderBottom()),
                scale * 30, scale * 90);
        shape.rect(scale * (-30 + (float) info.getMapBorderLeft()),
                scale * (float) info.getMapBorderTop(),
                scale * 90, scale * 30);
        shape.setColor(0.16f, 0.66f, 0.16f, 1);
        shape.rectLine(scale * (float) info.getMapBorderLeft(),
                scale * (float) info.getMapBorderBottom(),
                scale * (float) info.getMapBorderLeft(),
                scale * (float) info.getMapBorderTop(), 3f);
        shape.rectLine(scale * (float) info.getMapBorderLeft(),
                scale * (float) info.getMapBorderBottom(),
                scale * (float) info.getMapBorderRight(),
                scale * (float) info.getMapBorderBottom(), 3f);
        shape.rectLine(scale * (float) info.getMapBorderRight(),
                scale * (float) info.getMapBorderBottom(),
                scale * (float) info.getMapBorderRight(),
                scale * (float) info.getMapBorderTop(), 3f);
        shape.rectLine(scale * (float) info.getMapBorderLeft(),
                scale * (float) info.getMapBorderTop(),
                scale * (float) info.getMapBorderRight(),
                scale * (float) info.getMapBorderTop(), 3f);
    }

    private float getScale() {
        return Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) * cameraParameters.getCurrentScale();
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

    public void decreaseScale() {
        cameraParameters.decreaseScale();
    }

    public void increaseScale() {
        cameraParameters.increaseScale();
    }

    public void moveLeft() {
        cameraParameters.moveLeft(Gdx.graphics.getDeltaTime() * scrollSpeed / getScale());
    }

    public void moveRight() {
        cameraParameters.moveRight(Gdx.graphics.getDeltaTime() * scrollSpeed / getScale());
    }

    public void moveDown() {
        cameraParameters.moveDown(Gdx.graphics.getDeltaTime() * scrollSpeed / getScale());
    }

    public void moveUp() {
        cameraParameters.moveUp(Gdx.graphics.getDeltaTime() * scrollSpeed / getScale());
    }

}
