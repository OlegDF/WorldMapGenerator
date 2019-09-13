package com.worldmapgenerator.Model;

import com.worldmapgenerator.Model.DelaunayTriangulation.DelaunayTriangulation;
import com.worldmapgenerator.Model.VisualInformation.DelaunayVisualInfo;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;

public class DelaunayMapModel {

    private DelaunayTriangulation triangulation;

    public DelaunayMapModel() {
        triangulation = DelaunayTriangulation.randomTriangulationStartingAt00(1000, 1, 1);
    }

    public GenericVisualInfo getInfo() {
        return new DelaunayVisualInfo(triangulation.getPoints());
    }

}
