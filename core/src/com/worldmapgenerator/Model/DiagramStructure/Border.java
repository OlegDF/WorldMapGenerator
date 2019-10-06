package com.worldmapgenerator.Model.DiagramStructure;

import java.awt.geom.Point2D;

public class Border {

    public final double borderLeft, borderRight, borderBottom, borderTop;

    public Border(double borderLeft, double borderBottom, double borderRight, double borderTop) {
        if (borderRight < borderLeft) {
            double swap = borderRight;
            borderRight = borderLeft;
            borderLeft = swap;
        }
        if (borderTop < borderBottom) {
            double swap = borderTop;
            borderTop = borderBottom;
            borderBottom = swap;
        }
        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
        this.borderBottom = borderBottom;
        this.borderTop = borderTop;
    }

    Point2D getIntersectionLeft(double x1, double y1, double x2, double y2) {
        if (x1 <= borderLeft && x2 >= borderLeft || x1 >= borderLeft && x2 <= borderLeft) {
            double intersectionY = y1 + (y2 - y1) * (borderLeft - x1) / (x2 - x1);
            if (borderBottom <= intersectionY && borderTop >= intersectionY) {
                return new Point2D.Double(borderLeft, intersectionY);
            }
        }
        return null;
    }

    Point2D getIntersectionRight(double x1, double y1, double x2, double y2) {
        if(x1 <= borderRight && x2 >= borderRight || x1 >= borderRight && x2 <= borderRight) {
            double intersectionY = y1 + (y2 - y1) * (borderRight - x1) / (x2 - x1);
            if(borderBottom <= intersectionY && borderTop >= intersectionY) {
                return new Point2D.Double(borderRight, intersectionY);
            }
        }
        return null;
    }

    Point2D getIntersectionBottom(double x1, double y1, double x2, double y2) {
        if(y1 <= borderBottom && y2 >= borderBottom || y1 >= borderBottom && y2 <= borderBottom) {
            double intersectionX = x1 + (x2 - x1) * (borderBottom - y1) / (y2 - y1);
            if(borderLeft <= intersectionX && borderRight >= intersectionX) {
                return new Point2D.Double(intersectionX, borderBottom);
            }
        }
        return null;
    }

    Point2D getIntersectionTop(double x1, double y1, double x2, double y2) {
        if(y1 <= borderTop && y2 >= borderTop || y1 >= borderTop && y2 <= borderTop) {
            double intersectionX = x1 + (x2 - x1) * (borderTop - y1) / (y2 - y1);
            if(borderLeft <= intersectionX && borderRight >= intersectionX) {
                return new Point2D.Double(intersectionX, borderTop);
            }
        }
        return null;
    }

    PointPosition determinePosition(double x, double y) {
        if(x > borderLeft && x < borderRight && y > borderBottom && y < borderTop) {
            return PointPosition.INSIDE;
        } else if(x == borderLeft || x == borderRight || y == borderBottom || y == borderTop) {
            return PointPosition.ON_BORDER;
        } else {
            return PointPosition.OUTSIDE;
        }
    }

    public enum PointPosition {
        INSIDE, OUTSIDE, ON_BORDER
    }

}
