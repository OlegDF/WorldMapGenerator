package com.worldmapgenerator.Model;

public class QuadraticEquation {

    private final float a, b, c;
    private Float lesserRoot, greaterRoot;

    public QuadraticEquation(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
        solve();
    }

    private void solve() {
        float d = b * b - 4 * a * c;
        if(d < 0) {
            lesserRoot = greaterRoot = null;
        } else if(d == 0) {
            lesserRoot = greaterRoot = -b / (2 * a);
        } else {
            lesserRoot = (-b - (float) Math.sqrt(d)) / (2 * a);
            greaterRoot = (-b + (float) Math.sqrt(d)) / (2 * a);
            if(lesserRoot > greaterRoot) {
                float swap = lesserRoot;
                lesserRoot = greaterRoot;
                greaterRoot = swap;
            }
        }
    }

    public Float getLesserRoot() {
        return lesserRoot;
    }

    public Float getGreaterRoot() {
        return greaterRoot;
    }
}
