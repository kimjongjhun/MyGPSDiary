package com.zlaqh.mygpsdiary;

/**
 * Created by Me on 02/02/17.
 */

public class Location {
    String name, note;
    double lat, lng;

    String date;

    public Location () {

    }

    public Location(String name, String note, String date) {
        this.name = name;
        this.note = note;
        this.date = date;
    }

}
