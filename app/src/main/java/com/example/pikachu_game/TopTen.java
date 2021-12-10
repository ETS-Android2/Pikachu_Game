package com.example.pikachu_game;

import java.util.ArrayList;

public class TopTen {

    private ArrayList<Player> players;
    private final int MAX_SIZE =10;

    public TopTen() {
        players = new ArrayList<>();
    }

    public TopTen(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean checkAndAdd(Player newP) {
        for (Player p : players) {
            if (newP.compareTo(p) > 0) {
                players.add(players.indexOf(p), newP);
                if (players.size() > MAX_SIZE)
                    players.remove(MAX_SIZE);
                    return true;
            }
        }
        if(players.size()< MAX_SIZE){
            players.add(newP);
            return true;
        }
        return false;
    }
}
