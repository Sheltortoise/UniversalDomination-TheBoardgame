package com.sheltoidusa.universaldominationtheboardgame;

import android.support.v7.app.AppCompatActivity;

// This class is not implemented yet.
public class ResourceMound extends AppCompatActivity {
    // ResourceMound's Location
    public String positionCode;

    // ResourceMound's X Location
    public float xPosition;

    // ResourceMound's Y Location
    public float yPosition;

    // Explicit Constructor
    public ResourceMound(String posCode, float xPos, float yPos) {
        positionCode = posCode;
        xPosition = xPos;
        yPosition = yPos;
    }
}
