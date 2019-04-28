package com.sheltoidusa.universaldominationtheboardgame;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

// This class is not implemented yet.
public class PlanetarySystem extends AppCompatActivity {
    // Name of Planetary System
    public String name;
    // Territories in a Planetary System
    public ArrayList<String> territoriesInPlanterySystem = new ArrayList<String>();

    // Explicit Constructor
    public PlanetarySystem(String n, ArrayList<String> tips) {
        name = n;
        territoriesInPlanterySystem = tips;
    }
}
