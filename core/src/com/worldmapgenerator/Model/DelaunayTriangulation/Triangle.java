package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramCorner;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

class Triangle {

    private final DiagramPoint p1;
    private final DiagramPoint p2;
    private final DiagramPoint p3;

    Triangle(final DiagramPoint p1, final DiagramPoint p2, final DiagramPoint p3) {
        this.p1 = p1;
        if ((p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) - (p3.getX() - p1.getX()) * (p2.getY() - p1.getY()) > 0) {
            this.p2 = p2;
            this.p3 = p3;
        } else {
            this.p2 = p3;
            this.p3 = p2;
        }
    }

    TriangleEdge e12() {
        return new TriangleEdge(p1, p2);
    }

    TriangleEdge e23() {
        return new TriangleEdge(p2, p3);
    }

    TriangleEdge e31() {
        return new TriangleEdge(p3, p1);
    }

    boolean isPointVertex(final DiagramPoint checkedPoint) {
        return checkedPoint.equals(p1) || checkedPoint.equals(p2) || checkedPoint.equals(p3);
    }

    boolean isPointInCircumcircle(final DiagramPoint checkedPoint) {
        final double x1 = p1.getX() - checkedPoint.getX();
        final double y1 = p1.getY() - checkedPoint.getY();
        final double x2 = p2.getX() - checkedPoint.getX();
        final double y2 = p2.getY() - checkedPoint.getY();
        final double x3 = p3.getX() - checkedPoint.getX();
        final double y3 = p3.getY() - checkedPoint.getY();
        return (x1 * x1 + y1 * y1) * (x2 * y3 - x3 * y2) -
                (x2 * x2 + y2 * y2) * (x1 * y3 - x3 * y1) +
                (x3 * x3 + y3 * y3) * (x1 * y2 - x2 * y1)
                > 0;
    }

    void createCircumcenter(final DiagramPoint supertrianglePoint1, final DiagramPoint supertrianglePoint2,
                            final DiagramPoint supertrianglePoint3) {
        final double mid12x = (p1.getX() + p2.getX()) / 2;
        final double mid12y = (p1.getY() + p2.getY()) / 2;
        final double mid23x = (p2.getX() + p3.getX()) / 2;
        final double mid23y = (p2.getY() + p3.getY()) / 2;
        final double slope12 = -(p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        final double slope23 = -(p3.getX() - p2.getX()) / (p3.getY() - p2.getY());
        final double y12 = mid12y - slope12 * mid12x;
        final double y23 = mid23y - slope23 * mid23x;
        final double x = (y12 - y23) / (slope23 - slope12);
        final double y = (slope12 * x) + y12;
        final DiagramCorner circumcenter = new DiagramCorner(x, y);
        if (!p1.equals(supertrianglePoint1) && !p1.equals(supertrianglePoint2) && !p1.equals(supertrianglePoint3))
            p1.addNeighbourCorner(circumcenter);
        if (!p2.equals(supertrianglePoint1) && !p2.equals(supertrianglePoint2) && !p2.equals(supertrianglePoint3))
            p2.addNeighbourCorner(circumcenter);
        if (!p3.equals(supertrianglePoint1) && !p3.equals(supertrianglePoint2) && !p3.equals(supertrianglePoint3))
            p3.addNeighbourCorner(circumcenter);
    }

}
