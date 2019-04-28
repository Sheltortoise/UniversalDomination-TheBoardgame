package com.sheltoidusa.universaldominationtheboardgame;

// The purpose of this Java file is to control positions across the map.
public class VirtualMaps {
    private int[][] plotCodex;
    private Plot[][] virtualPlots;

    private int[][] territoryCodex;
    private Territory[] virtualTerritories;

    private int[][] planetarySystemCodex;
    private PlanetarySystem[] virtualPlanetarySystems;

    public VirtualMaps() {
        // Initialize Plots: 1"x1" Squares, determines their traversability.
        int[][] tempPlotsCodex = {
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 9, 9, 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 0, 0, 0, 9, 9, 9},
                {9, 9, 0, 2, 7, 7, 7, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 7, 7, 7, 7, 2, 9, 9},
                {9, 0, 7, 7, 7, 7, 7, 7, 0, 9, 9, 9, 9, 0, 0, 0, 0, 9, 9, 9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 9},
                {9, 0, 7, 7, 7, 7, 7, 7, 2, 9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 9},
                {9, 0, 7, 7, 7, 7, 7, 7, 0, 9, 9, 2, 7, 7, 7, 7, 7, 7, 7, 9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 9},
                {9, 0, 7, 7, 7, 7, 7, 7, 0, 9, 0, 7, 7, 7, 7, 7, 7, 7, 7, 0, 9, 2, 7, 7, 7, 7, 7, 7, 0, 9},
                {9, 9, 0, 7, 7, 7, 7, 2, 9, 9, 0, 7, 7, 7, 7, 7, 7, 7, 7, 2, 9, 9, 0, 7, 7, 7, 7, 0, 9, 9},
                {9, 9, 9, 2, 0, 0, 0, 9, 9, 9, 2, 7, 7, 7, 7, 7, 7, 7, 7, 0, 9, 9, 9, 2, 0, 0, 2, 9, 9, 9},
                {9, 0, 2, 9, 9, 9, 9, 9, 9, 9, 0, 7, 7, 7, 7, 7, 7, 7, 7, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 0, 2, 9, 9, 9, 9, 9, 9, 9, 9, 2, 7, 7, 7, 7, 7, 7, 2, 9, 9, 0, 2, 0, 9, 9, 9, 2, 0, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 9, 9, 2, 0, 0, 9, 9, 9, 2, 0, 9},
                {9, 9, 9, 2, 0, 0, 9, 9, 9, 9, 9, 9, 9, 0, 2, 0, 2, 9, 9, 9, 9, 0, 2, 0, 9, 9, 9, 9, 9, 9},
                {9, 9, 7, 7, 7, 7, 7, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 0, 2, 0, 9, 9},
                {9, 0, 7, 7, 7, 7, 7, 2, 9, 9, 9, 9, 9, 9, 9, 9, 0, 0, 2, 7, 0, 9, 9, 0, 2, 7, 7, 7, 0, 9},
                {9, 2, 7, 7, 7, 7, 7, 0, 9, 9, 0, 0, 7, 7, 2, 7, 7, 7, 7, 7, 7, 0, 9, 0, 7, 7, 7, 7, 2, 9},
                {9, 0, 7, 7, 7, 7, 7, 2, 9, 9, 2, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 2, 9, 2, 7, 7, 7, 7, 0, 9},
                {9, 9, 7, 7, 7, 7, 7, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 9, 0, 7, 7, 7, 7, 0, 9},
                {9, 9, 9, 0, 0, 0, 9, 9, 9, 9, 0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 9, 9, 9, 0, 0, 0, 0, 9, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}
        };
        plotCodex = tempPlotsCodex;

        virtualPlots = new Plot[30][20];

        // Initialize Territories: Combination of Plots, for later use.
        int[][] tempTerritoryCodex = {
                {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100},
                {100, 100, 100, 0  , 1  , 1  , 2  , 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 39 , 39 , 40 , 40 , 100, 100, 100},
                {100, 100, 0  , 0  , 1  , 1  , 2  , 3  , 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 38 , 39 , 39 , 40 , 40 , 40 , 100, 100},
                {100, 0  , 0  , 1  , 1  , 2  , 2  , 3  , 3  , 100, 100, 100, 100, 49 , 49 , 50 , 50 , 100, 100, 100, 100, 38 , 38 , 39 , 39 , 41 , 41 , 41 , 42 , 100},
                {100, 4  , 4  , 4  , 5  , 5  , 2  , 6  , 6  , 100, 100, 48 , 48 , 49 , 49 , 50 , 50 , 50 , 51 , 100, 100, 38 , 38 , 38 , 44 , 41 , 42 , 42 , 42 , 100},
                {100, 4  , 7  , 4  , 5  , 5  , 5  , 6  , 6  , 100, 100, 48 , 48 , 49 , 54 , 54 , 54 , 50 , 51 , 100, 100, 43 , 43 , 44 , 44 , 44 , 45 , 45 , 45 , 100},
                {100, 7  , 7  , 8  , 9  , 9  , 9  , 9  , 6  , 100, 52 , 52 , 53 , 53 , 53 , 54 , 55 , 55 , 51 , 51 , 100, 43 , 43 , 46 , 44 , 44 , 45 , 45 , 45 , 100},
                {100, 100, 7  , 8  , 8  , 9  , 10 , 10 , 100, 100, 52 , 52 , 53 , 53 , 56 , 56 , 55 , 55 , 55 , 55 , 100, 100, 43 , 46 , 46 , 47 , 47 , 47 , 100, 100},
                {100, 100, 100, 8  , 8  , 10 , 10 , 100, 100, 100, 52 , 52 , 56 , 56 , 56 , 56 , 57 , 57 , 58 , 58 , 100, 100, 100, 46 , 46 , 47 , 47 , 100, 100, 100},
                {100, 11 , 11 , 100, 100, 100, 100, 100, 100, 100, 59 , 59 , 60 , 56 , 56 , 57 , 57 , 57 , 58 , 58 , 100, 100, 100, 100, 100, 100, 100, 100, 100, 100},
                {100, 11 , 11 , 100, 100, 100, 100, 100, 100, 100, 100, 59 , 60 , 60 , 61 , 61 , 57 , 57 , 58 , 100, 100, 63 , 63 , 63 , 100, 100, 100, 30 , 30 , 100},
                {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 60 , 60 , 61 , 61 , 61 , 62 , 62 , 62 , 100, 100, 63 , 63 , 63 , 100, 100, 100, 30 , 30 , 100},
                {100, 100, 100, 12 , 12 , 12 , 100, 100, 100, 100, 100, 100, 100, 61 , 61 , 61 , 62 , 100, 100, 100, 100, 63 , 63 , 63 , 100, 100, 100, 100, 100, 100},
                {100, 100, 13 , 14 , 12 , 15 , 16 , 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 31 , 32 , 33 , 33 , 100, 100},
                {100, 13 , 13 , 14 , 15 , 15 , 16 , 16 , 100, 100, 100, 100, 100, 100, 100, 100, 23 , 23 , 24 , 24 , 24 , 100, 100, 31 , 31 , 32 , 33 , 33 , 33 , 100},
                {100, 17 , 14 , 14 , 15 , 15 , 16 , 16 , 100, 100, 21 , 21 , 21 , 22 , 22 , 22 , 23 , 23 , 25 , 25 , 24 , 24 , 100, 31 , 32 , 32 , 35 , 36 , 36 , 100},
                {100, 17 , 17 , 17 , 19 , 19 , 20 , 20 , 100, 100, 21 , 21 , 21 , 22 , 22 , 22 , 23 , 23 , 25 , 25 , 25 , 29 , 100, 34 , 34 , 35 , 35 , 36 , 36 , 100},
                {100, 100, 18 , 18 , 19 , 20 , 20 , 100, 100, 100, 26 , 26 , 21 , 22 , 27 , 27 , 27 , 28 , 28 , 28 , 29 , 29 , 100, 34 , 34 , 35 , 35 , 37 , 37 , 100},
                {100, 100, 100, 18 , 19 , 20 , 100, 100, 100, 100, 26 , 26 , 26 , 26 , 27 , 27 , 28 , 28 , 28 , 28 , 29 , 100, 100, 100, 34 , 35 , 37 , 37 , 100, 100},
                {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}
        };
        territoryCodex = tempTerritoryCodex;

        virtualTerritories = new Territory[64];

        // Initialize Planetary Systems
        int[][] tempPlanetarySystemCodex = {
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 9, 9, 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 4, 4, 4, 4, 4, 9, 9},
                {9, 9, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 4, 4, 4, 4, 4, 4, 9, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 5, 5, 5, 5, 9, 9, 9, 9, 4, 4, 4, 4, 4, 4, 4, 4, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 4, 4, 4, 4, 4, 4, 4, 4, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 4, 4, 4, 4, 4, 4, 4, 4, 9},
                {9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 4, 4, 4, 4, 4, 4, 4, 4, 9},
                {9, 9, 0, 0, 0, 0, 0, 0, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 4, 4, 4, 4, 4, 4, 9, 9},
                {9, 9, 9, 0, 0, 0, 0, 9, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 9, 4, 4, 4, 4, 9, 9, 9},
                {9, 0, 0, 9, 9, 9, 9, 9, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {9, 0, 0, 9, 9, 9, 9, 9, 9, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 5, 5, 5, 9, 9, 9, 3, 3, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 5, 5, 5, 5, 5, 5, 5, 5, 9, 9, 5, 5, 5, 9, 9, 9, 3, 3, 9},
                {9, 9, 9, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 5, 5, 5, 5, 9, 9, 9, 9, 5, 5, 5, 9, 9, 9, 9, 9, 9},
                {9, 9, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 3, 3, 3, 3, 9, 9},
                {9, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 9, 9, 3, 3, 3, 3, 3, 3, 9},
                {9, 1, 1, 1, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 3, 3, 3, 3, 3, 3, 9},
                {9, 1, 1, 1, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 3, 3, 3, 3, 3, 3, 9},
                {9, 9, 1, 1, 1, 1, 1, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 3, 3, 3, 3, 3, 3, 9},
                {9, 9, 9, 1, 1, 1, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 9, 9, 9, 3, 3, 3, 3, 9, 9},
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}
        };
        planetarySystemCodex = tempPlanetarySystemCodex;

        virtualPlanetarySystems = new PlanetarySystem[6];
    }
    // Sets the data for a plot to hold.
    public void initialize(float plotX, float plotY) {
        // String to adhere plot type to each plot in Plots[30][20]
        String plotType = "";

        // Initialize Plots
        for(int i = 0; i < 30; i++) {
            for(int j = 0; j < 20; j++) {
                if(plotCodex[j][i] == 9) {
                    plotType = "Space";
                }
                else if(plotCodex[j][i] == 0) {
                    plotType = "Full Land Plot";
                }
                else if(plotCodex[j][i] == 7){
                    plotType = "Partial Land Plot";
                }
                else if(plotCodex[j][i] == 2) {
                    plotType = "Portal";
                }
                Plot plot = new Plot(i + "-" + j, plotType, 0,
                        (i * plotX), ((.95f * j) * plotY),
                        ((i * plotX) + (.5f * plotX)), ((.95f * j) * plotY),
                        (i * plotX), (((.95f * j) * plotY) + (.5f * plotY)),
                        ((i * plotX) + (.5f * plotX)), (((.95f * j) * plotY) + (.5f * plotY)));

                virtualPlots[i][j] = plot;
            }
        }
        // Not Implemented Yet
        /*
        // Initialize Territories
        for(int a = 0; a < 64; a++) {
            for(int i = 0; i < 30; i++) {
                for(int j = 0; j < 20; j++) {
                    String tempString = virtualPlots[i][j].name;

                    if(territoryCodex[j][i] == a) {
                        Territory tempTerritory = new Territory("", tempString);

                        virtualTerritories[a] = tempTerritory;
                    }
                }
            }
        }
        // Instantiate Planetary Systems
        for(int a = 0; a < 6; a++) {
            for(int i = 0; i < 30; i++) {
                for(int j = 0; j < 20; j++) {
                    String tempString = virtualPlots[i][j].name;

                    if(planetarySystemCodex[j][i] == a) {
                        virtualPlanetarySystems[a].territoriesInPlanterySystem.add(tempString);
                    }
                }
            }
        }
        */
    }
    // Getters
    public Plot[][] getVirtualPlots() {
        return virtualPlots;
    }
    public Territory[] getVirtualTerritories() {
        return virtualTerritories;
    }
    public PlanetarySystem[] getVirtualPlanetarySystems() {
        return virtualPlanetarySystems;
    }
}