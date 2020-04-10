package com.worldmapgenerator.Model.GameModel;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

public class FieldArea {

    private final DiagramPoint point;
    private int terrainType;

    public FieldArea(DiagramPoint point, int terrainType) {
        this.point = point;
        this.terrainType = terrainType;
    }

    public DiagramPoint getPoint() {
        return point;
    }

    public int getTerrainType() {
        return terrainType;
    }
}
