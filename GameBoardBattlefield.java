package com.sheltoidusa.universaldominationtheboardgame;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

// This Java file manages all screen and game elements for the battlfield to appear normal and the user to play the game.
public class GameBoardBattlefield extends AppCompatActivity {
    // Battlefield Layout
    private RelativeLayout battlefield;

    // Message Board Text
    private TextView messageBoardText;

    // Controller Buttons
    private Button upBtn;
    private Button rightBtn;
    private Button downBtn;
    private Button leftBtn;
    private Button centerBtn;
    private Button topRightBtn;
    private Button bottomRightBtn;
    private Button bottomLeftBtn;
    private Button topLeftBtn;

    // Race Card Images
    private ImageButton baseCard;
    private ImageButton weakCard;
    private ImageButton standardCard;
    private ImageButton strongCard;
    private ImageButton heroCard;

    // Dice Images
    private ImageView attackerDie;
    private ImageView defenderDie;

    // Alpha Buttons
    private Button toggleOverlayBtn;
    private Button toggleRaceCardsBtn;
    private Button nextPhaseBtn;

    // Virtual Maps
    private VirtualMaps vm = new VirtualMaps();

    // Layout Inflaters
    private LayoutInflater unitLayout;
    private LayoutInflater resourceLayout;
    private LayoutInflater cursorLayout;

    // Scale Factors
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerID = INVALID_POINTER_ID;

    // Scale Data
    private ScaleGestureDetector SGD;
    private float scaleFactor = 1.0f;
    private final static float minZoom = 0.5f;
    private final static float maxZoom = 5.0f;

    // Touch Data
    private float posX = 0;
    private float posY = 0;
    private float touchX;
    private float touchY;

    // Layout Dimensions
    private float APP_WIDTH;
    private float APP_HEIGHT;

    // Board Dimensions
    private float boardWidth;
    private float boardHeight;

    // Dimension Scale Factors
    private float plotX;
    private float plotY;

    // Player Data
    private ArrayList<Player> playerList = new ArrayList<Player>();
        private Player animusPlayer = new Player("", 0, 0, null, 0, 0);
        private Player antibytePlayer = new Player("", 0, 0, null, 0, 0);
        private Player elementalPlayer = new Player("", 0, 0, null, 0, 0);
        private Player grotianPlayer = new Player("", 0, 0, null, 0, 0);
        private Player hivePlayer = new Player("", 0, 0, null, 0, 0);
        private Player humanityPlayer = new Player("", 0, 0, null, 0, 0);

    // Holds all resource mounds
    private ArrayList<ResourceMound> resourceMoundList = new ArrayList<ResourceMound>();

    // Holds all unit ImageViews
    private ArrayList<ImageView> animusUnits = new ArrayList<ImageView>();
    private ArrayList<ImageView> antibyteUnits = new ArrayList<ImageView>();
    private ArrayList<ImageView> grotianUnits = new ArrayList<ImageView>();
    private ArrayList<ImageView> elementalUnits = new ArrayList<ImageView>();
    private ArrayList<ImageView> hiveUnits = new ArrayList<ImageView>();
    private ArrayList<ImageView> humanityUnits = new ArrayList<ImageView>();

    // Phase Data
    private boolean isReinforcement;
    private boolean isMovement;
    private boolean isCombat;
    private int turnCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board_battlefield);

        // Links XML Game Board to Battlefield
        battlefield = findViewById(R.id.battlefieldLayout);

        // Links XML Overlay to their Widgets
        messageBoardText = findViewById(R.id.messageBoard);

        // Initializes Scale Listener
        SGD = new ScaleGestureDetector(this, new ScaleListener());

        // Links XML Widgets
        upBtn = findViewById(R.id.upButton);
        rightBtn = findViewById(R.id.rightButton);
        downBtn = findViewById(R.id.downButton);
        leftBtn = findViewById(R.id.leftButton);
        centerBtn = findViewById(R.id.centerButton);
        topRightBtn = findViewById(R.id.topRightButton);
        bottomLeftBtn = findViewById(R.id.bottomLeftButton);
        bottomRightBtn = findViewById(R.id.bottomRightButton);
        topLeftBtn = findViewById(R.id.topLeftButton);

        // Links XML Widgets
        baseCard = findViewById(R.id.baseRaceCard);
        weakCard = findViewById(R.id.weakUnitRaceCard);
        standardCard = findViewById(R.id.standardUnitRaceCard);
        strongCard = findViewById(R.id.strongUnitRaceCard);
        heroCard = findViewById(R.id.heroRaceCard);

        // Links XML Widgets
        attackerDie = findViewById(R.id.attacker_die_image);
        defenderDie = findViewById(R.id.defender_die_image);

        // Links XML Widgets
        toggleOverlayBtn = findViewById(R.id.hideOverlayButton);
        toggleRaceCardsBtn = findViewById(R.id.hideRaceCardsButton);
        nextPhaseBtn = findViewById(R.id.nextPhaseButton);

        // makes screen widgets transparent.
        messageBoardText.setAlpha(.5f);
        toggleOverlayBtn.setAlpha(.5f);
        toggleRaceCardsBtn.setAlpha(.5f);

        // Enables Resource Placement
        resourceLayout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Enables Unit Placement
        unitLayout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Enables Cursor Placement
        cursorLayout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Built in utilities that get the active layer's dimensions for later use.
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Determines Layout's Size
        APP_WIDTH = metrics.widthPixels;
        APP_HEIGHT = metrics.heightPixels;

        // Determines Game Board's Size
        boardWidth = APP_WIDTH;
        boardHeight = APP_HEIGHT;

        // Plot Size Factor
        plotX = (boardWidth / 30);
        plotY = (boardHeight / 20);

        // Start the Game
        startGame();
    }
    // Panning Method
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

                    // Adds Boundaries
                    // If the board is the same size as the screen
                    if(boardWidth == APP_WIDTH && boardHeight == APP_HEIGHT) {
                        // Do Nothing
                    }
                    // If board is bigger than the screen.
                    else if(boardWidth > APP_WIDTH || boardHeight > APP_HEIGHT) {
                        if(posX < (100 - boardWidth)) {
                            posX = (100 - boardWidth);
                        }
                        else if(posX > (APP_WIDTH - 100)) {
                            posX = (APP_WIDTH - 100);
                        }
                        if(posY < (100 - boardHeight)) {
                            posY = (100 - boardHeight);
                        }
                        else if(posY > (APP_HEIGHT - 100)) {
                            posY = (APP_HEIGHT - 100);
                        }
                    }
                    // If the board is smaller than the screen.
                    else {
                        if(posX < 20) {
                            posX = 20;
                        }
                        else if((posX + boardWidth) > (APP_WIDTH - 20)) {
                            posX = ((APP_WIDTH - 20) - boardWidth);
                        }
                        if(posY < 20) {
                            posY = 20;
                        }
                        else if((posY + boardHeight) > (APP_HEIGHT - 40)) {
                            posY = ((APP_HEIGHT - 40) - boardHeight);
                        }
                    }
                    // Moves the Views
                    battlefield.setX(posX);
                    battlefield.setY(posY);

                    // Updates Virtual Plot Map
                    positionPlots();
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
    // Scaling Method
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            // Get the Scale Factor
            scaleFactor *= scaleGestureDetector.getScaleFactor();

            // Prevent Overscan/Underscan
            scaleFactor = Math.max(minZoom, Math.min(scaleFactor, maxZoom));

            // Change Gameboard Dimensions
            battlefield.setScaleX(scaleFactor);
            battlefield.setScaleY(scaleFactor);

            // Adjust Virtual Board Dimensions
            boardWidth = (battlefield.getWidth() * scaleFactor);
            boardHeight = (battlefield.getHeight() * scaleFactor);

            // Adjust Virtual Plot Positions
            scalePlots(scaleFactor);

            return true;
        }
    }
    // Doesn't allow the user to backtrack anymore.
    @Override
    public void onBackPressed() {
        // Do Nothing
    }
    // Starts the Board Initialization Process
    public void startGame() {
        // Initialize Screen Elements
        hideOverlay();
        hideRaceCards();
        hideDice();

        // Initialize Java Code
        initializeVirtualMaps();
        initializeExtras();
        //initializeResources();
        initializePlayerTypes();
        initialPlacement();
    }
    // Initial Methods: Called by startGame()
    public void initializeVirtualMaps() {
        // Sets up the terrain of each plot.
        vm.initialize(plotX, plotY);
    }
    // Accepts putExtra from Skirmish Selection Menu
    public void initializeExtras() {
        animusPlayer = (Player) getIntent().getParcelableExtra("ANIMUS_PLAYER");
        antibytePlayer = (Player) getIntent().getParcelableExtra("ANTIBYTE_PLAYER");
        elementalPlayer = (Player) getIntent().getParcelableExtra("ELEMENTAL_PLAYER");
        grotianPlayer = (Player) getIntent().getParcelableExtra("GROTIAN_PLAYER");
        hivePlayer = (Player) getIntent().getParcelableExtra("HIVE_PLAYER");
        humanityPlayer = (Player) getIntent().getParcelableExtra("HUMANITY_PLAYER");
    }
    // Not Implemented in Skirmishes
    public void initializeResources() {
        setResourceMound(6, 2);
        setResourceMound(2, 5);
        setResourceMound(2, 15);
        setResourceMound(4, 18);
        setResourceMound(12, 17);
        setResourceMound(14, 18);
        setResourceMound(20, 15);
        setResourceMound(24, 17);
        setResourceMound(27, 14);
        setResourceMound(26, 7);
        setResourceMound(24, 1);
        setResourceMound(18, 7);
        setResourceMound(12, 7);
        setResourceMound(15, 11);

        for(int i = 0; i < resourceMoundList.size(); i++) {
            ImageView resourceImage = (ImageView) resourceLayout.inflate(R.layout.resource_view, null);

            ResourceMound tempResourceMound = resourceMoundList.get(i);

            resourceImage.setX(tempResourceMound.xPosition);
            resourceImage.setY(tempResourceMound.yPosition);

            resourceImage.setAlpha(.5f);

            battlefield.addView(resourceImage, 0);
        }
    }
    // Places the items stored in the unit lists in Skirmish Selection Java file.
    public void initialPlacement() {
        for(int i = 0; i < playerList.size(); i++) {
            Player tempPlayer = playerList.get(i);

            for(int j = 0; j < tempPlayer.unitList.size(); j++) {
                ImageView unitImage = (ImageView) unitLayout.inflate(R.layout.unit_view, null);

                Unit tempUnit = tempPlayer.unitList.get(j);

                unitImage.setImageResource(tempUnit.imageID);

                float[] tempArr = checkOccupancy(tempUnit.position, tempUnit.points);

                if(tempArr[0] != 100) {
                    unitImage.setX(tempArr[0]);
                    unitImage.setY(tempArr[1]);
                }
                addUnitImage(tempPlayer, unitImage);
                battlefield.addView(unitImage, 0);
            }
        }
        startRotation();
    }
    // Sees who is playing the match... which races.
    public void initializePlayerTypes() {
        int counter = 0;

        if(animusPlayer.playerType != 0) {
            playerList.add(animusPlayer);
            animusPlayer.isTurn = counter;
            counter++;
        }
        if(antibytePlayer.playerType != 0) {
            playerList.add(antibytePlayer);
            antibytePlayer.isTurn = counter;
            counter++;
        }
        if(elementalPlayer.playerType != 0) {
            playerList.add(elementalPlayer);
            elementalPlayer.isTurn = counter;
            counter++;
        }
        if(grotianPlayer.playerType != 0) {
            playerList.add(grotianPlayer);
            grotianPlayer.isTurn = counter;
            counter++;
        }
        if(hivePlayer.playerType != 0) {
            playerList.add(hivePlayer);
            hivePlayer.isTurn = counter;
            counter++;
        }
        if(humanityPlayer.playerType != 0) {
            playerList.add(humanityPlayer);
            humanityPlayer.isTurn = counter;
        }
    }
    // Initial Placement Methods: Called by Initializers
    // Not needed yet.
    public void setResourceMound(int x, int y) {
        String posCode = x + "-" + y;
        ResourceMound resourceMound = new ResourceMound(posCode, 0, 0);

        updatePlotsPlus(posCode, 4);

        resourceMound.xPosition = vm.getVirtualPlots()[x][y].xPos1;
        resourceMound.yPosition = vm.getVirtualPlots()[x][y].yPos1;

        resourceMoundList.add(resourceMound);
    }
    // Checks the plot's occupancy and returns open space in positions X and Y.
    public float[] checkOccupancy(String posCode, int pts) {
        int[] converted = posCodeToInts(posCode);

        int x = converted[0];
        int y = converted[1];

        // Temporary Values for Positioning
        float tempX = 100;
        float tempY = 100;

        Plot tempPlot = vm.getVirtualPlots()[x][y];

        // Adding a Large Unit
        if(pts == 4 && tempPlot.points == 0) {
            tempX = tempPlot.xPos1;
            tempY = tempPlot.yPos1;

            updatePlotsPlus(posCode, pts);
        }
        // Adding a Medium Unit
        if(pts == 2 && tempPlot.points == 0) {
            tempX = tempPlot.xPos1;
            tempY = tempPlot.yPos1;

            updatePlotsPlus(posCode, pts);
        }
        else if(pts == 2 && tempPlot.points == 1 || pts == 2 && tempPlot.points == 2) {
            tempX = tempPlot.xPos2;
            tempY = tempPlot.yPos2;

            updatePlotsPlus(posCode, pts);
        }
        // Adding a Small Unit
        if(pts == 1 && tempPlot.points == 0) {
            tempX = tempPlot.xPos1;
            tempY = tempPlot.yPos1;

            updatePlotsPlus(posCode, pts);
        }
        else if(pts == 1 && tempPlot.points == 1) {
            tempX = tempPlot.xPos3;
            tempY = tempPlot.yPos3;

            updatePlotsPlus(posCode, pts);
        }
        else if(pts == 1 && tempPlot.points == 2) {
            tempX = tempPlot.xPos2;
            tempY = tempPlot.yPos2;

            updatePlotsPlus(posCode, pts);
        }
        else if(pts == 1 && tempPlot.points == 3) {
            tempX = tempPlot.xPos4;
            tempY = tempPlot.yPos4;

            updatePlotsPlus(posCode, pts);
        }
        float[] tempPos = {tempX, tempY};

        return tempPos;
    }
    // Game Loop
    public void startRotation() {
        // Give each player 5 Resources per full turn.
        addResources();

        // Check Whose Turn
        if(animusPlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
        else if(antibytePlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
        else if(elementalPlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
        else if(grotianPlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
        else if(hivePlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
        else if(humanityPlayer.isTurn == turnCounter) {
            reinforcementPhase();
        }
    }
    // Sets Reinforcement Phase Widgets and Booleans
    public void reinforcementPhase() {
        isReinforcement = true;
        isMovement = false;
        isCombat = false;

        hideOverlay();
        hideDice();
        unhideRaceCards();
    }
    // Sets Movement Phase Widgets and Booleans
    public void movementPhase() {
        isReinforcement = false;
        isMovement = true;
        isCombat = false;

        hideRaceCards();
        hideDice();
        unhideOverlay();
    }
    // Sets Combat Phase Widgets and Booleans
    public void combatPhase() {
        isReinforcement = false;
        isMovement = false;
        isCombat = true;

        hideRaceCards();
        hideDice();
        unhideOverlay();
    }
    // Checks the win condition of the game... Does not work right now.
    public void checkWinCondition() {
        // Remove Dead Players
        for(int i = 0; i < playerList.size(); i++) {
            for(int j = 0; j < playerList.get(i).unitList.size(); j++) {
                if(playerList.get(i).unitList.isEmpty()) {
                    playerList.remove(playerList.get(i));
                }
            }
        }
        // Not implemented yet.
        /*
        // Set DBNO PLayers
        for(int i = 0; i < playerList.size(); i++) {
            Player tempPlayer = playerList.get(i);
            boolean hasBase = false;

            for(int j = 0; j < tempPlayer.unitList.size(); j++) {
                // Checks Player Lists for Base
                if(tempPlayer.unitList.get(j).imageID == R.drawable.the_tribute) {
                    hasBase = true;
                }
                else if(tempPlayer.unitList.get(j).imageID == R.drawable.the_cornucopia) {
                    hasBase = true;
                }
                else if(tempPlayer.unitList.get(j).imageID == R.drawable.the_spire) {
                    hasBase = true;
                }
                else if(tempPlayer.unitList.get(j).imageID == R.drawable.the_lair) {
                    hasBase = true;
                }
                else if(tempPlayer.unitList.get(j).imageID == R.drawable.the_colony) {
                    hasBase = true;
                }
                else if(tempPlayer.unitList.get(j).imageID == R.drawable.the_ark) {
                    hasBase = true;
                }
                // Updates DBNO if hasBase is false... 0 is DBNO, 1 is Up.
                if(hasBase == false) {
                    tempPlayer.isDBNO = 0;
                }
            }
        }
        */

        // Suppossed to Exit the Game Loop
        if(playerList.size() == 1) {
            messageBoardText.setText("You Win!");
        }
        // Keep the game going, keep Playing.
        else {
            resetMobility();

            int playerCount = 0;

            // Determine Number of Players
            if(animusPlayer.playerType != 0) {
                playerCount++;
            }
            else if(antibytePlayer.playerType != 0) {
                playerCount++;
            }
            else if(elementalPlayer.playerType != 0) {
                playerCount++;
            }
            else if(grotianPlayer.playerType != 0) {
                playerCount++;
            }
            else if(hivePlayer.playerType != 0) {
                playerCount++;
            }
            else if(humanityPlayer.playerType != 0) {
                playerCount++;
            }
            // Reset Turn Counter
            if(turnCounter == playerCount) {
                turnCounter = 0;
            }
            else {
                turnCounter++;
            }
            startRotation();
        }
    }
    // Mobility Methods
    // Sets the mobility of each unit to equal a value.
    public void setMobility(Player smPlayer, int smMobility) {
        if(animusPlayer == smPlayer) {
            for(int i = 0; i < animusPlayer.unitList.size(); i++) {
                if(!animusPlayer.unitList.get(i).ability.startsWith("Con")) {
                    animusPlayer.unitList.get(i).mobility = smMobility;
                }
            }
        }
        else if(antibytePlayer == smPlayer) {
            for(int i = 0; i < antibytePlayer.unitList.size(); i++) {
                if(!antibytePlayer.unitList.get(i).ability.startsWith("Con")) {
                    antibytePlayer.unitList.get(i).mobility = smMobility;
                }

            }
        }
        else if(elementalPlayer == smPlayer) {
            for(int i = 0; i < elementalPlayer.unitList.size(); i++) {
                if(!elementalPlayer.unitList.get(i).ability.startsWith("Con")) {
                    elementalPlayer.unitList.get(i).mobility = smMobility;
                }
            }
        }
        else if(grotianPlayer == smPlayer) {
            for(int i = 0; i < grotianPlayer.unitList.size(); i++) {
                if(!grotianPlayer.unitList.get(i).ability.startsWith("Con")) {
                    grotianPlayer.unitList.get(i).mobility = smMobility;
                }
            }
        }
        else if(hivePlayer == smPlayer) {
            for(int i = 0; i < hivePlayer.unitList.size(); i++) {
                if(!hivePlayer.unitList.get(i).ability.startsWith("Con")) {
                    hivePlayer.unitList.get(i).mobility = smMobility;
                }
            }
        }
        else if(humanityPlayer == smPlayer) {
            for(int i = 0; i < humanityPlayer.unitList.size(); i++) {
                if(!humanityPlayer.unitList.get(i).ability.startsWith("Con")) {
                    humanityPlayer.unitList.get(i).mobility = smMobility;
                }
            }
        }
    }
    // Resets the mobility of each unit to their base values.
    public void resetMobility() {
        for(int i = 0; i < playerList.size(); i++) {
            Player rmPlayer = playerList.get(i);

            for(int j = 0; j < rmPlayer.unitList.size(); j++) {
                Unit rmUnit = rmPlayer.unitList.get(j);

                if(rmUnit.name.equals("The Tribute") || rmUnit.name.equals("The Ark") || rmUnit.name.equals("The Cornucopia") || rmUnit.name.equals("The Spire") || rmUnit.name.equals("The Lair") || rmUnit.name.equals("The Colony")) {
                    // Leave the bases alone.
                }
                else if(rmUnit.name.equals("Zombie") || rmUnit.name.equals("Mercenary") || rmUnit.name.equals("Titan") || rmUnit.name.equals("Geode") || rmUnit.name.equals("Ogre") || rmUnit.name.equals("Aent")) {
                    rmUnit.mobility = 2;
                }
                else if(rmUnit.name.equals("Skeleton") || rmUnit.name.equals("Soldier") || rmUnit.name.equals("SPK Drone") || rmUnit.name.equals("Pyrode") || rmUnit.name.equals("Orc") || rmUnit.name.equals("Spyder")) {
                    rmUnit.mobility = 3;
                }
                else if(rmUnit.name.equals("Wraith") || rmUnit.name.equals("Assassin") || rmUnit.name.equals("Hornet") || rmUnit.name.equals("Aerode") || rmUnit.name.equals("Goblin") || rmUnit.name.equals("Great Mantis")) {
                    rmUnit.mobility = 4;
                }
                else if(rmUnit.name.equals("Fade") || rmUnit.name.equals("Alpha Kanein") || rmUnit.name.equals("Kristoff") || rmUnit.name.equals("Metamorphous") || rmUnit.name.equals("Toreg") || rmUnit.name.equals("Xythox")) {
                    rmUnit.mobility = 5;
                }
                playerList.get(i).unitList.set(j, rmUnit);
            }
        }
    }
    // Resource Methods
    // Gives each player five resources per turn.
    public void addResources() {
        for(int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).isTurn == turnCounter) {
                playerList.get(i).resources += 5;

                changeRaceCards(playerList.get(i).race);
            }
        }
    }
    // Controller Methods
    // Toggles overlay
    public void toggleOverlay(View view) {
        if(centerBtn.getAlpha() != 0) {
            hideOverlay();
        }
        else {
            unhideOverlay();
        }
    }
    // Hides Controller
    public void hideOverlay() {
        // Hide the Controller
        upBtn.setAlpha(0f);
        rightBtn.setAlpha(0f);
        downBtn.setAlpha(0f);
        leftBtn.setAlpha(0f);
        centerBtn.setAlpha(0f);
        topRightBtn.setAlpha(0f);
        bottomRightBtn.setAlpha(0f);
        bottomLeftBtn.setAlpha(0f);
        topLeftBtn.setAlpha(0f);

        // Disable the Controller
        upBtn.setEnabled(false);
        rightBtn.setEnabled(false);
        downBtn.setEnabled(false);
        leftBtn.setEnabled(false);
        centerBtn.setEnabled(false);
        topRightBtn.setEnabled(false);
        bottomRightBtn.setEnabled(false);
        bottomLeftBtn.setEnabled(false);
        topLeftBtn.setEnabled(false);

        // Update Button Image
        toggleOverlayBtn.setBackgroundResource(R.drawable.backward_button);
        toggleOverlayBtn.setAlpha(0f);
        toggleOverlayBtn.setEnabled(false);
    }
    // Reveals Controller
    public void unhideOverlay() {
        // Unhide the Controller
        upBtn.setAlpha(.5f);
        rightBtn.setAlpha(.5f);
        downBtn.setAlpha(.5f);
        leftBtn.setAlpha(.5f);
        centerBtn.setAlpha(.5f);
        topRightBtn.setAlpha(0f);
        bottomRightBtn.setAlpha(0f);
        bottomLeftBtn.setAlpha(0f);
        topLeftBtn.setAlpha(0f);

        // Enable the Controller
        upBtn.setEnabled(true);
        rightBtn.setEnabled(true);
        downBtn.setEnabled(true);
        leftBtn.setEnabled(true);
        centerBtn.setEnabled(true);
        topRightBtn.setEnabled(true);
        bottomRightBtn.setEnabled(true);
        bottomLeftBtn.setEnabled(true);
        topLeftBtn.setEnabled(true);

        // Update Button Image
        toggleOverlayBtn.setBackgroundResource(R.drawable.forward_button);
        toggleOverlayBtn.setAlpha(.5f);
        toggleOverlayBtn.setEnabled(true);
    }
    // Progresses the game loop and ensures game factors.
    public void nextPhase(View view) {
        if(isReinforcement) {
            // Makes sure no player has more than 20 resources ever.
            if(animusPlayer.isTurn == turnCounter) {
                if(animusPlayer.resources > 20) {
                    animusPlayer.resources = 20;
                }
            }
            else if(antibytePlayer.isTurn == turnCounter) {
                if(antibytePlayer.resources > 20) {
                    antibytePlayer.resources = 20;
                }
            }
            else if(elementalPlayer.isTurn == turnCounter) {
                if(elementalPlayer.resources > 20) {
                    elementalPlayer.resources = 20;
                }
            }
            else if(grotianPlayer.isTurn == turnCounter) {
                if(grotianPlayer.resources > 20) {
                    grotianPlayer.resources = 20;
                }
            }
            else if(hivePlayer.isTurn == turnCounter) {
                if(hivePlayer.resources > 20) {
                    hivePlayer.resources = 20;
                }
            }
            else if(humanityPlayer.isTurn == turnCounter) {
                if(humanityPlayer.resources > 20) {
                    humanityPlayer.resources = 20;
                }
            }
            movementPhase();
        }
        else if(isMovement) {
            // Makes sure the Combat Phase attack system can work.
            if(animusPlayer.isTurn == turnCounter) {
                setMobility(animusPlayer, 1);
            }
            else if(antibytePlayer.isTurn == turnCounter) {
                setMobility(antibytePlayer, 1);
            }
            else if(elementalPlayer.isTurn == turnCounter) {
                setMobility(elementalPlayer, 1);
            }
            else if(grotianPlayer.isTurn == turnCounter) {
                setMobility(grotianPlayer, 1);
            }
            else if(hivePlayer.isTurn == turnCounter) {
                setMobility(hivePlayer, 1);
            }
            else if(humanityPlayer.isTurn == turnCounter) {
                setMobility(humanityPlayer, 1);
            }
            combatPhase();
        }
        else if(isCombat) {
            checkWinCondition();
        }
    }
    // Overlay Methods
    // Detects Directional movement for tha movement and attack phases.
    public void overlayPressed(View view) {
        Button saButton = (Button) view;

        // During Movement Phase
        if(saButton == centerBtn && isMovement) {
            moveUnit("waste");
        }
        else if(saButton == upBtn && isMovement) {
            moveUnit("up");
        }
        else if(saButton == rightBtn && isMovement) {
            moveUnit("right");
        }
        else if(saButton == downBtn && isMovement) {
            moveUnit("down");
        }
        else if(saButton == leftBtn && isMovement) {
            moveUnit("left");
        }
        // During Combat Phase
        if(saButton == centerBtn && isCombat) {
            attackPlot("waste");
        }
        else if(saButton == upBtn && isCombat) {
            attackPlot("up");
        }
        else if(saButton == rightBtn && isCombat) {
            attackPlot("right");
        }
        else if(saButton == downBtn && isCombat) {
            attackPlot("down");
        }
        else if(saButton == leftBtn && isCombat) {
            attackPlot("left");
        }
    }
    // Movement Phase Methods
    // Moves the units
    public void moveUnit(String muDirection) {
        boolean isFound = false;

        for(int i = 0; i < playerList.size(); i++) {
            Player muPlayer = playerList.get(i);

            if(muPlayer.isTurn == turnCounter) {
                for(int j = 0; j < muPlayer.unitList.size(); j++) {
                    Unit muUnit = muPlayer.unitList.get(j);

                    if(muUnit.mobility > 0 && !isFound) {
                        checkMove(muPlayer, muUnit, muDirection, j);

                        isFound = true;
                    }
                }
            }
        }
    }
    // Makes sure move is valid.
    public void checkMove(Player muPlayer, Unit muUnit, String muDirection, int muCellPos) {
        String currentPos = muUnit.position;

        int[] converted = posCodeToInts(currentPos);

        int currentX = converted[0];
        int currentY = converted[1];

        if(muDirection.equals("waste")) {
            if(muPlayer == animusPlayer) {
                animusPlayer.unitList.get(muCellPos).mobility = 0;
            }
            else if(muPlayer == antibytePlayer) {
                antibytePlayer.unitList.get(muCellPos).mobility = 0;
            }
            else if(muPlayer == elementalPlayer) {
                elementalPlayer.unitList.get(muCellPos).mobility = 0;
            }
            else if(muPlayer == grotianPlayer) {
                grotianPlayer.unitList.get(muCellPos).mobility = 0;
            }
            else if(muPlayer == hivePlayer) {
                hivePlayer.unitList.get(muCellPos).mobility = 0;
            }
            else if(muPlayer == humanityPlayer) {
                humanityPlayer.unitList.get(muCellPos).mobility = 0;
            }
        }
        else if(muDirection.equals("up") && currentY != 0) {
            currentY -= 1;
        }
        else if(muDirection.equals("right") && currentX != 29) {
            currentX += 1;
        }
        else if(muDirection.equals("down") && currentY != 19) {
            currentY += 1;
        }
        else if(muDirection.equals("left") && currentX != 0) {
            currentX -= 1;
        }
        String newPos = currentX + "-" + currentY;

        boolean moveMade = false;

        // Scans for other units.
        for(int i = 0; i < playerList.size(); i++) {
            Player cmPlayer = playerList.get(i);

            for(int j = 0; j < cmPlayer.unitList.size(); j++) {
                Unit cmUnit = cmPlayer.unitList.get(j);

                if(!moveMade) {
                    if(!(cmUnit.position.equals(newPos)) && !(cmUnit.species.equals(muUnit.species))) {
                        makeMove(muUnit, muCellPos, currentPos, newPos);

                        moveMade = true;
                    }
                }
            }
        }
    }
    // Commits the move.
    public void makeMove(Unit mmUnit, int mmCellPos, String currentPos, String newPos) {
        float[] tempArr = checkOccupancy(newPos, mmUnit.points);

        if(tempArr[0] != 100) {
            moveUnitImage(mmCellPos, tempArr);

            if(animusPlayer.isTurn == turnCounter) {
                animusPlayer.unitList.get(mmCellPos).mobility -= 1;
                animusPlayer.unitList.get(mmCellPos).position = newPos;
            }
            else if(antibytePlayer.isTurn == turnCounter) {
                antibytePlayer.unitList.get(mmCellPos).mobility -= 1;
                antibytePlayer.unitList.get(mmCellPos).position = newPos;
            }
            else if(elementalPlayer.isTurn == turnCounter) {
                elementalPlayer.unitList.get(mmCellPos).mobility -= 1;
                elementalPlayer.unitList.get(mmCellPos).position = newPos;
            }
            else if(grotianPlayer.isTurn == turnCounter) {
                grotianPlayer.unitList.get(mmCellPos).mobility -= 1;
                grotianPlayer.unitList.get(mmCellPos).position = newPos;
            }
            else if(hivePlayer.isTurn == turnCounter) {
                hivePlayer.unitList.get(mmCellPos).mobility -= 1;
                hivePlayer.unitList.get(mmCellPos).position = newPos;
            }
            else if(humanityPlayer.isTurn == turnCounter) {
                humanityPlayer.unitList.get(mmCellPos).mobility -= 1;
                humanityPlayer.unitList.get(mmCellPos).position = newPos;
            }
            plotRearranger(currentPos);
        }
    }
    // Combat Phase Methods
    // Starts the Attack
    public void attackPlot(String apDirection) {
        boolean isValid = false;

        for(int i = 0; i < playerList.size(); i++) {
            Player apPlayer = playerList.get(i);

            if(apPlayer.isTurn == turnCounter) {
                for(int j = 0; j < apPlayer.unitList.size(); j++) {
                    Unit apUnit = apPlayer.unitList.get(j);

                    if(apUnit.resourceCost != 0 && apUnit.mobility > 0 && !isValid) {
                        checkAttack(apPlayer, apUnit, apDirection, j);

                        isValid = true;
                    }
                }
            }
        }
    }
    // Makes sure if the attack is valid.
    public void checkAttack(Player caPlayer, Unit caUnit, String caDirection, int caCellPos) {
        String currentPos = caUnit.position;

        int[] converted = posCodeToInts(currentPos);

        int currentX = converted[0];
        int currentY = converted[1];

        if(caDirection.equals("waste")) {
            if(caPlayer == animusPlayer) {
                animusPlayer.unitList.get(caCellPos).mobility = 0;
            }
            else if(caPlayer == antibytePlayer) {
                antibytePlayer.unitList.get(caCellPos).mobility = 0;
            }
            else if(caPlayer == elementalPlayer) {
                elementalPlayer.unitList.get(caCellPos).mobility = 0;
            }
            else if(caPlayer == grotianPlayer) {
                grotianPlayer.unitList.get(caCellPos).mobility = 0;
            }
            else if(caPlayer == hivePlayer) {
                hivePlayer.unitList.get(caCellPos).mobility = 0;
            }
            else if(caPlayer == humanityPlayer) {
                humanityPlayer.unitList.get(caCellPos).mobility = 0;
            }
        }
        else if(caDirection.equals("up") && currentY != 0) {
            currentY -= 1;
        }
        else if(caDirection.equals("right") && currentX != 29) {
            currentX += 1;
        }
        else if(caDirection.equals("down") && currentY != 19) {
            currentY += 1;
        }
        else if(caDirection.equals("left") && currentX != 0) {
            currentX -= 1;
        }
        String newPos = currentX + "-" + currentY;

        boolean madeAttack = false;

        for(int i = 0; i < playerList.size(); i++) {
            Player tempPlayer = playerList.get(i);

            for(int j = 0; j < tempPlayer.unitList.size(); j++) {
                Unit tempUnit = tempPlayer.unitList.get(j);

                if(!madeAttack) {
                    if((tempUnit.position.equals(newPos)) && !(tempUnit.species.equals(caUnit.species))) {
                        makeAttack(caPlayer, caUnit, caCellPos, currentPos, newPos);

                        madeAttack = true;
                    }
                }
            }
        }
    }
    // Commits the Attack
    public void makeAttack(Player attackingPlayer, Unit attackingUnit, int attackingCellPos, String attackingPlot, String defendingPlot) {
        boolean foundUnit = false;

        Player defendingPlayer = new Player("", 0, 0, null, 0, 0);
        Unit defendingUnit = new Unit("", 0, R.drawable.test_box, "", 0, "", 0, 0, "");

        int defenderCellPos = 0;

        for(int i = 0; i < playerList.size(); i++) {
            defendingPlayer = playerList.get(i);

            for(int j = 0; j < defendingPlayer.unitList.size(); j++) {
                if(!foundUnit) {
                    defendingUnit = defendingPlayer.unitList.get(j);

                    if(defendingUnit.position.equals(defendingPlot)) {
                        defenderCellPos = j;

                        foundUnit = true;
                    }
                }
            }
        }
        if(foundUnit) {
            int[] rolls = roll(attackingUnit.power, defendingUnit.power);

            int attackerRoll = rolls[0];
            int defenderRoll = rolls[1];

            unhideDice();
            setDice(attackingUnit.power, attackerRoll, defendingUnit.power, defenderRoll);

            // Handle the unit's ability to attack only once.
            if(animusPlayer.isTurn == turnCounter) {
                animusPlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            else if(antibytePlayer.isTurn == turnCounter) {
                antibytePlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            else if(elementalPlayer.isTurn == turnCounter) {
                elementalPlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            else if(grotianPlayer.isTurn == turnCounter) {
                grotianPlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            else if(hivePlayer.isTurn == turnCounter) {
                hivePlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            else if(humanityPlayer.isTurn == turnCounter) {
                humanityPlayer.unitList.get(attackingCellPos).mobility -= 1;
            }
            // Remove the Loser and its View
            if(attackerRoll > defenderRoll) {
                removeUnit(defendingPlayer, defenderCellPos);
                removeUnitImage(defendingPlayer, defenderCellPos);
                plotRearranger(defendingPlot);
            }
            else {
                removeUnit(attackingPlayer, attackingCellPos);
                removeUnitImage(attackingPlayer, attackingCellPos);
                plotRearranger(attackingPlot);
            }
        }
    }
    // Handles the values of the roll
    public int[] roll(int a, int d) {
        Random rng = new Random();

        int[] arr = {0, 0};

        if(a != 0 && d != 0) {
            arr[0] = rng.nextInt(a) + 1;
            arr[1] = rng.nextInt(d) + 1;
        }
        return arr;
    }
    // Dice Methods
    // Hides the Dice
    public void hideDice() {
        attackerDie.setAlpha(0f);
        defenderDie.setAlpha(0f);
    }
    // Reveals the Dice
    public void unhideDice() {
        attackerDie.setAlpha(.5f);
        defenderDie.setAlpha(.5f);
    }
    // Sets the dice to represent the rolls.
    public void setDice(int attackerPower, int a, int defenderPower, int d) {
        // Set Attacker Die
        if(attackerPower == 6) {
            if(a == 1) {
                attackerDie.setImageResource(R.drawable.a6_1);
            }
            else if(a == 2) {
                attackerDie.setImageResource(R.drawable.a6_2);
            }
            else if(a == 3) {
                attackerDie.setImageResource(R.drawable.a6_3);
            }
            else if(a == 4) {
                attackerDie.setImageResource(R.drawable.a6_4);
            }
            else if(a == 5) {
                attackerDie.setImageResource(R.drawable.a6_5);
            }
            else if(a == 6) {
                attackerDie.setImageResource(R.drawable.a6_6);
            }
        }
        else if(attackerPower == 8) {
            if(a == 1) {
                attackerDie.setImageResource(R.drawable.a8_1);
            }
            else if(a == 2) {
                attackerDie.setImageResource(R.drawable.a8_2);
            }
            else if(a == 3) {
                attackerDie.setImageResource(R.drawable.a8_3);
            }
            else if(a == 4) {
                attackerDie.setImageResource(R.drawable.a8_4);
            }
            else if(a == 5) {
                attackerDie.setImageResource(R.drawable.a8_5);
            }
            else if(a == 6) {
                attackerDie.setImageResource(R.drawable.a8_6);
            }
            else if(a == 7) {
                attackerDie.setImageResource(R.drawable.a8_7);
            }
            else if(a == 8) {
                attackerDie.setImageResource(R.drawable.a8_8);
            }
        }
        else if(attackerPower == 10) {
            if(a == 1) {
                attackerDie.setImageResource(R.drawable.a10_1);
            }
            else if(a == 2) {
                attackerDie.setImageResource(R.drawable.a10_2);
            }
            else if(a == 3) {
                attackerDie.setImageResource(R.drawable.a10_3);
            }
            else if(a == 4) {
                attackerDie.setImageResource(R.drawable.a10_4);
            }
            else if(a == 5) {
                attackerDie.setImageResource(R.drawable.a10_5);
            }
            else if(a == 6) {
                attackerDie.setImageResource(R.drawable.a10_6);
            }
            else if(a == 7) {
                attackerDie.setImageResource(R.drawable.a10_7);
            }
            else if(a == 8) {
                attackerDie.setImageResource(R.drawable.a10_8);
            }
            else if(a == 9) {
                attackerDie.setImageResource(R.drawable.a10_9);
            }
            else if(a == 10) {
                attackerDie.setImageResource(R.drawable.a10_10);
            }
        }
        else if(attackerPower == 12) {
            if(a == 1) {
                attackerDie.setImageResource(R.drawable.a12_1);
            }
            else if(a == 2) {
                attackerDie.setImageResource(R.drawable.a12_2);
            }
            else if(a == 3) {
                attackerDie.setImageResource(R.drawable.a12_3);
            }
            else if(a == 4) {
                attackerDie.setImageResource(R.drawable.a12_4);
            }
            else if(a == 5) {
                attackerDie.setImageResource(R.drawable.a12_5);
            }
            else if(a == 6) {
                attackerDie.setImageResource(R.drawable.a12_6);
            }
            else if(a == 7) {
                attackerDie.setImageResource(R.drawable.a12_7);
            }
            else if(a == 8) {
                attackerDie.setImageResource(R.drawable.a12_8);
            }
            else if(a == 9) {
                attackerDie.setImageResource(R.drawable.a12_9);
            }
            else if(a == 10) {
                attackerDie.setImageResource(R.drawable.a12_10);
            }
            else if(a == 11) {
                attackerDie.setImageResource(R.drawable.a12_11);
            }
            else if(a == 12) {
                attackerDie.setImageResource(R.drawable.a12_12);
            }
        }
        // Set Defender Die
        if(defenderPower == 6) {
            if(d == 1) {
                defenderDie.setImageResource(R.drawable.d6_1);
            }
            else if(d == 2) {
                defenderDie.setImageResource(R.drawable.d6_2);
            }
            else if(d == 3) {
                defenderDie.setImageResource(R.drawable.d6_3);
            }
            else if(d == 4) {
                defenderDie.setImageResource(R.drawable.d6_4);
            }
            else if(d == 5) {
                defenderDie.setImageResource(R.drawable.d6_5);
            }
            else if(d == 6) {
                defenderDie.setImageResource(R.drawable.d6_6);
            }
        }
        else if(defenderPower == 8) {
            if(d == 1) {
                defenderDie.setImageResource(R.drawable.d8_1);
            }
            else if(d == 2) {
                defenderDie.setImageResource(R.drawable.d8_2);
            }
            else if(d == 3) {
                defenderDie.setImageResource(R.drawable.d8_3);
            }
            else if(d == 4) {
                defenderDie.setImageResource(R.drawable.d8_4);
            }
            else if(d == 5) {
                defenderDie.setImageResource(R.drawable.d8_5);
            }
            else if(d == 6) {
                defenderDie.setImageResource(R.drawable.d8_6);
            }
            else if(d == 7) {
                defenderDie.setImageResource(R.drawable.d8_7);
            }
            else if(d == 8) {
                defenderDie.setImageResource(R.drawable.d8_8);
            }
        }
        else if(defenderPower == 10) {
            if(d == 1) {
                defenderDie.setImageResource(R.drawable.d10_1);
            }
            else if(d == 2) {
                defenderDie.setImageResource(R.drawable.d10_2);
            }
            else if(d == 3) {
                defenderDie.setImageResource(R.drawable.d10_3);
            }
            else if(d == 4) {
                defenderDie.setImageResource(R.drawable.d10_4);
            }
            else if(d == 5) {
                defenderDie.setImageResource(R.drawable.d10_5);
            }
            else if(d == 6) {
                defenderDie.setImageResource(R.drawable.d10_6);
            }
            else if(d == 7) {
                defenderDie.setImageResource(R.drawable.d10_7);
            }
            else if(d == 8) {
                defenderDie.setImageResource(R.drawable.d10_8);
            }
            else if(d == 9) {
                defenderDie.setImageResource(R.drawable.d10_9);
            }
            else if(d == 10) {
                defenderDie.setImageResource(R.drawable.d10_10);
            }
        }
        else if(defenderPower == 12) {
            if(d == 1) {
                defenderDie.setImageResource(R.drawable.d12_1);
            }
            else if(d == 2) {
                defenderDie.setImageResource(R.drawable.d12_2);
            }
            else if(d == 3) {
                defenderDie.setImageResource(R.drawable.d12_3);
            }
            else if(d == 4) {
                defenderDie.setImageResource(R.drawable.d12_4);
            }
            else if(d == 5) {
                defenderDie.setImageResource(R.drawable.d12_5);
            }
            else if(d == 6) {
                defenderDie.setImageResource(R.drawable.d12_6);
            }
            else if(d == 7) {
                defenderDie.setImageResource(R.drawable.d12_7);
            }
            else if(d == 8) {
                defenderDie.setImageResource(R.drawable.d12_8);
            }
            else if(d == 9) {
                defenderDie.setImageResource(R.drawable.d12_9);
            }
            else if(d == 10) {
                defenderDie.setImageResource(R.drawable.d12_10);
            }
            else if(d == 11) {
                defenderDie.setImageResource(R.drawable.d12_11);
            }
            else if(d == 12) {
                defenderDie.setImageResource(R.drawable.d12_12);
            }
        }
    }
    // Race Card Methods
    // Toggles the Race Cards
    public void toggleRaceCards(View view) {
        if(standardCard.getAlpha() != 0) {
            hideRaceCards();
        }
        else {
            unhideRaceCards();
        }
    }
    // Hides the Race Cards
    public void hideRaceCards() {
        // Hide Race Cards
        baseCard.setAlpha(0f);
        weakCard.setAlpha(0f);
        standardCard.setAlpha(0f);
        strongCard.setAlpha(0f);
        heroCard.setAlpha(0f);

        // Disable Race Cards
        baseCard.setEnabled(false);
        weakCard.setEnabled(false);
        standardCard.setEnabled(false);
        strongCard.setEnabled(false);
        heroCard.setEnabled(false);

        // Update Button Image
        toggleRaceCardsBtn.setBackgroundResource(R.drawable.forward_button);
        toggleRaceCardsBtn.setAlpha(0f);
        toggleRaceCardsBtn.setEnabled(false);
    }
    // Reveals the Race Cards
    public void unhideRaceCards() {
        // Unhide Race Cards
        baseCard.setAlpha(1f);
        weakCard.setAlpha(1f);
        standardCard.setAlpha(1f);
        strongCard.setAlpha(1f);
        heroCard.setAlpha(1f);

        // Undisable Race Cards
        baseCard.setEnabled(true);
        weakCard.setEnabled(true);
        standardCard.setEnabled(true);
        strongCard.setEnabled(true);
        heroCard.setEnabled(true);

        // Update Button Image
        toggleRaceCardsBtn.setBackgroundResource(R.drawable.backward_button);
        toggleRaceCardsBtn.setAlpha(.5f);
        toggleRaceCardsBtn.setEnabled(true);
    }
    // Makes the race cards match the race that has controll.
    public void changeRaceCards(String str) {
        if(str.equals("Animus")) {
            baseCard.setImageResource(R.drawable.rc_the_tribute);
            weakCard.setImageResource(R.drawable.rc_zombie_horde);
            standardCard.setImageResource(R.drawable.rc_wraith_swarm);
            strongCard.setImageResource(R.drawable.rc_skeleton_legion);
            heroCard.setImageResource(R.drawable.rc_fade);
        }
        else if(str.equals("Antibytes")) {
            baseCard.setImageResource(R.drawable.rc_the_cornucopia);
            weakCard.setImageResource(R.drawable.rc_spk_drone);
            standardCard.setImageResource(R.drawable.rc_hornet);
            strongCard.setImageResource(R.drawable.rc_titan);
            heroCard.setImageResource(R.drawable.rc_kristoff);
        }
        else if(str.equals("Elementals")) {
            baseCard.setImageResource(R.drawable.rc_the_spire);
            weakCard.setImageResource(R.drawable.rc_aerode_decaunit);
            standardCard.setImageResource(R.drawable.rc_pyrode_hexunit);
            strongCard.setImageResource(R.drawable.rc_geode_pentaunit);
            heroCard.setImageResource(R.drawable.rc_metamorphous);
        }
        else if(str.equals("Grotians")) {
            baseCard.setImageResource(R.drawable.rc_the_lair);
            weakCard.setImageResource(R.drawable.rc_goblin_horde);
            standardCard.setImageResource(R.drawable.rc_ogre_group);
            strongCard.setImageResource(R.drawable.rc_orc_chieftain);
            heroCard.setImageResource(R.drawable.rc_toreg);
        }
        else if(str.equals("Hive")) {
            baseCard.setImageResource(R.drawable.rc_the_colony);
            weakCard.setImageResource(R.drawable.rc_aent_squad);
            standardCard.setImageResource(R.drawable.rc_spider_unit);
            strongCard.setImageResource(R.drawable.rc_mantis_clan);
            heroCard.setImageResource(R.drawable.rc_xythox);
        }
        else if (str.equals("Humanity")){
            baseCard.setImageResource(R.drawable.rc_the_ark);
            weakCard.setImageResource(R.drawable.rc_squad_of_soldiers);
            standardCard.setImageResource(R.drawable.rc_mercenary);
            strongCard.setImageResource(R.drawable.rc_assassin);
            heroCard.setImageResource(R.drawable.rc_alpha_kanein);
        }
    }
    // Managing Units
    // Adds Units to the battlefield.
    public void addUnit(View view) {
        // Handle Unit Spawning by Race
        if(view == baseCard) {
            // Do nothing until battle cards are added.
        }
        else if(view == weakCard) {
            if(animusPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Zombie
                Unit tempUnit = new Unit("Zombie", 2, R.drawable.zombie, "Animate", 2, "Ambush[1]", 6, 1, "5-15");
                Player tempPlayer = animusPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    animusPlayer.unitList.add(tempUnit);
                    animusPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(antibytePlayer.isTurn == turnCounter && isReinforcement) {
                // Make SPK Drone
                Unit tempUnit = new Unit("SPK Drone", 3, R.drawable.spk_drone, "Antibyte", 3, "Ambush[1]", 6, 1, "26-17");
                Player tempPlayer = antibytePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    antibytePlayer.unitList.add(tempUnit);
                    antibytePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(elementalPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Aerode
                Unit tempUnit = new Unit("Aerode", 4, R.drawable.aerode, "Elemental", 4, "Flying, Blitzkrieg[1]", 6, 1, "25-6");
                Player tempPlayer = elementalPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    elementalPlayer.unitList.add(tempUnit);
                    elementalPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(grotianPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Goblin
                Unit tempUnit = new Unit("Goblin", 4, R.drawable.goblin, "Grotian", 4, "Flying, Blitzkrieg[1]", 6, 1, "13-9");
                Player tempPlayer = grotianPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    grotianPlayer.unitList.add(tempUnit);
                    grotianPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(hivePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Aent
                Unit tempUnit = new Unit("Aent", 2, R.drawable.aent, "Hivian", 2, "Ambush[1]", 6, 1, "4-5");
                Player tempPlayer = hivePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    hivePlayer.unitList.add(tempUnit);
                    hivePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(humanityPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Zombie
                Unit tempUnit = new Unit("Soldier", 3, R.drawable.soldier, "Human", 3, "Blitzkrieg[1]", 6, 1, "13-16");
                Player tempPlayer = humanityPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    humanityPlayer.unitList.add(tempUnit);
                    humanityPlayer.resources -= tempUnit.resourceCost;
                }
            }
        }
        else if(view == standardCard) {
            if(animusPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Wraith
                Unit tempUnit = new Unit("Wraith", 6, R.drawable.wraith, "Animate", 4, "Flying, Blitzkrieg[1]", 8, 2, "5-15");
                Player tempPlayer = animusPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    animusPlayer.unitList.add(tempUnit);
                    animusPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(antibytePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Hornet
                Unit tempUnit = new Unit("Hornet", 6, R.drawable.hornet, "Antibyte", 3, "Flying, Blitzkrieg[1]", 8, 2, "26-17");
                Player tempPlayer = antibytePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    antibytePlayer.unitList.add(tempUnit);
                    antibytePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(elementalPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Pyrode
                Unit tempUnit = new Unit("Pyrode", 5, R.drawable.pyrode, "Elemental", 3, "Ambush[1]", 8, 2, "25-6");
                Player tempPlayer = elementalPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    elementalPlayer.unitList.add(tempUnit);
                    elementalPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(grotianPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Ogre
                Unit tempUnit = new Unit("Ogre", 4, R.drawable.ogre, "Grotian", 2, "Ambush[1]", 8, 2, "13-9");
                Player tempPlayer = grotianPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    grotianPlayer.unitList.add(tempUnit);
                    grotianPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(hivePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Spyder
                Unit tempUnit = new Unit("Spyder", 5, R.drawable.spyder, "Hivian", 3, "Blitzkrieg[1]", 8, 2, "4-5");
                Player tempPlayer = hivePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    hivePlayer.unitList.add(tempUnit);
                    hivePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(humanityPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Mercenary
                Unit tempUnit = new Unit("Mercenary", 3, R.drawable.mercenary, "Human", 2, "Ambush[1]", 8, 2, "13-16");
                Player tempPlayer = humanityPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    humanityPlayer.unitList.add(tempUnit);
                    humanityPlayer.resources -= tempUnit.resourceCost;
                }
            }
        }
        else if(view == strongCard) {
            if(animusPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Skeleton
                Unit tempUnit = new Unit("Skeleton", 7, R.drawable.skeleton, "Animate", 3, "Leadership[1]", 10, 2, "5-15");
                Player tempPlayer = animusPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    animusPlayer.unitList.add(tempUnit);
                    animusPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(antibytePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Titan
                Unit tempUnit = new Unit("Titan", 6, R.drawable.titan, "Antibyte", 2, "Leadership[1]", 10, 2, "26-17");
                Player tempPlayer = antibytePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    antibytePlayer.unitList.add(tempUnit);
                    antibytePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(elementalPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Geode
                Unit tempUnit = new Unit("Geode", 6, R.drawable.geode, "Elemental", 2, "Leadership[1]", 10, 2, "25-6");
                Player tempPlayer = elementalPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    elementalPlayer.unitList.add(tempUnit);
                    elementalPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(grotianPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Orc
                Unit tempUnit = new Unit("Orc", 7, R.drawable.orc, "Grotian", 3, "Leadership[1]", 10, 2, "13-9");
                Player tempPlayer = grotianPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    grotianPlayer.unitList.add(tempUnit);
                    grotianPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(hivePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Great Mantis
                Unit tempUnit = new Unit("Great Mantis", 8, R.drawable.great_mantis, "Hivian", 4, "Flying, Leadership[1]", 10, 2, "4-5");
                Player tempPlayer = hivePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    hivePlayer.unitList.add(tempUnit);
                    hivePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(humanityPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Assassin
                Unit tempUnit = new Unit("Assassin", 8, R.drawable.assassin, "Human", 4, "Flying, Leadership[1]", 10, 2, "13-16");
                Player tempPlayer = humanityPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    humanityPlayer.unitList.add(tempUnit);
                    humanityPlayer.resources -= tempUnit.resourceCost;
                }
            }
        }
        else if(view == heroCard) {
            if(animusPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Fade
                Unit tempUnit = new Unit("Fade", 20, R.drawable.fade, "Animate Hero", 5, "Flying, Leadership[2]", 12, 4, "5-15");
                Player tempPlayer = animusPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    animusPlayer.unitList.add(tempUnit);
                    animusPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(antibytePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Kristoff
                Unit tempUnit = new Unit("Kristoff", 20, R.drawable.kristoff, "Antibyte Hero", 5, "Flying, Leadership[2]", 12, 4, "26-17");
                Player tempPlayer = antibytePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    antibytePlayer.unitList.add(tempUnit);
                    antibytePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(elementalPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Metamorphous
                Unit tempUnit = new Unit("Metamorphous", 20, R.drawable.metamorphous, "Elemental Hero", 5, "Flying, Leadership[2]", 12, 4, "25-6");
                Player tempPlayer = elementalPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    elementalPlayer.unitList.add(tempUnit);
                    elementalPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(grotianPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Toreg
                Unit tempUnit = new Unit("Toreg", 20, R.drawable.toreg, "Grotian Hero", 5, "Flying, Leadership[2]", 12, 4, "13-9");
                Player tempPlayer = grotianPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    grotianPlayer.unitList.add(tempUnit);
                    grotianPlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(hivePlayer.isTurn == turnCounter && isReinforcement) {
                // Make Xythox
                Unit tempUnit = new Unit("Xythox", 20, R.drawable.xythox, "Hivian Hero", 5, "Flying, Leadership[2]", 12, 4, "4-5");
                Player tempPlayer = hivePlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    hivePlayer.unitList.add(tempUnit);
                    hivePlayer.resources -= tempUnit.resourceCost;
                }
            }
            else if(humanityPlayer.isTurn == turnCounter && isReinforcement) {
                // Make Alpha Kanein
                Unit tempUnit = new Unit("Alpha Kanein", 20, R.drawable.alpha_kanein, "Human Hero", 5, "Flying, Leadership[2]", 12, 4, "13-16");
                Player tempPlayer = humanityPlayer;

                if(inflateUnit(tempPlayer, tempUnit)) {
                    humanityPlayer.unitList.add(tempUnit);
                    humanityPlayer.resources -= tempUnit.resourceCost;
                }
            }
        }
    }
    // Inflates Units
    public boolean inflateUnit(Player player, Unit unit) {
        // Inflatable Unit Image
        ImageView unitImage = (ImageView) unitLayout.inflate(R.layout.unit_view, null);

        // Place the Unit
        if(player.resources >= unit.resourceCost) {
            // Manage the Unit
            unitImage.setImageResource(unit.imageID);

            // Checks Spawn Plot's Occupancy
            float[] tempArr = checkOccupancy(unit.position, unit.points);

            if(tempArr[0] != 100) {
                unitImage.setX(tempArr[0]);
                unitImage.setY(tempArr[1]);

                // Inflate the unit and add it to the list.
                addUnitImage(player, unitImage);
                battlefield.addView(unitImage, 0);

                return true;
            }
            else {
                messageBoardText.setText("Spawn Plot is Full");

                return false;
            }
        }
        else {
            messageBoardText.setText("Insufficient Resources");

            return false;
        }
    }
    // Removes Virtual Units
    public void removeUnit(Player ruPlayer, int ruCellPos) {
        if(animusPlayer == ruPlayer) {
            animusPlayer.unitList.remove(ruCellPos);
        }
        else if(antibytePlayer == ruPlayer) {
            antibytePlayer.unitList.remove(ruCellPos);
        }
        else if(elementalPlayer == ruPlayer) {
            elementalPlayer.unitList.remove(ruCellPos);
        }
        else if(grotianPlayer == ruPlayer) {
            grotianPlayer.unitList.remove(ruCellPos);
        }
        else if(hivePlayer == ruPlayer) {
            hivePlayer.unitList.remove(ruCellPos);
        }
        else if(humanityPlayer == ruPlayer) {
            humanityPlayer.unitList.remove(ruCellPos);
        }
    }
    // Managing Unit Images
    // Adds Image View for Unit
    public void addUnitImage(Player currentPlayer, ImageView unit) {
        if(currentPlayer == animusPlayer) {
            animusUnits.add(unit);
        }
        else if(currentPlayer == antibytePlayer) {
            antibyteUnits.add(unit);
        }
        else if(currentPlayer == elementalPlayer) {
            elementalUnits.add(unit);
        }
        else if(currentPlayer == grotianPlayer) {
            grotianUnits.add(unit);
        }
        else if(currentPlayer == hivePlayer) {
            hiveUnits.add(unit);
        }
        else if(currentPlayer == humanityPlayer) {
            humanityUnits.add(unit);
        }
    }
    // Moves Image View for Unit
    public void moveUnitImage(int muiCellPos, float[] muiArr) {
        ImageView tempUnitView = null;

        if(animusPlayer.isTurn == turnCounter && animusPlayer.playerType != 0) {
            tempUnitView = animusUnits.get(muiCellPos);
        }
        else if(antibytePlayer.isTurn == turnCounter && antibytePlayer.playerType != 0) {
            tempUnitView = antibyteUnits.get(muiCellPos);
        }
        else if(elementalPlayer.isTurn == turnCounter && elementalPlayer.playerType != 0) {
            tempUnitView = elementalUnits.get(muiCellPos);
        }
        else if(grotianPlayer.isTurn == turnCounter && grotianPlayer.playerType != 0) {
            tempUnitView = grotianUnits.get(muiCellPos);
        }
        else if(hivePlayer.isTurn == turnCounter && hivePlayer.playerType != 0) {
            tempUnitView = hiveUnits.get(muiCellPos);
        }
        else if(humanityPlayer.isTurn == turnCounter && humanityPlayer.playerType != 0) {
            tempUnitView = humanityUnits.get(muiCellPos);
        }
        tempUnitView.setX(muiArr[0]);
        tempUnitView.setY(muiArr[1]);
    }
    // Removes Image View for Unit.
    public void removeUnitImage(Player ruiPlayer, int ruiCellPos) {
        if(animusPlayer == ruiPlayer) {
            ImageView animate = animusUnits.get(ruiCellPos);
            battlefield.removeView(animate);

            animusUnits.remove(ruiCellPos);
        }
        else if(antibytePlayer == ruiPlayer) {
            ImageView antibyte = antibyteUnits.get(ruiCellPos);
            battlefield.removeView(antibyte);

            antibyteUnits.remove(ruiCellPos);
        }
        else if(elementalPlayer == ruiPlayer) {
            ImageView elemental = elementalUnits.get(ruiCellPos);
            battlefield.removeView(elemental);

            elementalUnits.remove(ruiCellPos);
        }
        else if(grotianPlayer == ruiPlayer) {
            ImageView grotian = grotianUnits.get(ruiCellPos);
            battlefield.removeView(grotian);

            grotianUnits.remove(ruiCellPos);
        }
        else if(hivePlayer == ruiPlayer) {
            ImageView hivian = hiveUnits.get(ruiCellPos);
            battlefield.removeView(hivian);

            hiveUnits.remove(ruiCellPos);
        }
        else if(humanityPlayer == ruiPlayer) {
            ImageView human = humanityUnits.get(ruiCellPos);
            battlefield.removeView(human);

            humanityUnits.remove(ruiCellPos);
        }
    }
    // Managing Virtual Plots
    // Adds occupancy points to a plot.
    public void updatePlotsPlus(String posCode, int pts) {
        int[] converted = posCodeToInts(posCode);

        int x = converted[0];
        int y = converted[1];

        if((vm.getVirtualPlots()[x][y].points + pts) <= 4) {
            vm.getVirtualPlots()[x][y].points += pts;
        }
    }
    // Rearranges Image Views in a plot.
    public void plotRearranger(String posCode) {
        int[] converted = posCodeToInts(posCode);

        int x = converted[0];
        int y = converted[1];

        float[] posArr;

        vm.getVirtualPlots()[x][y].points = 0;

        if(animusPlayer.isTurn == turnCounter) {
            for(int i = 0; i < animusPlayer.unitList.size(); i++) {
                Unit animate = animusPlayer.unitList.get(i);

                if(animate.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, animate.points);

                    animusUnits.get(i).setX(posArr[0]);
                    animusUnits.get(i).setY(posArr[1]);
                }
            }
        }
        else if(antibytePlayer.isTurn == turnCounter) {
            for(int i = 0; i < antibytePlayer.unitList.size(); i++) {
                Unit antibyte = antibytePlayer.unitList.get(i);

                if(antibyte.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, antibyte.points);

                    antibyteUnits.get(i).setX(posArr[0]);
                    antibyteUnits.get(i).setY(posArr[1]);
                }
            }
        }
        else if(elementalPlayer.isTurn == turnCounter) {
            for(int i = 0; i < elementalPlayer.unitList.size(); i++) {
                Unit elemental = elementalPlayer.unitList.get(i);

                if(elemental.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, elemental.points);

                    elementalUnits.get(i).setX(posArr[0]);
                    elementalUnits.get(i).setY(posArr[1]);
                }
            }
        }
        else if(grotianPlayer.isTurn == turnCounter) {
            for(int i = 0; i < grotianPlayer.unitList.size(); i++) {
                Unit grotian = grotianPlayer.unitList.get(i);

                if(grotian.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, grotian.points);

                    grotianUnits.get(i).setX(posArr[0]);
                    grotianUnits.get(i).setY(posArr[1]);
                }
            }
        }
        else if(hivePlayer.isTurn == turnCounter) {
            for(int i = 0; i < hivePlayer.unitList.size(); i++) {
                Unit hivian = hivePlayer.unitList.get(i);

                if(hivian.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, hivian.points);

                    hiveUnits.get(i).setX(posArr[0]);
                    hiveUnits.get(i).setY(posArr[1]);
                }
            }
        }
        else if(humanityPlayer.isTurn == turnCounter) {
            for(int i = 0; i < humanityPlayer.unitList.size(); i++) {
                Unit human = humanityPlayer.unitList.get(i);

                if(human.position.equals(posCode)) {
                    posArr = checkOccupancy(posCode, human.points);

                    humanityUnits.get(i).setX(posArr[0]);
                    humanityUnits.get(i).setY(posArr[1]);
                }
            }
        }
    }
    // Turns position code string into two ints.
    public int[] posCodeToInts(String posCode) {
        // Breaks Down Position Code for Unit Placement
        char[] charArr = posCode.toCharArray();

        String tempString1 = "";
        String tempString2 = "";
        Boolean tempBoolean = false;

        for(int i = 0; i < charArr.length; i++) {
            char tempChar = charArr[i];

            if(tempChar == '-') {
                tempBoolean = true;
            }
            else if(!tempBoolean) {
                tempString1 += charArr[i];
            }
            else if(tempBoolean) {
                tempString2 += charArr[i];
            }
        }
        int x = Integer.parseInt(tempString1);
        int y = Integer.parseInt((tempString2));

        int[] arr = {x, y};

        return arr;
    }
    // Pans Virtual Plots with the Game Board
    public void positionPlots() {
        for(int i = 0; i < 30; i++) {
            for(int j = 0; j < 20; j++) {
                vm.getVirtualPlots()[i][j].positionPlot(plotX, plotY, i, j);
            }
        }
    }
    // Pinches Virtual Plots with the Game Board
    public void scalePlots(float scaleFactor) {
        // Find the way back to the original top left of the view.
        float tempShiftX = (scaleFactor * plotX);
        float tempShiftY = (scaleFactor * plotY);

        for(int i = 0; i < 30; i++) {
            for(int j = 0; j < 20; j++) {
                vm.getVirtualPlots()[i][j].scalePlot(tempShiftX, tempShiftY);
            }
        }
    }
}
