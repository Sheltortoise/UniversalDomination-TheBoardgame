package com.sheltoidusa.universaldominationtheboardgame;

import android.support.v7.app.AppCompatActivity;

public class Plot extends AppCompatActivity {
    // Name of the Plot
    public String name;
    // Type of Plot(Space, Full Land Plot, Partial Land Plot)
    public String plotType;
    // Plot Occupancy
    public int points = 0;

    // Location of Plot's First Position
    public float xPos1;
    public float yPos1;
    // Location of Plot's First Position
    public float xPos2;
    public float yPos2;
    // Location of Plot's First Position
    public float xPos3;
    public float yPos3;
    // Location of Plot's First Position
    public float xPos4;
    public float yPos4;

    // Explicit Constructor
    public Plot(String n, String type, int pts, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        name = n;
        plotType = type;
        points = pts;

        xPos1 = x1;
        yPos1 = y1;

        xPos2 = x2;
        yPos2 = y2;

        xPos3 = x3;
        yPos3 = y3;

        xPos4 = x4;
        yPos4 = y4;
    }
    // Sets Plot's Position
    public void positionPlot(float plotX, float plotY, int i, int j) {
        xPos1 = (i * plotX);
        yPos1 = ((.95f * j) * plotY);

        xPos2 = ((i * plotX) + (.5f * plotX));
        yPos2 = ((.95f * j) * plotY);

        xPos3 = (i * plotX);
        yPos3 = (((.95f * j) * plotY) + (.5f * plotY));

        xPos4 = ((i * plotX) + (.5f * plotX));
        yPos4 = (((.95f * j) * plotY) + (.5f * plotY));
    }
    // Updates Plot's Scaled Location
    public void scalePlot(float xShift, float yShift) {
        xPos1 += xShift;
        yPos1 += yShift;

        xPos2 += xShift;
        yPos2 += yShift;

        xPos3 += xShift;
        yPos3 += yShift;

        xPos4 += xShift;
        yPos4 += yShift;
    }
}
