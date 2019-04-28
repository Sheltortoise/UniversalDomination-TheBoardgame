package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// This class is for bringing the player from the mulitplayer menu to the next menu.
public class MultiplayerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_menu);
    }
    public void openCoopertiveMenu(View view) {
        Intent intent = new Intent(this, CoopertiveMenu.class);
        startActivity(intent);
    }
    public void openSkirmishSelectionMenu(View view) {
        Intent intent = new Intent(this, SkirmishSelectionMenu.class);
        startActivity(intent);
    }
}
