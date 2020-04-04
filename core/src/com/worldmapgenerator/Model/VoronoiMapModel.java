package com.worldmapgenerator.Model;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;
import com.worldmapgenerator.Model.VisualInformation.VoronoiVisualInfo;
import com.worldmapgenerator.Model.VoronoiDiagram.VoronoiDiagram;

public class VoronoiMapModel {

    private VoronoiDiagram diagram;

    public VoronoiMapModel() {
        diagram = VoronoiDiagram.randomDiagram(64, new Border(0, 0, 1f, 1f));
    }

    public void relaxMap() {
        diagram = diagram.getLloydRelaxation();
    }

    public GenericVisualInfo getInfo() {
        return new VoronoiVisualInfo(diagram.getPoints(), diagram.getMapBorder());
    }

}
