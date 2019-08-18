package com.worldmapgenerator.Model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagramCorner {

    private final Vector2 coordinates;

    private final ArrayList<DiagramCorner> neighbourCorners;
    private final ArrayList<DiagramPoint> neighbourPoints;
    private final ArrayList<DiagramEdge> neighbourEdges;

    DiagramCorner(float x, float y) {
        coordinates = new Vector2(x, y);
        neighbourCorners = new ArrayList<>();
        neighbourPoints = new ArrayList<>();
        neighbourEdges = new ArrayList<>();
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

}
