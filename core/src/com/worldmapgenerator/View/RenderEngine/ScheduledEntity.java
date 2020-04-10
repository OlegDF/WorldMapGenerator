package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ScheduledEntity {

    /**
     * Отображает графический элемент на экране
     *
     * @param batch - объект libdgx, выполняющий отрисовку спрайтов
     * @param polygonBatch - объект libdgx, выполняющий отрисовку многоугольников
     * @param elapsedTime - время с начала работы программы в секундах
     */
    abstract void draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch, float elapsedTime);
}
