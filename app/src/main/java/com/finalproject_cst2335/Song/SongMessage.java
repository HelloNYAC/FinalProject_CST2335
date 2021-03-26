package com.finalproject_cst2335.Song;

public class SongMessage {
    int id;
    String songID;
    String songTitle;
    String artistID;
    String artistName;

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