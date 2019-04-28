package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// This class is not implemented yet.
public class SinglePlayerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player_menu);
    }
    public void openCampaignMenu(View view) {
        Intent intent = new Intent(this, CampaignMenu.class);
        startActivity(intent);
    }
    public void openTestArenaMenu(View view) {
        Intent intent = new Intent(this, TestArenaMenu.class);
        startActivity(intent);
    }
}
