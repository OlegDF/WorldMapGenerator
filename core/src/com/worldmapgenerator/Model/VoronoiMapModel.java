package com.worldmapgenerator.Model;

import com.worldmapgenerator.Model.VisualInformation.VoronoiVisualInfo;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;
import com.worldmapgenerator.Model.VoronoiDiagram.VoronoiDiagram;

public class VoronoiMapModel {

    private VoronoiDiagram diagram;

    public VoronoiMapModel() {
        diagram = VoronoiDiagram.randomDiagram(100, 0, 0, 1, 1);
    }

    public GenericVisualInfo getInfo() {
        return new VoronoiVisualInfo(diagram.getPoints(), diagram.getMapBorderLeft(), diagram.getMapBorderBottom(),
                diagram.getMapBorderRight(), diagram.getMapBorderTop());
    }

}
