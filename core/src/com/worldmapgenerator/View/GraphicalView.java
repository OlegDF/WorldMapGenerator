package com.worldmapgenerator.View;

import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public interface GraphicalView {

    void render(final GenericVisualInfo info);

    void switchPoints();

    void switchConnections();

    void switchPolygons();

    public void decreaseScale();

    public void increaseScale();

    public void moveLeft();

    public void moveRight();

    public void moveUp();

    public void moveDown();

}
