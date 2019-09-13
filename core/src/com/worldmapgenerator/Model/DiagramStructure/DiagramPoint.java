package com.worldmapgenerator.Model.DiagramStructure;

import java.util.*;

public class DiagramPoint implements Comparable<DiagramPoint> {

    private final double x, y;

    private final Set<DiagramPoint> neighbourPoints;
    private final Set<DiagramCorner> neighbourCorners;
    private final Set<DiagramEdge> neighbourEdges;

    public DiagramPoint(double x, double y) {
        this.x = x;
        this.y = y;
        neighbourPoints = new HashSet<>();
        neighbourCorners = new HashSet<>();
        neighbourEdges = new HashSet<>();
    }

    public void addNeighbourPoint(DiagramPoint p) {
        neighbourPoints.add(p);
        p.neighbourPoints.add(this);
    }

    public void addNeighbourCorner(DiagramCorner c) {
        neighbourCorners.add(c);
        c.addNeighbourPoint(this);
    }

    void addNeighbourEdge(DiagramEdge e) {
        neighbourEdges.add(e);
    }

    public Set<DiagramPoint> getNeighbourPoints() {
        return Collections.unmodifiableSet(neighbourPoints);
    }

    Set<DiagramCorner> getNeighbourCorners() {
        return Collections.unmodifiableSet(neighbourCorners);
    }

    Set<DiagramEdge> getNeighbourEdges() {
        return Collections.unmodifiableSet(neighbourEdges);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(DiagramPoint o) {
        if(this.getY() == o.getY()) {
            return Double.compare(this.getX(), o.getX());
        } else {
            return Double.compare(this.getY(), o.getY());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramPoint point = (DiagramPoint) o;
        return Double.compare(point.getX(), getX()) == 0 &&
                Double.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
