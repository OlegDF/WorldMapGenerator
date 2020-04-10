package com.worldmapgenerator.Model.VisualInformation;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramEdge;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;
import com.worldmapgenerator.Model.GameModel.FieldArea;

import java.util.*;

public class VoronoiVisualInfo implements GenericVisualInfo {

    private final Set<AreaInfo> areasDescription;
    private final Set<Float[]> connectionsDescription;

    private final Border border;

    /**
     * Создает описания элементов - вершин графа, ребер между вершинами и границ многоугольников - по диаграмме Вороного
     *
     * @param areas - набор вершиин, составляющих диаграмму Вороного
     * @param border - границы диаграммы
     */
    public VoronoiVisualInfo(final List<FieldArea> areas, final Border border) {
        areasDescription = new HashSet<>();
        connectionsDescription = new HashSet<>();
        for (final FieldArea area: areas) {
            Float[] newPoint = {(float)area.getPoint().getX(), (float)area.getPoint().getY()};
            final List<DiagramCorner> corners = new ArrayList<>(area.getPoint().getNeighbourCorners());
            Collections.sort(corners, new Comparator<DiagramCorner>() {
                @Override
                public int compare(final DiagramCorner o1, final DiagramCorner o2) {
                    return Double.compare(o2.convertedAngle(area.getPoint()), o1.convertedAngle(area.getPoint()));
                }
            });
            Float[] polygon = new Float[corners.size() * 2];
            for (int i = 0; i < corners.size(); i++) {
                polygon[i * 2] = (float)corners.get(i).getX();
                polygon[i * 2 + 1] = (float)corners.get(i).getY();
            }
            areasDescription.add(new AreaInfo(newPoint, polygon, area.getTerrainType()));
            for (final DiagramEdge edge : area.getPoint().getNeighbourEdges()) {
                if(edge.getC1().getX() >= border.borderLeft && edge.getC1().getX() <= border.borderRight &&
                        edge.getC1().getY() >= border.borderBottom && edge.getC1().getY() <= border.borderTop ||
                        edge.getC2().getX() >= border.borderLeft && edge.getC2().getX() <= border.borderRight &&
                                edge.getC2().getY() >= border.borderBottom && edge.getC2().getY() <= border.borderTop) {
                    DiagramPoint neighbour;
                    if(edge.getP1().equals(area.getPoint())) {
                        neighbour = edge.getP2();
                    } else {
                        neighbour = edge.getP1();
                    }
                    if (area.getPoint().compareTo(neighbour) < 0) {
                        Float[] newConnection = {(float)area.getPoint().getX(), (float)area.getPoint().getY(), (float)neighbour.getX(), (float)neighbour.getY()};
                        connectionsDescription.add(newConnection);
                    } else {
                        Float[] newConnection = {(float)neighbour.getX(), (float)neighbour.getY(), (float)area.getPoint().getX(), (float)area.getPoint().getY()};
                        connectionsDescription.add(newConnection);
                    }
                }
            }
        }
        this.border = border;
    }

    /**
     * @return описания вершин диаграммы в форме "x;y"
     */
    public Set<AreaInfo> getAreasDescription() {
        return areasDescription;
    }

    /**
     * @return описания ребер диаграммы в форме "x1;y1;x2;y2"
     */
    public Set<Float[]> getConnectionsDescription() {
        return connectionsDescription;
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
