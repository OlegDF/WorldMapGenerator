package com.worldmapgenerator.Model.VisualInformation;

import java.util.Set;

public interface GenericVisualInfo {

    /**
     * @return набор объектов, содержащих информацию о каждой вершине и типе местности.
     */
    Set<AreaInfo> getAreasDescription();

    /**
     * @return набор массивов чисел, соответствующих каждому ребру графа карты.
     * Формат: (x первой вершины);(y первой вершины);(x второй вершины);(y второй вершины)
     */
    Set<Float[]> getConnectionsDescription();

    double getMapBorderLeft();

    double getMapBorderBottom();

    double getMapBorderRight();

    double getMapBorderTop();

}
