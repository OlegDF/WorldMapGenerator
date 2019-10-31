package com.worldmapgenerator.Model.DiagramStructure;

import java.awt.geom.Point2D;

/**
 * Ограничительная рамка - прямоугольник вокруг диаграммы со сторонами, параллельными осям x и y
 */
public class Border {

    public final double borderLeft, borderRight, borderBottom, borderTop;

    public Border(double borderLeft, double borderBottom, double borderRight, double borderTop) {
        if (borderRight < borderLeft) {
            final double swap = borderRight;
            borderRight = borderLeft;
            borderLeft = swap;
        }
        if (borderTop < borderBottom) {
            final double swap = borderTop;
            borderTop = borderBottom;
            borderBottom = swap;
        }
        this.borderLeft = borderLeft;
        this.borderRight = borderRight;
        this.borderBottom = borderBottom;
        this.borderTop = borderTop;
    }

    /**
     * Получает пересечение между данным отрезком и левой стороной ограничительной рамки, если оно существует.
     *
     * @param x1 - координата x первой вершины отрезка
     * @param y1 - координата y первой вершины отрезка
     * @param x2 - координата x второй вершины отрезка
     * @param y2 - координата y второй вершины отрезка
     * @return координаты x, y пересечения в виде Point2D или null
     */
    Point2D getIntersectionLeft(final double x1, final double y1, final double x2, final double y2) {
        if (x1 <= borderLeft && x2 >= borderLeft || x1 >= borderLeft && x2 <= borderLeft) {
            final double intersectionY = y1 + (y2 - y1) * (borderLeft - x1) / (x2 - x1);
            if (borderBottom <= intersectionY && borderTop >= intersectionY) {
                return new Point2D.Double(borderLeft, intersectionY);
            }
        }
        return null;
    }

    /**
     * Получает пересечение между данным отрезком и правой стороной ограничительной рамки, если оно существует.
     *
     * @param x1 - координата x первой вершины отрезка
     * @param y1 - координата y первой вершины отрезка
     * @param x2 - координата x второй вершины отрезка
     * @param y2 - координата y второй вершины отрезка
     * @return координаты x, y пересечения в виде Point2D или null
     */
    Point2D getIntersectionRight(final double x1, final double y1, final double x2, final double y2) {
        if (x1 <= borderRight && x2 >= borderRight || x1 >= borderRight && x2 <= borderRight) {
            final double intersectionY = y1 + (y2 - y1) * (borderRight - x1) / (x2 - x1);
            if (borderBottom <= intersectionY && borderTop >= intersectionY) {
                return new Point2D.Double(borderRight, intersectionY);
            }
        }
        return null;
    }

    /**
     * Получает пересечение между данным отрезком и нижней стороной ограничительной рамки, если оно существует.
     *
     * @param x1 - координата x первой вершины отрезка
     * @param y1 - координата y первой вершины отрезка
     * @param x2 - координата x второй вершины отрезка
     * @param y2 - координата y второй вершины отрезка
     * @return координаты x, y пересечения в виде Point2D или null
     */
    Point2D getIntersectionBottom(final double x1, final double y1, final double x2, final double y2) {
        if (y1 <= borderBottom && y2 >= borderBottom || y1 >= borderBottom && y2 <= borderBottom) {
            final double intersectionX = x1 + (x2 - x1) * (borderBottom - y1) / (y2 - y1);
            if (borderLeft <= intersectionX && borderRight >= intersectionX) {
                return new Point2D.Double(intersectionX, borderBottom);
            }
        }
        return null;
    }

    /**
     * Получает пересечение между данным отрезком и верхней стороной ограничительной рамки, если оно существует.
     *
     * @param x1 - координата x первой вершины отрезка
     * @param y1 - координата y первой вершины отрезка
     * @param x2 - координата x второй вершины отрезка
     * @param y2 - координата y второй вершины отрезка
     * @return координаты x, y пересечения в виде Point2D или null
     */
    Point2D getIntersectionTop(final double x1, final double y1, final double x2, final double y2) {
        if (y1 <= borderTop && y2 >= borderTop || y1 >= borderTop && y2 <= borderTop) {
            final double intersectionX = x1 + (x2 - x1) * (borderTop - y1) / (y2 - y1);
            if (borderLeft <= intersectionX && borderRight >= intersectionX) {
                return new Point2D.Double(intersectionX, borderTop);
            }
        }
        return null;
    }

    /**
     * Определяет, находится ли данная точка внутри, на грани или вне ограничительной рамки.
     *
     * @param x - координата x точки
     * @param y - координата y точки
     * @return вид расположения точки в виде enum
     */
    PointPosition determinePosition(final double x, final double y) {
        if (x > borderLeft && x < borderRight && y > borderBottom && y < borderTop) {
            return PointPosition.INSIDE;
        } else if (x == borderLeft || x == borderRight || y == borderBottom || y == borderTop) {
            return PointPosition.ON_BORDER;
        } else {
            return PointPosition.OUTSIDE;
        }
    }

    public enum PointPosition {
        INSIDE, OUTSIDE, ON_BORDER
    }

}
