package com.finalproject_cst2335.trivia;

public class TriviaRankData {
    private long id;
    private String playerName, gameLevel;
    private int gameScore;

    public TriviaRankData(){

    }

    public TriviaRankData(String playerName, String gameLevel, int gameScore){
        this.playerName = playerName;
        this.gameLevel = gameLevel;
        this.gameScore = gameScore;
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }
}
