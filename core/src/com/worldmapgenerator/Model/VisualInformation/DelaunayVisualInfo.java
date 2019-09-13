package com.worldmapgenerator.Model.VisualInformation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DelaunayVisualInfo implements GenericVisualInfo {

    private Set<String> pointsDescription;
    private Set<String> connectionsDescription;

    public DelaunayVisualInfo(final List<DiagramPoint> points) {
        pointsDescription = new HashSet<>();
        connectionsDescription = new HashSet<>();
        for(DiagramPoint point: points) {
            pointsDescription.add(point.getX() + ";" + point.getY());
            for(DiagramPoint neighbour: point.getNeighbourPoints()) {
                if(point.compareTo(neighbour) < 0) {
                    connectionsDescription.add(point.getX() + ";" + point.getY() + ";" + neighbour.getX() + ";" + neighbour.getY());
                } else {
                    connectionsDescription.add(neighbour.getX() + ";" + neighbour.getY() + ";" + point.getX() + ";" + point.getY());
                }
            }
        }
    }

    public Set<String> getPointsDescription() {
        return pointsDescription;
    }

    public Set<String> getConnectionsDescription() {
        return connectionsDescription;
    }

}
