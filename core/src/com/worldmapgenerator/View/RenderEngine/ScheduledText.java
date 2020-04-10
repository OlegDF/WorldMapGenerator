package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScheduledText extends ScheduledEntity {
    private BitmapFont font;
    private float x, y;
    private float r, g, b, a;
    private String text;

    /**
     * Создает текст, запланированный на отрисовку.
     *
     * @param font - bitmap-шрифт для отрисовки текста
     * @param x - место отрисовки текста по оси X
     * @param y - место отрисовки текста по оси Y
     * @param text - содержимое текста
     * @param r - значение красного цвета от 0 до 1
     * @param g - значение зеленого цвета от 0 до 1
     * @param b - значение синего цвета от 0 до 1
     * @param a - значение зеленого цвета от 0 до 1
     */
    ScheduledText(BitmapFont font, float x, float y, String text, float r, float g, float b, float a) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.text = text;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch, float elapsedTime) {
        font.setColor(r, g, b, a);
        font.draw(batch, text, x, y);
    }
}
