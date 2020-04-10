package com.worldmapgenerator.Model;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.GameModel.Field;
import com.worldmapgenerator.Model.VisualInformation.GenericVisualInfo;
import com.worldmapgenerator.Model.VisualInformation.VoronoiVisualInfo;
import com.worldmapgenerator.Model.VoronoiDiagram.VoronoiDiagram;

public class VoronoiMapModel {

    private Field field;

    public VoronoiMapModel() {
        field = new Field(VoronoiDiagram.randomDiagram(64, new Border(0, 0, 1f, 1f)));
        relaxMap();
        relaxMap();
    }

    public void relaxMap() {
        field = field.relaxMap();
    }

    public GenericVisualInfo getInfo() {
        return new VoronoiVisualInfo(field.getAreas(), field.getDiagram().getMapBorder());
    }

}
