package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

class Triangle {

    private DiagramPoint p1, p2, p3;

    Triangle(DiagramPoint p1, DiagramPoint p2, DiagramPoint p3) {
        this.p1 = p1;
        if((p2.getX() - p1.getX())*(p3.getY() - p1.getY())-(p3.getX() - p1.getX())*(p2.getY() - p1.getY()) > 0) {
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

    boolean isPointInCircumcircle(DiagramPoint checkedPoint) {
        float x1 = p1.getX() - checkedPoint.getX();
        float y1 = p1.getY() - checkedPoint.getY();
        float x2 = p2.getX() - checkedPoint.getX();
        float y2 = p2.getY() - checkedPoint.getY();
        float x3 = p3.getX() - checkedPoint.getX();
        float y3 = p3.getY() - checkedPoint.getY();
        return  (x1 * x1 + y1 * y1) * (x2 * y3 - x3 * y2) -
                (x2 * x2 + y2 * y2) * (x1 * y3 - x3 * y1) +
                (x3 * x3 + y3 * y3) * (x1 * y2 - x2 * y1)
                > 0;
    }

}
