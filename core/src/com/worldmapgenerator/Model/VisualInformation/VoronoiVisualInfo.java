package com.worldmapgenerator.Model.VisualInformation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class VoronoiVisualInfo implements GenericVisualInfo {

    private Set<String> pointsDescription;
    private Set<String> connectionsDescription;
    private Set<String> polygonsDescription;

    public VoronoiVisualInfo(final List<DiagramPoint> points) {
        pointsDescription = new HashSet<>();
        connectionsDescription = new HashSet<>();
        polygonsDescription = new HashSet<>();
        for(final DiagramPoint point: points) {
            pointsDescription.add(point.getX() + ";" + point.getY());
            for(DiagramPoint neighbour: point.getNeighbourPoints()) {
                if(point.compareTo(neighbour) < 0) {
                    connectionsDescription.add(point.getX() + ";" + point.getY() + ";" + neighbour.getX() + ";" + neighbour.getY());
                } else {
                    connectionsDescription.add(neighbour.getX() + ";" + neighbour.getY() + ";" + point.getX() + ";" + point.getY());
                }
            }
            StringBuilder polygon = new StringBuilder();
            List<DiagramCorner> corners = new ArrayList<>(point.getNeighbourCorners());
            Collections.sort(corners, new Comparator<DiagramCorner>() {
                @Override
                public int compare(DiagramCorner o1, DiagramCorner o2) {
                    return Double.compare(o1.convertedAngle(point), o2.convertedAngle(point));
                }
            });
            //polygon.append(point.getX()).append(";").append(point.getY()).append(";");
            for(DiagramCorner corner: corners) {
                polygon.append(corner.getX()).append(";").append(corner.getY()).append(";");
            }
            polygonsDescription.add(polygon.toString());
        }
    }

    public Set<String> getPointsDescription() {
        return pointsDescription;
    }

    public Set<String> getConnectionsDescription() {
        return connectionsDescription;
    }

    public Set<String> getPolygonsDescription() {
        return polygonsDescription;
    }

}
