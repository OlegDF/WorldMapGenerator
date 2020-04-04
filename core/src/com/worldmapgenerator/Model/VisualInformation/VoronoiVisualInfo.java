package com.worldmapgenerator.Model.VisualInformation;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class VoronoiVisualInfo implements GenericVisualInfo {

    private final Set<Float[]> pointsDescription;
    private final Set<Float[]> connectionsDescription;
    private final Set<Float[]> polygonsDescription;

    private final Border border;

    /**
     * Создает описания элементов - вершин графа, ребер между вершинами и границ многоугольников - по диаграмме Вороного
     *
     * @param points - набор вершиин, составляющих диаграмму Вороного
     */
    public VoronoiVisualInfo(final List<DiagramPoint> points, final Border border) {
        pointsDescription = new HashSet<>();
        connectionsDescription = new HashSet<>();
        polygonsDescription = new HashSet<>();
        for (final DiagramPoint point : points) {
            Float[] newPoint = {(float)point.getX(), (float)point.getY()};
            pointsDescription.add(newPoint);
            for (final DiagramPoint neighbour : point.getNeighbourPoints()) {
                if (point.compareTo(neighbour) < 0) {
                    Float[] newConnection = {(float)point.getX(), (float)point.getY(), (float)neighbour.getX(), (float)neighbour.getY()};
                    connectionsDescription.add(newConnection);
                } else {
                    Float[] newConnection = {(float)neighbour.getX(), (float)neighbour.getY(), (float)point.getX(), (float)point.getY()};
                    connectionsDescription.add(newConnection);
                }
            }
            final List<DiagramCorner> corners = new ArrayList<>(point.getNeighbourCorners());
            Collections.sort(corners, new Comparator<DiagramCorner>() {
                @Override
                public int compare(final DiagramCorner o1, final DiagramCorner o2) {
                    return Double.compare(o1.convertedAngle(point), o2.convertedAngle(point));
                }
            });
            Float[] polygon = new Float[corners.size() * 2];
            for (int i = 0; i < corners.size(); i++) {
                polygon[i * 2] = (float)corners.get(i).getX();
                polygon[i * 2 + 1] = (float)corners.get(i).getY();
            }
            polygonsDescription.add(polygon);
        }
        this.border = border;
    }

    /**
     * @return описания вершин диаграммы в форме "x;y"
     */
    public Set<Float[]> getPointsDescription() {
        return pointsDescription;
    }

    /**
     * @return описания ребер диаграммы в форме "x1;y1;x2;y2"
     */
    public Set<Float[]> getConnectionsDescription() {
        return connectionsDescription;
    }

    /**
     * @return описания многоугольников диаграммы в форме "x1;y1;x2;y2;..."
     */
    public Set<Float[]> getPolygonsDescription() {
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
