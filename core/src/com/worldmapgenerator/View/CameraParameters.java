package com.worldmapgenerator.View;

public class CameraParameters {

    private final float scaleStep = 2;

    private float currentScale = 1;
    private float offsetX = -0.5f;
    private float offsetY = -0.5f;

    /**
     * Объект, содержащий и изменяющий параметры проекции камеры.
     */
    public CameraParameters() {}

    /**
     * Уменьшает масштаб изображения в определенное количество раз.
     */
    public void decreaseScale() {
        currentScale /= scaleStep;
    }

    /**
     * Увеличивает масштаб изображения в определенное количество раз.
     */
    public void increaseScale() {
        currentScale *= scaleStep;
    }

    /**
     * Смещает камеру влево.
     *
     * @param delta - величина смещения в пикселях
     */
    public void moveLeft(final double delta) {
        if(offsetX >= -0.5f + delta) {
            offsetX -= delta * currentScale;
        }
    }

    /**
     * Смещает камеру вправо.
     *
     * @param delta - величина смещения в пикселях
     */
    public void moveRight(final double delta) {
        if(offsetX <= 0.5f - delta) {
            offsetX += delta * currentScale;
        }
    }

    /**
     * Смещает камеру вниз.
     *
     * @param delta - величина смещения в пикселях
     */
    public void moveDown(final double delta) {
        if(offsetY >= -0.5f + delta) {
            offsetY -= delta * currentScale;
        }
    }

    /**
     * Смещает камеру вверх.
     *
     * @param delta - величина смещения в пикселях
     */
    public void moveUp(final double delta) {
        if(offsetY <= 0.5f - delta) {
            offsetY += delta * currentScale;
        }
    }

    /**
     * @return масштаб камеры
     */
    public float getCurrentScale() {
        return currentScale;
    }

    /**
     * @return смещение камеры по горизонтали
     */
    public float getOffsetX() {
        return offsetX;
    }

    /**
     * @return смещение камеры по вертикали
     */
    public float getOffsetY() {
        return offsetY;
    }

}
