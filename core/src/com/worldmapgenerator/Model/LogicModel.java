package com.worldmapgenerator.Model;

import com.worldmapgenerator.Model.DelaunayTriangulation.DelaunayTriangulation;

public class LogicModel {

    DelaunayTriangulation triangulation;

    public LogicModel() {
        triangulation = DelaunayTriangulation.randomTriangulationStartingAt00(100, 1, 1);
    }

}
