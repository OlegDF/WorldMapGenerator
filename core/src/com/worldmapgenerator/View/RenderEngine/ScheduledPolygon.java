package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.EarClippingTriangulator;

public class ScheduledPolygon extends ScheduledEntity {

    private PolygonSprite sprite;
    private float scale;

    /**
     * Создает многоугольник, запланированный на отрисовку.
     *
     * @param texture - текстура, которой должен быть заполнен многоугольник
     * @param points - координаты вершин многоугольника (для n вершин в массиве 2n чисел; points[2*i] - x i-той точки,
     *               points[2*i+1] - y i-той точки
     * @param scale - масштабирование спрайта
     */
    ScheduledPolygon(Texture texture, float[] points, float scale) {
        this.sprite = new PolygonSprite(
                new PolygonRegion(
                        new TextureRegion(texture),
                        points,
                        new EarClippingTriangulator().computeTriangles(points).toArray()
                )
        );
        this.scale = scale;
    }

    public void draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch, float elapsedTime) {
        sprite.setOrigin(0, 0);
        sprite.setScale(scale);
        sprite.draw(polygonBatch);
    }

}
