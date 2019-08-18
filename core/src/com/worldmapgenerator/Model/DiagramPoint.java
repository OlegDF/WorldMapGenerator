package com.worldmapgenerator.Model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagramPoint implements Comparable<DiagramPoint> {

    private final Vector2 coordinates;

    private final ArrayList<DiagramPoint> neighbourPoints;
    private final ArrayList<DiagramCorner> neighbourCorners;
    private final ArrayList<DiagramEdge> neighbourEdges;

    DiagramPoint(float x, float y) {
        coordinates = new Vector2(x, y);
        neighbourPoints = new ArrayList<>();
        neighbourCorners = new ArrayList<>();
        neighbourEdges = new ArrayList<>();
    }

    void addNeighbour(DiagramPoint p) {
        neighbourPoints.add(p);
    }

    List<DiagramPoint> getNeighbourPoints() {
        return Collections.unmodifiableList(neighbourPoints);
    }

    ArrayList<DiagramCorner> getNeighbourCorners() {
        return neighbourCorners;
    }

    ArrayList<DiagramEdge> getNeighbourEdges() {
        return neighbourEdges;
    }

    float getX() {
        return coordinates.x;
    }

    float getY() {
        return coordinates.y;
    }

    @Override
    public int compareTo(DiagramPoint o) {
        if(this.getY() == o.getY()) {
            return Float.compare(this.getX(), o.getX());
        } else {
            return Float.compare(this.getY(), o.getY());
        }
    }
}
