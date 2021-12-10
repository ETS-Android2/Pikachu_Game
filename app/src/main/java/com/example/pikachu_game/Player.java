package com.example.pikachu_game;

import androidx.annotation.NonNull;

public class Player implements Comparable<Player> {

    private String name;
    private int score;
    private double latitude;
    private double longitude;

    public Player() {
        name="";
        score=0;
        this.latitude = 0;
        this.longitude = 0;
    }
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
        this.latitude = 0;
        this.longitude = 0;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLocation( double newLatitude, double newLongitude)
    {
        this.latitude = newLatitude;
        this.longitude = newLongitude;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int compareTo(Player p) {
        int otherScore = p.getScore();
        if(this.score< otherScore)
            return -1;
        else if(this.score> otherScore)
            return 1;
        else
            return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "player name : "+ name+", score : "+ score;
    }
}
