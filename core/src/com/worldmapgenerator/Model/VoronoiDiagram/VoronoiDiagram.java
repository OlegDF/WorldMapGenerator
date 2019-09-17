package com.worldmapgenerator.Model.VoronoiDiagram;

import com.worldmapgenerator.Model.DelaunayTriangulation.DelaunayTriangulation;
import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramEdge;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;
import java.util.List;

public class VoronoiDiagram {

    private final DelaunayTriangulation triangulation;

    private VoronoiDiagram(int numberOfPoints, double borderLeft, double borderBottom, double borderRight,
                           double borderTop, long seed) {
        triangulation = DelaunayTriangulation.seededTriangulation(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
        generatePolygons();
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     * @param numberOfPoints - количество вершин в диаграмме
     * @param borderLeft - минимальная координата X каждой вершины
     * @param borderBottom - минимальная координата Y каждой вершины
     * @param borderRight - максимальная координата X каждой вершины
     * @param borderTop - максимальная координата Y каждой вершины
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram randomDiagram(int numberOfPoints, double borderLeft, double borderBottom,
                                               double borderRight, double borderTop) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop,
                (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     * @param numberOfPoints - количество вершин в диаграмме
     * @param borderLeft - минимальная координата X каждой вершины
     * @param borderBottom - минимальная координата Y каждой вершины
     * @param borderRight - максимальная координата X каждой вершины
     * @param borderTop - максимальная координата Y каждой вершины
     * @param seed - зерно
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram seededDiagram(int numberOfPoints, double borderLeft, double borderBottom,
                                               double borderRight, double borderTop, long seed) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     * @param numberOfPoints - количество вершин в диаграмме
     * @param borderRight - максимальная координата X каждой вершины
     * @param borderTop - максимальная координата Y каждой вершины
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram randomDiagramStartingAt00(int numberOfPoints, double borderRight, double borderTop) {
        return new VoronoiDiagram(numberOfPoints, 0, 0, borderRight, borderTop,
                (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0) и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     * @param numberOfPoints - количество вершин в диаграмме
     * @param borderRight - максимальная координата X каждой вершины
     * @param borderTop - максимальная координата Y каждой вершины
     * @param seed - зерно
     * @return диаграмма Вороного
     */
    public static VoronoiDiagram seededDiagramStartingAt00(int numberOfPoints, double borderRight, double borderTop, long seed) {
        return new VoronoiDiagram(numberOfPoints, 0, 0, borderRight, borderTop, seed);
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

    /**
     * @return набор вершин, определяющих диаграмму
     */
    public ArrayList<DiagramPoint> getPoints() {
        return triangulation.getPoints();
    }

    public double getMapBorderLeft() {
        return triangulation.getMapBorderLeft();
    }

    public double getMapBorderBottom() {
        return triangulation.getMapBorderBottom();
    }

    public double getMapBorderRight() {
        return triangulation.getMapBorderRight();
    }

    public double getMapBorderTop() {
        return triangulation.getMapBorderTop();
    }

}
