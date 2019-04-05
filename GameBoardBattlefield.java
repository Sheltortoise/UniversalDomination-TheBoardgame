package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameBoardBattlefield extends AppCompatActivity {
    private RelativeLayout battlefield;
    private ImageView gameBoard;

    private Plot plots[][] = new Plot[30][20];
    private Territory territories[] = new Territory[64];
    private PlanetarySystem planetarySystems[] = new PlanetarySystem[6];

    private ImageView unitImage;
    private Unit zombie;

    private int boardWidth;
    private int boardHeight;

    private int APP_WIDTH;
    private int APP_HEIGHT;

    private float posX = 0;
    private float posY = 0;
    private float touchX;
    private float touchY;

    private static final int INVALID_POINTER_ID = -1;
    private int activePointerID = INVALID_POINTER_ID;

    private ScaleGestureDetector SGD;
    private float scaleFactor = 1.0f;
    private final static float minZoom = 0.5f;
    private final static float maxZoom = 5.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board_battlefield);

        battlefield = findViewById(R.id.battlefieldLayout);

        gameBoard = findViewById(R.id.gameBoardImage);
        gameBoard.setScaleType(ImageView.ScaleType.FIT_XY);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        unitImage = (ImageView) layoutInflater.inflate(R.layout.unit_item, null);

        zombie = new Unit("Zombie", 2, R.drawable.zombie, "Animate", 2, "Ambush", 0, 0, 1, 1);

        // Initialize Plots
        for(int i = 0; i < 30; i++) {
            for(int j = 0; j < 20; j++) {
                Plot plot = new Plot("L" + i + "W" + j, "Unassigned", 0, 0, 1, 1);
                plots[i][j] = plot;
            }
        }
        // Update Plots... TBA
        // Instantiate Territories
        //territories[1].plotsInTerritory[] RESUME HERE

        unitImage.setX(0);
        unitImage.setY(0);

        unitImage.setScaleX((float) .05);
        unitImage.setScaleY((float) .05);

        battlefield.addView(unitImage, 1);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        APP_HEIGHT = size.x;
        APP_WIDTH = size.y;

        SGD = new ScaleGestureDetector(this, new ScaleListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // The scale gesture detector should inspect all the touch events
        SGD.onTouchEvent(event);

        final int action = event.getAction();

        switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                // Gets Coordinates of Initial Touch
                final float x = event.getX();
                final float y = event.getY();

                // Sets Initial Touch to Memory
                touchX = x;
                touchY = y;

                // Save Pointer ID
                activePointerID = event.getPointerId(0);

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Finds the pointer's index and grabs its position.
                final int pointerIndex = event.findPointerIndex(activePointerID);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);

                if(!SGD.isInProgress()) {
                    // Determines the length of the stroke.
                    final float xDistance = (x - touchX);
                    final float yDistance = (y - touchY);

                    // Applies the stroke length to update the position of the image.
                    posX += xDistance;
                    posY += yDistance;

                    zombie.reposition(xDistance, yDistance);
                    unitImage.setX(zombie.xPos);
                    unitImage.setY(zombie.yPos);

                    // Sets values to be used.
                    boardWidth = gameBoard.getMeasuredWidth();
                    boardHeight = gameBoard.getMeasuredHeight();

                    /*
                    // Adds Boundaries
                    if(boardWidth > APP_WIDTH || boardHeight > APP_HEIGHT) {
                        if(posX < (20 - boardWidth)) {
                            posX = (20 - boardWidth);
                        }
                        else if(posX > (APP_HEIGHT - 20)) {
                            posX = (APP_HEIGHT - 20);
                        }
                        if(posY < (20 - boardHeight)) {
                            posY = (20 - boardHeight);
                        }
                        else if(posY > (APP_WIDTH - 20)) {
                            posY = (APP_WIDTH - 20);
                        }
                    }
                    else {
                        if (posX < 20) {
                            posX = 20;
                        }
                        else if ((posX + boardWidth) > (APP_HEIGHT - 20)) {
                            posX = ((APP_HEIGHT - 20) - boardWidth);
                        }
                        if (posY < 20) {
                            posY = 20;
                        }
                        else if ((posY + boardHeight) > (APP_WIDTH - 20)) {
                            posY = ((APP_WIDTH - 20) - boardHeight);
                        }
                    }
                    */

                    // Moves the Views
                    gameBoard.setX(posX);
                    gameBoard.setY(posY);
                }
                // Updates the initial position, so another touch event can start properly.
                touchX = x;
                touchY = y;

                break;
            }
            case MotionEvent.ACTION_UP : {
                activePointerID = INVALID_POINTER_ID;

                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                activePointerID = INVALID_POINTER_ID;

                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                // Extracts the index of the pointer that left the screen.
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);

                if(pointerId == activePointerID) {
                    // Active pointer chooses another active pointer and adjusts.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;

                    touchX = event.getX(newPointerIndex);
                    touchY = event.getY(newPointerIndex);

                    activePointerID = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            // Get the Scale Factor
            scaleFactor *= scaleGestureDetector.getScaleFactor();

            // Prevent Overscan/Underscan
            scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom));

            gameBoard.setScaleX(scaleFactor);
            gameBoard.setScaleY(scaleFactor);

            zombie.alterScale(scaleFactor);
            unitImage.setScaleX(zombie.xScale);
            unitImage.setScaleY(zombie.yScale);

            return true;
        }
    }
}
