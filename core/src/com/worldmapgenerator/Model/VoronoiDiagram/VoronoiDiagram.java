package com.worldmapgenerator.Model.VoronoiDiagram;

import com.worldmapgenerator.Model.DelaunayTriangulation.DelaunayTriangulation;
import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramEdge;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;
import java.util.List;

public class VoronoiDiagram {

    private final DelaunayTriangulation triangulation;

    private VoronoiDiagram(int numberOfPoints, Border border, long seed) {
        triangulation = DelaunayTriangulation.seededTriangulation(numberOfPoints, border, seed);
        generatePolygons();
    }

    private VoronoiDiagram(DelaunayTriangulation triangulation) {
        this.triangulation = triangulation;
        generatePolygons();
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     * @param numberOfPoints - количество вершин в диаграмме
     * @param border - координаты левого нижнего и правого верхнего углов
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram randomDiagram(int numberOfPoints, Border border) {
        return new VoronoiDiagram(numberOfPoints, border, (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     * @param numberOfPoints - количество вершин в диаграмме
     * @param border - координаты левого нижнего и правого верхнего углов
     * @param seed - зерно
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram seededDiagram(int numberOfPoints, Border border, long seed) {
        return new VoronoiDiagram(numberOfPoints, border, seed);
    }

    /**
     * Создает границы между точками, соответствующие многоугольникам Вороного
     */
    private void generatePolygons() {
        for (final DiagramPoint point : triangulation.getPoints()) {
            List<DiagramCorner> corners = new ArrayList<>(point.getNeighbourCorners());
            Collections.sort(corners, new Comparator<DiagramCorner>() {
                @Override
                public int compare(DiagramCorner o1, DiagramCorner o2) {
                    return Double.compare(o1.convertedAngle(point), o2.convertedAngle(point));
                }
            });
            for (int i = 0; i < corners.size(); i++) {
                DiagramCorner corner1 = corners.get(i);
                DiagramCorner corner2 = corners.get((i + 1) % corners.size());
                DiagramPoint point2 = null;
                for (DiagramPoint neighbour : corner1.getNeighbourPoints()) {
                    if (corner2.getNeighbourPoints().contains(neighbour)) {
                        point2 = neighbour;
                    }
                }
                if (point2 != null) {
                    new DiagramEdge(corner1, corner2, point, point2);
                }
            }
        }
    }

    public VoronoiDiagram getLloydRelaxation() {
        return new VoronoiDiagram(triangulation.getLloydRelaxation());
    }

    /**
     * @return набор вершин, определяющих диаграмму
     */
    public List<DiagramPoint> getPoints() {
        return triangulation.getPoints();
    }

    public Border getMapBorder() {
        return triangulation.getMapBorder();
    }

}
