package com.worldmapgenerator.Model.VisualInformation;

import java.util.Set;

public interface GenericVisualInfo {

    /**
     * @return набор строк, соответствующих каждой вершине графа карты.
     * Формат: (x вершины);(y вершины)
     */
    Set<Float[]> getPointsDescription();

    /**
     * @return набор строк, соответствующих каждому ребру графа карты.
     * Формат: (x первой вершины);(y первой вершины);(x второй вершины);(y второй вершины)
     */
    Set<Float[]> getConnectionsDescription();

    /**
     * @return набор строк, соответствующих каждому ребру графа карты.
     * Формат: (x первой вершины);(y первой вершины);(x второй вершины);(y второй вершины)
     */
    Set<Float[]> getPolygonsDescription();

    double getMapBorderLeft();

    double getMapBorderBottom();

    double getMapBorderRight();

    double getMapBorderTop();

}
