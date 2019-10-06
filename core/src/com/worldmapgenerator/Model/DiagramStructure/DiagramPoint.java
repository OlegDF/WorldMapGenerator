package com.worldmapgenerator.Model.DiagramStructure;

import java.awt.geom.Point2D;
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

    public Set<DiagramCorner> getNeighbourCorners() {
        return Collections.unmodifiableSet(neighbourCorners);
    }

    public Set<DiagramEdge> getNeighbourEdges() {
        return Collections.unmodifiableSet(neighbourEdges);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DiagramPoint getPolygonCenter(Border border) {
        double newPointX = 0;
        double newPointY = 0;
        double cornersNum = 0;
        boolean touchesLeft = false;
        boolean touchesRight = false;
        boolean touchesBottom = false;
        boolean touchesTop = false;
        List<DiagramCorner> corners = new ArrayList<>(neighbourCorners);
        final DiagramPoint point = this;
        Collections.sort(corners, new Comparator<DiagramCorner>() {
            @Override
            public int compare(DiagramCorner o1, DiagramCorner o2) {
                return Double.compare(o1.convertedAngle(point), o2.convertedAngle(point));
            }
        });
        for (int i = 0; i < corners.size(); i++) {
            DiagramCorner corner1 = corners.get(i);
            DiagramCorner corner2 = corners.get((i + 1) % corners.size());
            Border.PointPosition pos1 = border.determinePosition(corner1.getX(), corner1.getY());
            Border.PointPosition pos2 = border.determinePosition(corner2.getX(), corner2.getY());
            if(pos1 == Border.PointPosition.INSIDE || pos1 == Border.PointPosition.ON_BORDER) {
                newPointX += corner1.getX();
                newPointY += corner1.getY();
                cornersNum++;
            }
            if(pos1 == Border.PointPosition.INSIDE && pos2 == Border.PointPosition.OUTSIDE ||
                    pos1 == Border.PointPosition.OUTSIDE && pos2 == Border.PointPosition.INSIDE) {
                Point2D intersectionLeft = border.getIntersectionLeft(corner1.getX(), corner1.getY(),
                        corner2.getX(), corner2.getY());
                Point2D intersectionRight = border.getIntersectionRight(corner1.getX(), corner1.getY(),
                        corner2.getX(), corner2.getY());
                Point2D intersectionBottom = border.getIntersectionBottom(corner1.getX(), corner1.getY(),
                        corner2.getX(), corner2.getY());
                Point2D intersectionTop = border.getIntersectionTop(corner1.getX(), corner1.getY(),
                        corner2.getX(), corner2.getY());
                if(intersectionLeft != null) {
                    newPointX += intersectionLeft.getX();
                    newPointY += intersectionLeft.getY();
                    cornersNum++;
                    touchesLeft = true;
                }
                if(intersectionRight != null) {
                    newPointX += intersectionRight.getX();
                    newPointY += intersectionRight.getY();
                    cornersNum++;
                    touchesRight = true;
                }
                if(intersectionBottom != null) {
                    newPointX += intersectionBottom.getX();
                    newPointY += intersectionBottom.getY();
                    cornersNum++;
                    touchesBottom = true;
                }
                if(intersectionTop != null) {
                    newPointX += intersectionTop.getX();
                    newPointY += intersectionTop.getY();
                    cornersNum++;
                    touchesTop = true;
                }
            }
            if(pos1 == Border.PointPosition.OUTSIDE && pos2 == Border.PointPosition.OUTSIDE ||
                    pos1 == Border.PointPosition.ON_BORDER && pos2 == Border.PointPosition.OUTSIDE ||
                    pos1 == Border.PointPosition.OUTSIDE && pos2 == Border.PointPosition.ON_BORDER) {
                if(corner1.getX() < border.borderLeft || corner2.getX() < border.borderLeft) {
                    touchesLeft = true;
                }
                if(corner1.getX() > border.borderRight || corner2.getX() > border.borderRight) {
                    touchesRight = true;
                }
                if(corner1.getY() < border.borderBottom || corner2.getY() < border.borderBottom) {
                    touchesBottom = true;
                }
                if(corner1.getY() > border.borderTop || corner2.getY() > border.borderTop) {
                    touchesTop = true;
                }
            }
        }
        if(touchesLeft && touchesBottom) {
            newPointX += border.borderLeft;
            newPointY += border.borderBottom;
            cornersNum++;
        }
        if(touchesLeft && touchesTop) {
            newPointX += border.borderLeft;
            newPointY += border.borderTop;
            cornersNum++;
        }
        if(touchesRight && touchesBottom) {
            newPointX += border.borderRight;
            newPointY += border.borderBottom;
            cornersNum++;
        }
        if(touchesRight && touchesTop) {
            newPointX += border.borderRight;
            newPointY += border.borderTop;
            cornersNum++;
        }
        return new DiagramPoint(newPointX / cornersNum, newPointY / cornersNum);
    }

    @Override
    public int compareTo(DiagramPoint o) {
        if (this.getY() == o.getY()) {
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
