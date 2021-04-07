package com.finalproject_cst2335.Song;


public class SongMessage {
    int id;
    String songID;
    String songTitle;
    String artistID;
    String artistName;

    public SongMessage(){}
    /**
     *
     * @param id
     * @param songID
     * @param songTitle
     * @param artistID
     * @param artistName
     */
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

    /**
     *
     * @return
     */
    public String getSongID() {
        return songID;
    }

    /**
     *
     * @param songID
     */
    public void setSongId(String songID){
        this.songID = songID;
    }

    /**
     *
     * @return
     */
    public String getSongTitle(){
        return songTitle;
    }

    /**
     *
     * @param songTitle
     */
    public void getSetTitle(String songTitle){
        this.songTitle= songTitle;
    }

    /**
     *
     * @return
     */
    public String getArtistID(){
        return artistID;
    }

    /**
     *
     * @param artistID
     */
    public void setArtistid(String artistID){
        this.artistID = artistID;
    }

    /**
     *
     * @return
     */
    public String getArtistName(){
        return artistName;
    }

    /**
     *
     * @param artistName
     */
    public void setArtistName(String artistName){
        this.artistName = artistName;
    }
}