package com.sheltoidusa.universaldominationtheboardgame;

import android.support.v7.app.AppCompatActivity;

public class Unit extends AppCompatActivity {
    // Unit's Name
    public String name;
    // Unit's Resource Cost
    public int resourceCost;

    // Image's ID
    public int imageID;

    // Unit's Race
    public String species;
    // Unit's Base Mobility
    public int mobility;

    // Unit's Ability
    public String ability;

    // Unit's Position
    public float xPos;
    public float yPos;

    // Unit's Scale
    public float xScale;
    public float yScale;

    //Explicit Constructor
    public Unit(String n, int rc, int id, String s, int m, String a, float x, float y, float l, float w) {
        n = name;
        rc = resourceCost;

        id = imageID;

        s = species;
        m = mobility;

        a = ability;

        x = xPos;
        y = yPos;

        l = xScale;
        w = yScale;
    }
    // Updates Unit's Location
    public void reposition(float xShift, float yShift) {
        xPos += xShift;
        yPos += yShift;
    }
    // Updates Unit's Size
    public void alterScale(float scalar) {
        scalar *= .05;
        xScale = scalar;
        yScale = scalar;
    }
}