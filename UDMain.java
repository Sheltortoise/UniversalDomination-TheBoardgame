package com.sheltoidusa.universaldominationtheboardgame;

// Standard Importation Process: Import all necessary android files to utilize the library.
// Alphabetized for quick reference!
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// Extend AppCompatActivity: Necessary to access hidden code.
public class UDMain extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    // onCreate(...); : When the app is loaded, this method will trigger.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super.onCreate(...): This accesses the super class to create a local save state.
        super.onCreate(savedInstanceState);

        // setContentView(...): Makes the XML "ud_main" take focus.
        setContentView(R.layout.ud_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.universal_domination_theme);
        playSound();
    }
    // openMainMenu(...): Makes the class "MainMenu" trigger.
    public void openMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    public void playSound() {
        if(!mediaPlayer.isPlaying()) {
           mediaPlayer.start();
        }
        else {
            mediaPlayer.reset();
        }
    }
}
