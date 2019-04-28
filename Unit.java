package com.sheltoidusa.universaldominationtheboardgame;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

// This is the virtual representation of the units in the game.
public class Unit extends AppCompatActivity implements Parcelable {
    // Unit's Name
    public String name;
    // Unit's ResourceMound Cost
    public int resourceCost;
    // Image's ID
    public int imageID;
    // Unit's Race
    public String species;
    // Unit's Base Mobility
    public int mobility;
    // Unit's Ability
    public String ability;
    // Unit's Power
    public int power;

    // Unit's Occupancy Points
    public int points;
    // Unit's Plot Position
    public String position;

    // Parcelable Constructor
    public Unit(Parcel in) {
        name = in.readString();
        resourceCost = in.readInt();
        imageID = in.readInt();
        species = in.readString();
        mobility = in.readInt();
        ability = in.readString();
        power = in.readInt();

        points = in.readInt();
        position = in.readString();
    }
    // Needed for putExtra
    @Override
    public int describeContents() {
        return 0;
    }
    // Needed for putEstra
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(resourceCost);
        dest.writeInt(imageID);
        dest.writeString(species);
        dest.writeInt(mobility);
        dest.writeString(ability);
        dest.writeInt(power);

        dest.writeInt(points);
        dest.writeString(position);
    }
    public static final Parcelable.Creator<Unit> CREATOR = new Parcelable.Creator<Unit>() {
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
    // Explicit Constructor
    public Unit(String n, int rc, int id, String s, int m, String a, int pwr, int pts, String pos) {
        name = n;
        resourceCost = rc;
        imageID = id;
        species = s;
        mobility = m;
        ability = a;
        power = pwr;

        points = pts;
        position = pos;
    }
}