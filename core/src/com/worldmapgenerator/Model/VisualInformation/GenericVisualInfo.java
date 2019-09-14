package com.worldmapgenerator.Model.VisualInformation;

import java.util.Set;

public interface GenericVisualInfo {

    /**
     * Возвращает набор строк, соответствующих каждой вершине графа карты.
     * Формат: (x вершины);(y вершины)
     */
    Set<String> getPointsDescription();

    /**
     * Возвращает набор строк, соответствующих каждому ребру графа карты.
     * Формат: (x первой вершины);(y первой вершины);(x второй вершины);(y второй вершины)
     */
    Set<String> getConnectionsDescription();

    /**
     * Возвращает набор строк, соответствующих каждому ребру графа карты.
     * Формат: (x первой вершины);(y первой вершины);(x второй вершины);(y второй вершины)
     */
    Set<String> getPolygonsDescription();

}
