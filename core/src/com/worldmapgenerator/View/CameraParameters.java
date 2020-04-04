package com.worldmapgenerator.View;

public class CameraParameters {

    private final float scaleStep = (float)Math.sqrt(2);

    private float currentScale = 1;
    private float offsetX = -0.5f;
    private float offsetY = -0.5f;

    public CameraParameters() {}

    public void decreaseScale() {
        currentScale /= scaleStep;
    }

    public void increaseScale() {
        currentScale *= scaleStep;
    }

    public void moveLeft(final double delta) {
        if(offsetX >= -0.5f + delta) {
            offsetX -= delta;
        }
    }

    public void moveRight(final double delta) {
        if(offsetX <= 0.5f - delta) {
            offsetX += delta;
        }
    }

    public void moveDown(final double delta) {
        if(offsetY >= -0.5f + delta) {
            offsetY -= delta;
        }
    }

    public void moveUp(final double delta) {
        if(offsetY <= 0.5f - delta) {
            offsetY += delta;
        }
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

}
