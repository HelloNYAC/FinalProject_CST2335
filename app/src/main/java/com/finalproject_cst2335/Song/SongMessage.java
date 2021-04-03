package com.finalproject_cst2335.Song;

public class SongMessage {
    int id;
    String songID;
    String songTitle;
    String artistID;
    String artistName;

    public SongMessage(){

    }

    public SongMessage(int id, String songID, String songTitle, String artistID, String artistName){
        this.id = id;
        this.songID = songID;
        this.songTitle = songTitle;
        this.artistID = artistID;
        this.artistName = artistName;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public String getSongID() {
        return songID;
    }
    public void setSongId(String songID){
        this.songID = songID;
    }

    public String getSongTitle(){
        return songTitle;
    }

    public void getSetTitle(String songTitle){
        this.songTitle= songTitle;
    }

    public String getArtistID(){
        return artistID;
    }
    public void setArtistid(String artistID){
        this.artistID = artistID;
    }

    public String getArtistName(){
        return artistName;
    }
    public void setArtistName(String artistName){
        this.artistName = artistName;
    }
}