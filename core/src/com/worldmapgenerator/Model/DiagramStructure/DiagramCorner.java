package com.worldmapgenerator.Model.DiagramStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagramCorner {

    private final double x, y;

    private final ArrayList<DiagramCorner> neighbourCorners;
    private final ArrayList<DiagramPoint> neighbourPoints;
    private final ArrayList<DiagramEdge> neighbourEdges;

    public DiagramCorner(double x, double y) {
        this.x = x;
        this.y = y;
        neighbourCorners = new ArrayList<>();
        neighbourPoints = new ArrayList<>();
        neighbourEdges = new ArrayList<>();
    }

    void addNeighbourPoint(DiagramPoint p) {
        neighbourPoints.add(p);
    }

    void addNeighbourCorner(DiagramCorner c) {
        neighbourCorners.add(c);
        c.addNeighbourCorner(this);
    }

    void addNeighbourEdge(DiagramEdge e) {
        neighbourEdges.add(e);
    }

    List<DiagramCorner> getNeighbourCorners() {
        return Collections.unmodifiableList(neighbourCorners);
    }

    List<DiagramPoint> getNeighbourPoints() {
        return Collections.unmodifiableList(neighbourPoints);
    }

    List<DiagramEdge> getNeighbourEdges() {
        return Collections.unmodifiableList(neighbourEdges);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
