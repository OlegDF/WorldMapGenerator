package com.worldmapgenerator.Model.VisualInformation;

public class AreaInfo {

    public final Float[] point;
    public final Float[] polygon;
    public final int terrainType;

    /**
     * Создает объект, содержащий информацию об одном из участков сгенерированного графа
     *
     * @param point - координаты центра участка
     * @param polygon - координаты каждой вершины границы участка (последовательно, по часовой стрелке)
     * @param terrainType - тип местности
     */
    public AreaInfo(Float[] point, Float[] polygon, int terrainType) {
        this.point = point;
        this.polygon = polygon;
        this.terrainType = terrainType;
    }

}
