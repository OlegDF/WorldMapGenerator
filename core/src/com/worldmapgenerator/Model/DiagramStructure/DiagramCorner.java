package com.worldmapgenerator.Model.DiagramStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Угол одного или более многоугольников диаграммы; вершина нескольких рёбер
 */
public class DiagramCorner {

    private final double x, y;

    private final ArrayList<DiagramCorner> neighbourCorners;
    private final ArrayList<DiagramPoint> neighbourPoints;
    private final ArrayList<DiagramEdge> neighbourEdges;

    public DiagramCorner(final double x, final double y) {
        this.x = x;
        this.y = y;
        neighbourCorners = new ArrayList<>();
        neighbourPoints = new ArrayList<>();
        neighbourEdges = new ArrayList<>();
    }

    void addNeighbourPoint(final DiagramPoint p) {
        neighbourPoints.add(p);
    }

    void addNeighbourCorner(final DiagramCorner c) {
        neighbourCorners.add(c);
        c.neighbourCorners.add(this);
    }

    void addNeighbourEdge(final DiagramEdge e) {
        neighbourEdges.add(e);
    }

    public List<DiagramCorner> getNeighbourCorners() {
        return Collections.unmodifiableList(neighbourCorners);
    }

    public List<DiagramPoint> getNeighbourPoints() {
        return Collections.unmodifiableList(neighbourPoints);
    }

    public List<DiagramEdge> getNeighbourEdges() {
        return Collections.unmodifiableList(neighbourEdges);
    }

    /**
     * Получает угол вектора между данной вершиной и этим углом, против часовой стрелки
     *
     * @param point - вершина - начало вектора
     * @return угол в радианах
     */
    public double convertedAngle(final DiagramPoint point) {
        double angle = Math.atan2(y - point.getY(), x - point.getX());
        if (angle < 0) {
            angle = Math.PI * 2 + angle;
        }
        return angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DiagramCorner that = (DiagramCorner) o;
        return Double.compare(that.getX(), getX()) == 0 &&
                Double.compare(that.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

}
