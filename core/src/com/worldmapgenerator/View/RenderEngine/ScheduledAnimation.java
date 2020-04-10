package com.worldmapgenerator.View.RenderEngine;

import com.badlogic.gdx.graphics.g2d.*;

public class ScheduledAnimation extends ScheduledEntity {

    private Animation<TextureRegion> sprite;
    private float x, y, scale, rotation, startTime;
    private boolean flipX, reverse;

    /**
     * Создает анимацию, запланированную на отрисовку.
     *
     * @param sprite - анимация, которую нужно будет отобразить
     * @param x - место отрисовки анимации по оси X
     * @param y - место отрисовки анимации по оси Y
     * @param scale - масштабирование анимации
     * @param rotation - поворот анимации в градусах
     * @param flipX - при значении true анимация отражается по горизонтали
     * @param startTime - время с начала работы (в секундах), в которое анимация добавлена в очередь
     * @param reverse - при значении true анимация проигрывается в обратном порядке кадров
     */
    ScheduledAnimation(Animation<TextureRegion> sprite, float x, float y, float scale, boolean flipX, float rotation, float startTime,
                       boolean reverse) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.flipX = flipX;
        this.rotation = rotation;
        this.startTime = startTime;
        this.reverse = reverse;
    }

    public void draw(SpriteBatch batch, PolygonSpriteBatch polygonBatch, float elapsedTime) {
        float time = elapsedTime - startTime;
        while(time < 0) {
            time += sprite.getAnimationDuration();
        }
        if(reverse) {
            time = sprite.getAnimationDuration() - time;
        }
        while(time < 0) {
            time += sprite.getAnimationDuration();
        }
        TextureAtlas.AtlasRegion currentFrame = (TextureAtlas.AtlasRegion)sprite.getKeyFrame(time, sprite.getPlayMode() == Animation.PlayMode.LOOP);
        float width = currentFrame.getRegionWidth();
        float height = currentFrame.getRegionHeight();
        batch.draw(currentFrame,  currentFrame.offsetX * scale + x + (flipX?(currentFrame.originalWidth - currentFrame.offsetX * 2) * scale:0),
                currentFrame.offsetY * scale + y,
                0, 0, width, height, flipX?-scale:scale, scale, rotation);
    }

}
