package com.worldmapgenerator.Model.DiagramStructure;

import java.util.*;

public class DiagramPoint implements Comparable<DiagramPoint> {

    private final float x, y;

    private final Set<DiagramPoint> neighbourPoints;
    private final Set<DiagramCorner> neighbourCorners;
    private final Set<DiagramEdge> neighbourEdges;

    public DiagramPoint(float x, float y) {
        this.x = x;
        this.y = y;
        neighbourPoints = new HashSet<>();
        neighbourCorners = new HashSet<>();
        neighbourEdges = new HashSet<>();
    }

    void addNeighbourPoint(DiagramPoint p) {
        neighbourPoints.add(p);
        p.neighbourPoints.add(this);
    }

    void addNeighbourCorner(DiagramCorner c) {
        neighbourCorners.add(c);
        c.addNeighbourPoint(this);
    }

    void addNeighbourEdge(DiagramEdge e) {
        neighbourEdges.add(e);
    }

    Set<DiagramPoint> getNeighbourPoints() {
        return Collections.unmodifiableSet(neighbourPoints);
    }

    Set<DiagramCorner> getNeighbourCorners() {
        return Collections.unmodifiableSet(neighbourCorners);
    }

    Set<DiagramEdge> getNeighbourEdges() {
        return Collections.unmodifiableSet(neighbourEdges);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public int compareTo(DiagramPoint o) {
        if(this.getY() == o.getY()) {
            return Float.compare(this.getX(), o.getX());
        } else {
            return Float.compare(this.getY(), o.getY());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramPoint point = (DiagramPoint) o;
        return Float.compare(point.getX(), getX()) == 0 &&
                Float.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
