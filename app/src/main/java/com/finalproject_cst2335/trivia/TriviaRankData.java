package com.finalproject_cst2335.trivia;

public class TriviaRankData {
    private long id;
    private String playerName, gameLevel;
    private int gameScore;

    /**
     * no-arg constructor
     */
    public TriviaRankData(){

    }

    /**
     * constructor with 3-args of palyer name, gave level and game score
     * @param playerName
     * @param gameLevel
     * @param gameScore
     */
    public TriviaRankData(String playerName, String gameLevel, int gameScore){
        this.playerName = playerName;
        this.gameLevel = gameLevel;
        this.gameScore = gameScore;
    }

    /**
     * getter of game level
     * @return
     */
    public String getGameLevel() {
        return gameLevel;
    }

    /**
     * setter of game level
     * @param gameLevel
     */
    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * getter of id
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * setter of id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getter of player name
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * setter to set player name
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * getter to get score
     * @return
     */
    public int getGameScore() {
        return gameScore;
    }

    /**
     * setter to set score
     * @param gameScore
     */
    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }
}
