package com.worldmapgenerator.Model.VisualInformation;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class VoronoiVisualInfo implements GenericVisualInfo {

    private final Set<String> pointsDescription;
    private final Set<String> connectionsDescription;
    private final Set<String> polygonsDescription;

    private final Border border;

    /**
     * Создает описания элементов - вершин графа, ребер между вершинами и границ многоугольников - по диаграмме Вороного
     * @param points - набор вершиин, составляющих диаграмму Вороного
     */
    public VoronoiVisualInfo(final List<DiagramPoint> points, final Border border) {
        pointsDescription = new HashSet<>();
        connectionsDescription = new HashSet<>();
        polygonsDescription = new HashSet<>();
        for (final DiagramPoint point : points) {
            pointsDescription.add(point.getX() + ";" + point.getY());
            for (final DiagramPoint neighbour : point.getNeighbourPoints()) {
                if (point.compareTo(neighbour) < 0) {
                    connectionsDescription.add(point.getX() + ";" + point.getY() + ";" + neighbour.getX() + ";" + neighbour.getY());
                } else {
                    connectionsDescription.add(neighbour.getX() + ";" + neighbour.getY() + ";" + point.getX() + ";" + point.getY());
                }
            }
            final StringBuilder polygon = new StringBuilder();
            final List<DiagramCorner> corners = new ArrayList<>(point.getNeighbourCorners());
            Collections.sort(corners, new Comparator<DiagramCorner>() {
                @Override
                public int compare(final DiagramCorner o1, final DiagramCorner o2) {
                    return Double.compare(o1.convertedAngle(point), o2.convertedAngle(point));
                }
            });
            for (final DiagramCorner corner : corners) {
                polygon.append(corner.getX()).append(";").append(corner.getY()).append(";");
            }
            polygonsDescription.add(polygon.toString());
        }
        this.border = border;
    }

    /**
     * @return описания вершин диаграммы в форме "x;y"
     */
    public Set<String> getPointsDescription() {
        return pointsDescription;
    }

    /**
     * @return описания ребер диаграммы в форме "x1;y1;x2;y2"
     */
    public Set<String> getConnectionsDescription() {
        return connectionsDescription;
    }

    /**
     * @return описания многоугольников диаграммы в форме "x1;y1;x2;y2;..."
     */
    public Set<String> getPolygonsDescription() {
        return polygonsDescription;
    }

    @Override
    public double getMapBorderLeft() {
        return border.borderLeft;
    }

    @Override
    public double getMapBorderBottom() {
        return border.borderBottom;
    }

    @Override
    public double getMapBorderRight() {
        return border.borderRight;
    }

    @Override
    public double getMapBorderTop() {
        return border.borderTop;
    }

}
