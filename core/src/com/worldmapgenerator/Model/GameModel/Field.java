package com.worldmapgenerator.Model.GameModel;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;
import com.worldmapgenerator.Model.VoronoiDiagram.VoronoiDiagram;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final VoronoiDiagram diagram;
    private final List<FieldArea> areas;

    public Field(VoronoiDiagram diagram) {
        this.diagram = diagram;
        this.areas = new ArrayList<>();
        for(DiagramPoint point: diagram.getPoints()) {
            areas.add(new FieldArea(point, (int)(Math.random() * 3)));
        }
    }

    public Field relaxMap() {
        return new Field(diagram.getLloydRelaxation());
    }

    public VoronoiDiagram getDiagram() {
        return diagram;
    }

    public List<FieldArea> getAreas() {
        return areas;
    }

}
