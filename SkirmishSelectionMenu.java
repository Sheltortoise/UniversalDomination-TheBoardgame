package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;

public class SkirmishSelectionMenu extends AppCompatActivity {
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

    private boolean isAnimusPlayer;
    private boolean isAntibytePlayer;
    private boolean isElementalPlayer;
    private boolean isGrotianPlayer;
    private boolean isHivePlayer;
    private boolean isHumanityPlayer;

    private boolean isAnimusAI;
    private boolean isAntibyteAI;
    private boolean isElementalAI;
    private boolean isGrotianAI;
    private boolean isHiveAI;
    private boolean isHumanityAI;

    private ArrayList teamList = new ArrayList<ArrayList>();
        private ArrayList animusList = new ArrayList<Integer>();
        private ArrayList antibyteList = new ArrayList<Integer>();
        private ArrayList elementalList = new ArrayList<Integer>();
        private ArrayList grotianList = new ArrayList<Integer>();
        private ArrayList hiveList = new ArrayList<Integer>();
        private ArrayList humanityList = new ArrayList<Integer>();

    private int countTeams = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skirmish_selection_menu);

        animusButton = findViewById(R.id.skirmishAnimusButton);
        antibyteButton = findViewById(R.id.skirmishAntibyteButton);
        elementalButton = findViewById(R.id.skirmishElementalButton);
        grotianButton = findViewById(R.id.skirmishGrotianButton);
        hiveButton = findViewById(R.id.skirmishHiveButton);
        humanityButton = findViewById(R.id.skirmishHumanityButton);

        animusSwitch = findViewById(R.id.skirmishAnimusSwitch);
        antibyteSwitch = findViewById(R.id.skirmishAnibyteSwitch);
        elementalSwitch = findViewById(R.id.skirmishElementalSwitch);
        grotianSwitch = findViewById(R.id.skirmishGrotianSwitch);
        hiveSwitch = findViewById(R.id.skirmishHiveSwitch);
        humanitySwitch = findViewById(R.id.skirmishHumanitySwitch);

        animusByeByeButton = findViewById(R.id.byeByeAnimusButton);
        antibyteByeByeButton = findViewById(R.id.byeByeAntibyteButton);
        elementalByeByeButton = findViewById(R.id.byeByeElementalButton);
        grotianByeByeButton = findViewById(R.id.byeByeGrotianButton);
        hiveByeByeButton = findViewById(R.id.byeByeHiveButton);
        humanityByeByeButton = findViewById(R.id.byeByeHumanityButton);

        initializeTeamList();
    }
    public void initializeTeamList() {
        // AI versus Player
        if(animusSwitch.isChecked()) {
            isAnimusAI = true;
            isAnimusPlayer = false;
        }
        else {
            isAnimusPlayer = true;
            isAnimusAI = false;
        }
        if(antibyteSwitch.isChecked()) {
            isAntibyteAI = true;
            isAntibytePlayer = false;
        }
        else {
            isAntibytePlayer = true;
            isAntibyteAI = false;
        }
        if(elementalSwitch.isChecked()) {
            isElementalAI = true;
            isElementalPlayer = false;
        }
        else {
            isElementalPlayer = true;
            isElementalAI = false;
        }
        if(grotianSwitch.isChecked()) {
            isGrotianAI = true;
            isGrotianPlayer = false;
        }
        else {
            isGrotianPlayer = true;
            isGrotianAI = false;
        }
        if(hiveSwitch.isChecked()) {
            isHiveAI = true;
            isHivePlayer = false;
        }
        else {
            isHivePlayer = true;
            isHiveAI = false;
        }
        if(humanitySwitch.isChecked()) {
            isHumanityAI = true;
            isHumanityPlayer = false;
        }
        else {
            isHumanityPlayer = true;
            isHumanityAI = false;
        }
    }
    public void updateTeamList() {
        if(isAnimusAI || isAnimusPlayer) {
            teamList.add(animusList);
        }
        if(isAntibyteAI || isAntibytePlayer) {
            teamList.add(antibyteList);
        }
        if(isElementalAI || isElementalPlayer) {
            teamList.add(elementalList);
        }
        if(isGrotianAI || isGrotianPlayer) {
            teamList.add(grotianList);
        }
        if(isHiveAI || isHivePlayer) {
            teamList.add(hiveList);
        }
        if(isHumanityAI || isHumanityPlayer) {
            teamList.add(humanityList);
        }
    }
    public void toggleRace(View view) {
        if(view == animusByeByeButton) {
            isAnimusAI = false;
            isAnimusPlayer = false;

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
            isAntibyteAI = false;
            isAntibytePlayer = false;

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
            isElementalAI = false;
            isElementalPlayer = false;

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
            isGrotianAI = false;
            isGrotianPlayer = false;

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
            isHiveAI = false;
            isHivePlayer = false;

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
            isHumanityAI = false;
            isHumanityPlayer = false;

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
    public void startSkirmish(View view) {
        updateTeamList();
        openGameBoardBattlefield();
    }
    public void openGameBoardBattlefield() {
        Intent intent = new Intent(this, GameBoardBattlefield.class);

        // Get my data into GameBoardBattlefield
        intent.putExtra("ANIMUS_AI_BOOLEAN", isAnimusAI);
        intent.putExtra("ANTIBYTE_AI_BOOLEAN", isAntibyteAI);
        intent.putExtra("ELEMENTAL_AI_BOOLEAN", isElementalAI);
        intent.putExtra("GROTIAN_AI_BOOLEAN", isGrotianAI);
        intent.putExtra("HIVE_AI_BOOLEAN", isHiveAI);
        intent.putExtra("HUMANITY_AI_BOOLEAN", isHumanityAI);

        intent.putExtra("ANIMUS_PLAYER_BOOLEAN", isAnimusPlayer);
        intent.putExtra("ANTIBYTE_PLAYER_BOOLEAN", isAntibytePlayer);
        intent.putExtra("ELEMENTAL_PLAYER_BOOLEAN", isElementalPlayer);
        intent.putExtra("GROTIAN_PLAYER_BOOLEAN", isGrotianPlayer);
        intent.putExtra("HIVE_PLAYER_BOOLEAN", isHivePlayer);
        intent.putExtra("HUMANITY_PLAYER_BOOLEAN", isHumanityPlayer);

        intent.putExtra("TEAM_LIST", teamList);
            intent.putExtra("ANIMUS_LIST", animusList);
            intent.putExtra("ANTIBYTE_LIST", antibyteList);
            intent.putExtra("ELEMENTAL_LIST", elementalList);
            intent.putExtra("GROTIAN_LIST", grotianList);
            intent.putExtra("HIVE_LIST", hiveList);
            intent.putExtra("HUMANITY_LIST", humanityList);

        startActivity(intent);
    }
}
