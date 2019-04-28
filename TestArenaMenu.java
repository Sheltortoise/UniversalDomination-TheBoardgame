package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// This class is not Implemented Yet
public class TestArenaMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstatanceState) {
        super.onCreate(savedInstatanceState);
        setContentView(R.layout.test_arena_menu);
    }
    public void openTestArena(View view) {
        Intent intent = new Intent(this, TestArenaMenu.class);
        startActivity(intent);
    }
}
