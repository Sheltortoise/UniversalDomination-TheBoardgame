package com.sheltoidusa.universaldominationtheboardgame;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

// This class is not implemented yet
public class Territory extends AppCompatActivity {
    // Name of the Territory
    public String name;
    // Plots in a Territory
    public String plotsInTerritory;

    // Explicit Constructor
    public Territory(String n, String pit) {
        name = n;
        plotsInTerritory = pit;
    }
}
