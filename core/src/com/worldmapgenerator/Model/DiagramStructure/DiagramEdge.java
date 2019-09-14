package com.worldmapgenerator.Model.DiagramStructure;

import java.util.Objects;

public class DiagramEdge {

    private final DiagramCorner c1, c2;
    private final DiagramPoint p1, p2;

    public DiagramEdge(DiagramCorner c1, DiagramCorner c2, DiagramPoint p1, DiagramPoint p2) {
        this.c1 = c1;
        this.c2 = c2;
        if(p1.compareTo(p2) < 0) {
            this.p1 = p1;
            this.p2 = p2;
        } else {
            this.p1 = p2;
            this.p2 = p1;
        }
        p1.addNeighbourEdge(this);
        p2.addNeighbourEdge(this);
        p1.addNeighbourPoint(p2);
        c1.addNeighbourEdge(this);
        c2.addNeighbourEdge(this);
        c1.addNeighbourCorner(c2);
        if(p1.getY() > 1 || p2.getY() > 1) {
            System.out.println("Supertriangle detected");
        }
    }

    public DiagramCorner getC1() {
        return c1;
    }

    public DiagramCorner getC2() {
        return c2;
    }

    public DiagramPoint getP1() {
        return p1;
    }

    public DiagramPoint getP2() {
        return p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiagramEdge that = (DiagramEdge) o;
        return Objects.equals(getP1(), that.getP1()) &&
                Objects.equals(getP2(), that.getP2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP1(), getP2());
    }
}
