package com.sheltoidusa.universaldominationtheboardgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

// This class is so the user can traverse the rule book.
public class RuleBook extends AppCompatActivity {
    Button forwardBtn;
    Button backwardBtn;

    ImageView page;

    // Counter for page number.
    public int counter = 1;

    // First code that gets executed.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_book);

        initialize();
    }
    // Links XML widgets to code.
    public void initialize() {
        forwardBtn = findViewById(R.id.forwardButton);
        backwardBtn = findViewById(R.id.backwardButton);

        page = findViewById(R.id.page);
        page.setScaleType(ImageView.ScaleType.FIT_XY);
    }
    // Moves between pages in the rule book.
    public void changePage(View view) {
        // Checks which button triggered, and insures that the counter does not go out of range.
        if(view.getId() == R.id.forwardButton && (counter != 18)) {
            counter ++;
        }
        else if(view.getId() == R.id.backwardButton && (counter != 1)) {
            counter --;
        }
        // Changes the image to match the page.
        if(counter == 1) {
            page.setImageResource(R.drawable.rulebook_01);
        }
        else if(counter == 2) {
            page.setImageResource(R.drawable.rulebook_02);
        }
        else if(counter == 3) {
            page.setImageResource(R.drawable.rulebook_03);
        }
        else if(counter == 4) {
            page.setImageResource(R.drawable.rulebook_04);
        }
        else if(counter == 5) {
            page.setImageResource(R.drawable.rulebook_05);
        }
        else if(counter == 6) {
            page.setImageResource(R.drawable.rulebook_06);
        }
        else if(counter == 7) {
            page.setImageResource(R.drawable.rulebook_07);
        }
        else if(counter == 8) {
            page.setImageResource(R.drawable.rulebook_08);
        }
        else if(counter == 9) {
            page.setImageResource(R.drawable.rulebook_09);
        }
        else if(counter == 10) {
            page.setImageResource(R.drawable.rulebook_10);
        }
        else if(counter == 11) {
            page.setImageResource(R.drawable.rulebook_11);
        }
        else if(counter == 12) {
            page.setImageResource(R.drawable.rulebook_12);
        }
        else if(counter == 13) {
            page.setImageResource(R.drawable.rulebook_13);
        }
        else if(counter == 14) {
            page.setImageResource(R.drawable.rulebook_14);
        }
        else if(counter == 15) {
            page.setImageResource(R.drawable.rulebook_15);
        }
        else if(counter == 16) {
            page.setImageResource(R.drawable.rulebook_16);
        }
        else if(counter == 17) {
            page.setImageResource(R.drawable.rulebook_17);
        }
        else if(counter == 18) {
            page.setImageResource(R.drawable.rulebook_18);
        }
    }
}
