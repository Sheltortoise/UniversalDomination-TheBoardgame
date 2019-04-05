package com.sheltoidusa.universaldominationtheboardgame;

public class Plot {
    // Name of the Plot
    public String name;
    // Type of Plot(Space, Full Land Plot, Partial Land Plot)
    public String plotType;

    // Location of Plot
    public float xPos;
    public float yPos;

    // Size of Plot
    public float xScale;
    public float yScale;

    // Explicit Constructor
    public Plot(String n, String type, float x, float y, float l, float w) {
        n = name;
        type = plotType;

        x = xPos;
        y = yPos;

        l = xScale;
        w = yScale;
    }
    // Updates Plot's Location
    public void reposition(float xShift, float yShift) {
        xPos += xShift;
        yPos += yShift;
    }
    // Updates Plot's Size
    public void alterScale(float scalar) {
        scalar *= .05;
        xScale = scalar;
        yScale = scalar;
    }
}
