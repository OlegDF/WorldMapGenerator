package com.worldmapgenerator.View;

import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public interface GraphicalView {

    /**
     * Отображает содержимое объекта на экране.
     *
     * @param info - информация о состоянии бизнес-логики, которая будет ипользоваться для отрисовки
     */
    void render(final GenericVisualInfo info);

    /**
     * Уменьшает масштаб изображения.
     */
    public void decreaseScale();

    /**
     * Увеличивает масштаб изображения.
     */
    public void increaseScale();

    /**
     * Смещает камеру влево.
     */
    public void moveLeft();

    /**
     * Смещает камеру вправо.
     */
    public void moveRight();

    /**
     * Смещает камеру вверх.
     */
    public void moveUp();

    /**
     * Смещает камеру вниз.
     */
    public void moveDown();

}
