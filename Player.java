package com.sheltoidusa.universaldominationtheboardgame;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

// This class holds all necessary player data.
public class Player extends AppCompatActivity implements Parcelable {
    // Player's Race
    public String race;

    // Type of PlayerType
    public int playerType;

    // PlayerType Resources
    public int resources;

    // Units
    public ArrayList<Unit> unitList;

    // Determines PlayerType's Turn
    public int isTurn;

    // Determine if player has a base.
    public int isDBNO;

    // Parcel Constructor
    public Player(Parcel in) {
        race = in.readString();

        playerType = in.readInt();
        resources = in.readInt();

        unitList = in.readArrayList(Unit.class.getClassLoader());

        isTurn = in.readInt();
        isDBNO = in.readInt();
    }
    // Necessary for putExtra.
    @Override
    public int describeContents() {
        return 0;
    }
    // Necessary for putExtra.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(race);

        dest.writeInt(playerType);
        dest.writeInt(resources);

        dest.writeList(unitList);

        dest.writeInt(isTurn);
        dest.writeInt(isDBNO);
    }
    // Necessary for putExtra
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    // Explicit Constructor
    public Player(String s, int pt, int r, ArrayList<Unit> units, int turn, int DBNO) {
        // Player's Race
        race = s;

        // Player Type Name
        playerType = pt;

        // PlayerType's Resources
        resources = r;

        // PlayerType's Units
        unitList = units;

        // PlayerType's Turn?
        isTurn = turn;

        // PlayerType has Base?
        isDBNO = DBNO;
    }
}
