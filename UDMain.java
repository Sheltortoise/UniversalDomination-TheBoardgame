package com.sheltoidusa.universaldominationtheboardgame;

// Standard Importation Process: Import all necessary android files to utilize the library.
// Alphabetized for quick reference!
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

// The purpose of this Java file is to start the app.
// Extend AppCompatActivity: Necessary to access hidden code.
public class UDMain extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private int countSongs = 0;

    // onCreate(...); : When the app is loaded, this method will trigger.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super.onCreate(...): This accesses the super class to create a local save state.
        super.onCreate(savedInstanceState);

        // setContentView(...): Makes the XML "ud_main" take focus.
        setContentView(R.layout.ud_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.universal_domination_theme);

        // New thread to optimize performance
        new Thread(new Runnable() {
            public void run() {
                for(;;) {
                    if(mediaPlayer.isPlaying()) {
                        try {
                            // Pauses output to save resources
                            Thread.sleep(10000);
                        }
                        catch(InterruptedException iE) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    else {
                        playSound();
                    }
                }
            }
        }).start();
    }
    // openMainMenu(...): Makes the class "MainMenu" trigger.
    public void openMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    public void playSound() {
        // Rotates through my library of music forever.
        if(!mediaPlayer.isPlaying()) {
            if(countSongs == 1) {
                mediaPlayer = MediaPlayer.create(this, R.raw.assassins_to_battle);
            }
            else if(countSongs == 2) {
                mediaPlayer = MediaPlayer.create(this, R.raw.elementals_reinforcement_phase);
            }
            else if(countSongs == 3) {
                mediaPlayer = MediaPlayer.create(this, R.raw.humanity_trial);
            }
            else if(countSongs == 4) {
                mediaPlayer = MediaPlayer.create(this, R.raw.grotians_turn_phase);
            }
            else if(countSongs == 5) {
                mediaPlayer = MediaPlayer.create(this, R.raw.the_ark_departs);
            }
            else if(countSongs == 6) {
                mediaPlayer = MediaPlayer.create(this, R.raw.my_rise);
            }
            else if(countSongs == 7) {
                mediaPlayer = MediaPlayer.create(this, R.raw.my_fall);
            }
            else if(countSongs == 8) {
                mediaPlayer = MediaPlayer.create(this, R.raw.universal_domination_theme);

                countSongs = 0;
            }
            mediaPlayer.start();

            countSongs++;
        }
        else {
            mediaPlayer.reset();
        }
    }
}
