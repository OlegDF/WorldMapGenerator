package com.worldmapgenerator.Model;

public class QuadraticEquation {

    private final double a, b, c;
    private Double lesserRoot, greaterRoot;

    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        solve();
    }

    private void solve() {
        double d = b * b - 4 * a * c;
        if(d < 0) {
            lesserRoot = greaterRoot = null;
        } else if(d == 0) {
            lesserRoot = greaterRoot = -b / (2 * a);
        } else {
            lesserRoot = (-b - (float) Math.sqrt(d)) / (2 * a);
            greaterRoot = (-b + (float) Math.sqrt(d)) / (2 * a);
            if(lesserRoot > greaterRoot) {
                double swap = lesserRoot;
                lesserRoot = greaterRoot;
                greaterRoot = swap;
            }
        }
    }

    public Double getLesserRoot() {
        return lesserRoot;
    }

    public Double getGreaterRoot() {
        return greaterRoot;
    }
}
