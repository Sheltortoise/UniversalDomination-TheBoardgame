package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;

// This class sends the Skirmish parameters to the battlefield to begin playing the game.
public class SkirmishSelectionMenu extends AppCompatActivity {
    // Skirmish Selection Menu Information
    private Button animusButton;
    private Button antibyteButton;
    private Button elementalButton;
    private Button grotianButton;
    private Button hiveButton;
    private Button humanityButton;

    private Switch animusSwitch;
    private Switch antibyteSwitch;
    private Switch elementalSwitch;
    private Switch grotianSwitch;
    private Switch hiveSwitch;
    private Switch humanitySwitch;

    private Button animusByeByeButton;
    private Button antibyteByeByeButton;
    private Button elementalByeByeButton;
    private Button grotianByeByeButton;
    private Button hiveByeByeButton;
    private Button humanityByeByeButton;

    private int countTeams = 6;

    // Players
    private Player animusPlayer = new Player("Animus", 0, 0, null, 100, 1);
    private Player antibytePlayer = new Player("Antibytes", 0, 0, null, 100, 1);
    private Player elementalPlayer = new Player("Elementals", 0, 0, null, 100, 1);
    private Player grotianPlayer = new Player("Grotians", 0, 0, null, 100, 1);
    private Player hivePlayer = new Player("Hive", 0, 0, null, 100, 1);
    private Player humanityPlayer = new Player("Humanity", 0, 0, null, 100, 1);

    // Initial Units
    private ArrayList<Unit> animateList = new ArrayList<Unit>();
    private ArrayList<Unit> antibyteList = new ArrayList<Unit>();
    private ArrayList<Unit> elementalList = new ArrayList<Unit>();
    private ArrayList<Unit> grotianList = new ArrayList<Unit>();
    private ArrayList<Unit> hiveList = new ArrayList<Unit>();
    private ArrayList<Unit> humanityList = new ArrayList<Unit>();

    // First code to be executed.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skirmish_selection_menu);

        // Primary Button Linkage
        animusButton = findViewById(R.id.skirmishAnimusButton);
        antibyteButton = findViewById(R.id.skirmishAntibyteButton);
        elementalButton = findViewById(R.id.skirmishElementalButton);
        grotianButton = findViewById(R.id.skirmishGrotianButton);
        hiveButton = findViewById(R.id.skirmishHiveButton);
        humanityButton = findViewById(R.id.skirmishHumanityButton);

        // Primary Switch Linkage
        animusSwitch = findViewById(R.id.skirmishAnimusSwitch);
        antibyteSwitch = findViewById(R.id.skirmishAnibyteSwitch);
        elementalSwitch = findViewById(R.id.skirmishElementalSwitch);
        grotianSwitch = findViewById(R.id.skirmishGrotianSwitch);
        hiveSwitch = findViewById(R.id.skirmishHiveSwitch);
        humanitySwitch = findViewById(R.id.skirmishHumanitySwitch);

        // Secondary Button Linkage
        animusByeByeButton = findViewById(R.id.byeByeAnimusButton);
        antibyteByeByeButton = findViewById(R.id.byeByeAntibyteButton);
        elementalByeByeButton = findViewById(R.id.byeByeElementalButton);
        grotianByeByeButton = findViewById(R.id.byeByeGrotianButton);
        hiveByeByeButton = findViewById(R.id.byeByeHiveButton);
        humanityByeByeButton = findViewById(R.id.byeByeHumanityButton);
    }
    // Activated by "Start" Button: Determines Player Types
    public void initializePlayerTypes() {
        // Is the Animus controlled by a Player[1], AI[2], or Empty[0]?
        if(animusSwitch.getAlpha() == 0) {
            animusPlayer.playerType = 0;
        }
        else if(animusSwitch.isChecked()) {
            animusPlayer.playerType = 2;
        }
        else {
            animusPlayer.playerType = 1;
        }
        // Are the Antibytes controlled by a Player[1], AI[2], or Empty[0]?
        if(antibyteSwitch.getAlpha() == 0) {
            antibytePlayer.playerType = 0;
        }
        else if(antibyteSwitch.isChecked()) {
            antibytePlayer.playerType = 2;
        }
        else {
            antibytePlayer.playerType = 1;
        }
        // Are the Elementals controlled by a Player[1], AI[2], or Empty[0]?
        if(elementalSwitch.getAlpha() == 0) {
            elementalPlayer.playerType = 0;
        }
        else if(elementalSwitch.isChecked()) {
            elementalPlayer.playerType = 2;
        }
        else {
            elementalPlayer.playerType = 1;
        }
        // Are the Grotians controlled by a Player[1], AI[2], or Empty[0]?
        if(grotianSwitch.getAlpha() == 0) {
            grotianPlayer.playerType = 0;
        }
        else if(grotianSwitch.isChecked()) {
            grotianPlayer.playerType = 2;
        }
        else {
            grotianPlayer.playerType = 1;
        }
        // Is the Hive controlled by a Player[1], AI[2], or Empty[0]?
        if(hiveSwitch.getAlpha() == 0) {
            hivePlayer.playerType = 0;
        }
        else if(hiveSwitch.isChecked()) {
            hivePlayer.playerType = 2;
        }
        else {
            hivePlayer.playerType = 1;
        }
        // Is Humanity controlled by a Player[1], AI[2], or Empty[0]?
        if(humanitySwitch.getAlpha() == 0) {
            humanityPlayer.playerType = 0;
        }
        else if(humanitySwitch.isChecked()) {
            humanityPlayer.playerType = 2;
        }
        else {
            humanityPlayer.playerType = 1;
        }
    }
    // Activated by "Start" Button: Updates the Unit Lists to the Player Types
    public void initializeUnitLists() {
        // Gives the Animus Player Starter Units, and sets up their data and positions
        if(animusPlayer.playerType != 0) {
            Unit base = new Unit("The Tribute", 0, R.drawable.the_tribute, "Animate", 0, "Consumption[10]", 12,  4, "5-14");
            animateList.add(base);

            for(int i = 0; i < 6; i++) {
                if (i < 3) {
                    Unit unit = new Unit("Zombie", 2, R.drawable.zombie, "Animate", 2, "Ambush", 6, 1, "5-15");
                    animateList.add(unit);
                }
                else {
                    Unit unit = new Unit("Zombie", 2, R.drawable.zombie, "Animate", 2, "Ambush", 6, 1, "5-13");
                    animateList.add(unit);
                }
            }
            animusPlayer.unitList = animateList;
        }
        // Gives the Antibyte Player Starter Units
        if(antibytePlayer.playerType != 0) {
            Unit base = new Unit("The Cornucopia", 0, R.drawable.the_cornucopia, "Antibyte", 0, "Consumption[10]", 12, 4,  "26-16");
            antibyteList.add(base);

            for(int i = 0; i < 4; i++) {
                Unit unit = new Unit("SPK Drone", 3, R.drawable.spk_drone, "Antibyte", 3, "Ambush", 0, 1, "25-16");

                antibyteList.add(unit);
            }
            antibytePlayer.unitList = antibyteList;
        }
        // Gives the Elemental Player Starter Units
        if(elementalPlayer.playerType != 0) {
            Unit base = new Unit("The Spire", 0, R.drawable.the_spire, "Elemental", 0, "Consumption[10]", 12, 4, "24-5");
            elementalList.add(base);

            for(int i = 0; i < 3; i++) {
                Unit unit = new Unit("Aerode", 4, R.drawable.aerode, "Elemental", 4, "Blitzkrieg", 6, 1, "24-6");

                elementalList.add(unit);
            }
            elementalPlayer.unitList = elementalList;
        }
        // Gives the Grotian Player Starter Units
        if(grotianPlayer.playerType != 0) {
            Unit base = new Unit("The Lair", 0, R.drawable.the_lair, "Grotians", 0, "Consumption[10]", 12, 4, "14-8");
            grotianList.add(base);

            for(int i = 0; i < 3; i++) {
                Unit unit = new Unit("Goblin", 4, R.drawable.goblin, "Grotian", 4, "Blitzkrieg", 6, 1, "13-8");

                grotianList.add(unit);
            }
            grotianPlayer.unitList = grotianList;
        }
        // Gives the Hive Player Starter Units
        if(hivePlayer.playerType != 0) {
            Unit base = new Unit("The Colony", 0, R.drawable.the_colony, "Hivian", 0, "Consumption[10]", 12, 4, "5-4");
            hiveList.add(base);

            for(int i = 0; i < 6; i++) {
                if(i < 3) {
                    Unit unit = new Unit("Aent", 2, R.drawable.aent, "Hivian", 2, "Ambush", 6, 1, "5-5");
                    hiveList.add(unit);
                }
                else {
                    Unit unit = new Unit("Aent", 2, R.drawable.aent, "Hivian", 2, "Ambush", 6, 1, "4-4");
                    hiveList.add(unit);
                }
            }
            hivePlayer.unitList = hiveList;
        }
        // Gives the Human Player Starter Units
        if(humanityPlayer.playerType != 0) {
            Unit base = new Unit("The Ark", 0, R.drawable.the_ark, "Human", 0, "Consumption[10]", 12, 4, "14-16");
            humanityList.add(base);

            for(int i = 0; i < 4; i++) {
                Unit unit = new Unit("Soldier", 3, R.drawable.soldier, "Human", 3, "Blitzkrieg", 6, 1, "15-16");

                humanityList.add(unit);
            }
            humanityPlayer.unitList = humanityList;
        }
    }
    // Activated by "X or <" Button: Toggles Alpha of XML Widgets. This will deactivate a race for the battlefield so that less than 6 players can play.
    public void toggleRace(View view) {
        if(view == animusByeByeButton) {
            if(animusSwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                animusButton.setAlpha(0);
                animusSwitch.setAlpha(0);

                animusByeByeButton.setBackgroundResource(R.drawable.backward_button);
                animusByeByeButton.setText(" ");
            }
            else if(animusSwitch.getAlpha() == 0) {
                countTeams++;

                animusButton.setAlpha(1);
                animusSwitch.setAlpha(1);

                animusByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                animusByeByeButton.setText("X");
            }
        }
        else if(view == antibyteByeByeButton) {
            if(antibyteSwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                antibyteButton.setAlpha(0);
                antibyteSwitch.setAlpha(0);

                antibyteByeByeButton.setBackgroundResource(R.drawable.backward_button);
                antibyteByeByeButton.setText(" ");
            }
            else if(antibyteSwitch.getAlpha() == 0) {
                countTeams++;

                antibyteButton.setAlpha(1);
                antibyteSwitch.setAlpha(1);

                antibyteByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                antibyteByeByeButton.setText("X");
            }
        }
        else if(view == elementalByeByeButton) {
            if(elementalSwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                elementalButton.setAlpha(0);
                elementalSwitch.setAlpha(0);

                elementalByeByeButton.setBackgroundResource(R.drawable.backward_button);
                elementalByeByeButton.setText(" ");
            }
            else if(elementalSwitch.getAlpha() == 0) {
                countTeams++;

                elementalButton.setAlpha(1);
                elementalSwitch.setAlpha(1);

                elementalByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                elementalByeByeButton.setText("X");
            }
        }
        else if(view == grotianByeByeButton) {
            if(grotianSwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                grotianButton.setAlpha(0);
                grotianSwitch.setAlpha(0);

                grotianByeByeButton.setBackgroundResource(R.drawable.backward_button);
                grotianByeByeButton.setText(" ");
            }
            else if(grotianSwitch.getAlpha() == 0) {
                countTeams++;

                grotianButton.setAlpha(1);
                grotianSwitch.setAlpha(1);

                grotianByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                grotianByeByeButton.setText("X");
            }
        }
        else if(view == hiveByeByeButton) {
            if(hiveSwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                hiveButton.setAlpha(0);
                hiveSwitch.setAlpha(0);

                hiveByeByeButton.setBackgroundResource(R.drawable.backward_button);
                hiveByeByeButton.setText(" ");
            }
            else if(hiveSwitch.getAlpha() == 0) {
                countTeams++;

                hiveButton.setAlpha(1);
                hiveSwitch.setAlpha(1);

                hiveByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                hiveByeByeButton.setText("X");
            }
        }
        else if(view == humanityByeByeButton) {
            if(humanitySwitch.getAlpha() != 0 && countTeams > 2) {
                countTeams--;

                humanityButton.setAlpha(0);
                humanitySwitch.setAlpha(0);

                humanityByeByeButton.setBackgroundResource(R.drawable.backward_button);
                humanityByeByeButton.setText(" ");
            }
            else if(humanitySwitch.getAlpha() == 0) {
                countTeams++;

                humanityButton.setAlpha(1);
                humanitySwitch.setAlpha(1);

                humanityByeByeButton.setBackgroundResource(R.drawable.button_light_gradient_small);
                humanityByeByeButton.setText("X");
            }
        }
    }
    // Activated by "Start" Button: Calls All Local Methods
    public void startSkirmish(View view) {
        initializePlayerTypes();
        initializeUnitLists();

        openGameBoardBattlefield();
    }
    // Opens the Battlefield and Passes Players to the Battlefield to be used.
    public void openGameBoardBattlefield() {
        Intent intent = new Intent(this, GameBoardBattlefield.class);

        // Pass Player.playerType into GameBoardBattlefield
        intent.putExtra("ANIMUS_PLAYER", animusPlayer);
        intent.putExtra("ANTIBYTE_PLAYER", antibytePlayer);
        intent.putExtra("ELEMENTAL_PLAYER", elementalPlayer);
        intent.putExtra("GROTIAN_PLAYER", grotianPlayer);
        intent.putExtra("HIVE_PLAYER", hivePlayer);
        intent.putExtra("HUMANITY_PLAYER", humanityPlayer);

        // Clears the backpath of my intents, and pushes the app into the battlefield.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
