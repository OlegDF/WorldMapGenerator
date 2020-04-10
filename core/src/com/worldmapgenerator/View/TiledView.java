package com.worldmapgenerator.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.worldmapgenerator.Model.VisualInformation.AreaInfo;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;
import com.worldmapgenerator.View.Loader.AtlasLoader;
import com.worldmapgenerator.View.Loader.DataLoader;
import com.worldmapgenerator.View.RenderEngine.Renderer;
import com.worldmapgenerator.View.Storage.MusicStorage;
import com.worldmapgenerator.View.Storage.SpriteStorage;

public class TiledView implements GraphicalView {

    private OrthographicCamera camera;
    private final ShapeRenderer shape;
    private final Renderer renderer;
    private MusicStorage musicStorage;
    public SpriteStorage spriteStorage;

    private final CameraParameters cameraParameters;

    private final float scrollSpeed = 300;

    private float elapsedTime = 0;

    /**
     * Изображает на экране элементы модели в виде карты из многоугольников, заполненных различными текстурами (трава,
     * горы и т. д.)
     */
    public TiledView() {
        renderer = new Renderer(new SpriteBatch(), new PolygonSpriteBatch());
        shape = new ShapeRenderer();
        musicStorage = new MusicStorage();
        spriteStorage = new SpriteStorage();
        DataLoader loader = new AtlasLoader();
        loader.load(renderer, spriteStorage, musicStorage);
        cameraParameters = new CameraParameters();
    }

    public void render(final GenericVisualInfo info) {
        final float scale = getScale();
        createCamera(info, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), scale);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderPolygons(info, scale);
        renderer.setCamera(camera.combined);
        renderer.render(elapsedTime);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        renderPolygonBorders(info, scale);
        renderConnections(info, scale);
        renderPoints(info, scale);
        renderBorders(info, scale);
        shape.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        elapsedTime += Gdx.graphics.getDeltaTime();
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
        for (final AreaInfo areaDescription : info.getAreasDescription()) {
            final int l = (areaDescription.polygon.length / 2) * 2;
            float[] points = new float[l + 2];
            for (int i = 0; i < l + 2; i += 2) {
                points[i] = areaDescription.polygon[i % l] * scale / cameraParameters.getCurrentScale();
                points[i + 1] = areaDescription.polygon[(i + 1) % l] * scale / cameraParameters.getCurrentScale();
            }
            renderer.addPolygonToSchedule(spriteStorage.terrainTextures[areaDescription.terrainType], points, cameraParameters.getCurrentScale());
        }
    }

    private void renderPolygonBorders(final GenericVisualInfo info, final float scale) {
        for (final AreaInfo areaDescription : info.getAreasDescription()) {
            final int l = (areaDescription.polygon.length / 2) * 2;
            shape.setColor(0f, 0f, 0f, 0.5f);
            for (int i = 0; i < l + 2; i += 2) {
                shape.rectLine(areaDescription.polygon[i % l] * scale,
                        areaDescription.polygon[(i + 1) % l] * scale,
                        areaDescription.polygon[(i + 2) % l] * scale,
                        areaDescription.polygon[(i + 3) % l] * scale, 3f);
            }
        }
    }

    private void renderConnections(final GenericVisualInfo info, final float scale) {
        for (final Float[] connectionDescription : info.getConnectionsDescription()) {
            shape.setColor(0, 0.11f, 0.33f, 0.5f);
            shape.rectLine(connectionDescription[0] * scale,
                    connectionDescription[1] * scale,
                    connectionDescription[2] * scale,
                    connectionDescription[3] * scale, 3f);
        }
    }

    private void renderPoints(final GenericVisualInfo info, final float scale) {
        for (final AreaInfo pointDescription : info.getAreasDescription()) {
            shape.setColor(0.8f, 0.1f, 0.4f, 1);
            shape.circle(pointDescription.point[0] * scale,
                    pointDescription.point[1] * scale,
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
        shape.setColor(0, 0, 0, 0.5f);
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
