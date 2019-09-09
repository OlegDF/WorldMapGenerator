package com.worldmapgenerator.Model.DiagramStructure;

public class DiagramEdge {

    private final DiagramCorner c1, c2;
    private final DiagramPoint p1, p2;

    public DiagramEdge(DiagramCorner c1, DiagramCorner c2, DiagramPoint p1, DiagramPoint p2) {
        this.c1 = c1;
        this.c2 = c2;
        this.p1 = p1;
        this.p2 = p2;
        if(p1 != null) {
            p1.addNeighbourEdge(this);
        }
        if(p2 != null) {
            p2.addNeighbourEdge(this);
        }
        if(p1 != null && p2 != null) {
            p1.addNeighbourPoint(p2);
        }
        c1.addNeighbourEdge(this);
        c2.addNeighbourEdge(this);
        c1.addNeighbourCorner(c2);
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
}
