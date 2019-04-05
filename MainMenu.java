package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }
    // openSinglePlayerMenu(...): Makes the class "SinglePlayerMenu" trigger.
    public void openSinglePlayerMenu(View view) {
        Intent intent = new Intent(this, SinglePlayerMenu.class);
        startActivity(intent);
    }
    // openMultiplayerMenu(...): Makes the class "MultiplayerMenu" trigger.
    public void openMultiplayerMenu(View view) {
        Intent intent = new Intent(this, MultiplayerMenu.class);
        startActivity(intent);
    }
    // openRuleBook(...): Makes the class "RuleBook" trigger.
    public void openRuleBook(View view) {
        Intent intent = new Intent(this, RuleBook.class);
        startActivity(intent);
    }
    // openOptionsMenu(...): Makes the class "OptionsMenu" trigger.
    public void openOptionsMenu(View view) {
        Intent intent = new Intent(this, OptionsMenu.class);
        startActivity(intent);
    }
    // openCredits(...): Makes the class "credits" trigger.
    public void openCredits(View view) {
        Intent intent = new Intent(this, Credits.class);
        startActivity(intent);
    }
}
