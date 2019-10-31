package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.Objects;

class TriangleEdge {

    DiagramPoint p1, p2;
    int numberOfAppearances = 1;

    TriangleEdge(final DiagramPoint p1, final DiagramPoint p2) {
        if (p1.compareTo(p2) < 0) {
            this.p1 = p1;
            this.p2 = p2;
        } else {
            this.p1 = p2;
            this.p2 = p1;
        }
    }

    void addAppearance() {
        numberOfAppearances++;
    }

    void connectPoints() {
        p1.addNeighbourPoint(p2);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TriangleEdge that = (TriangleEdge) o;
        return p1.equals(that.p1) &&
                p2.equals(that.p2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2);
    }

}
