package com.dweb_x.movielist;


import java.io.Serializable;

/**
 * @author : D.Carruthers
 * @version : 1.0
 * Date: 28/02/2015.
 * email: dave@dweb-x.com
 */
public class MovieEntry implements Serializable{

    private String title;
    private TYPE type;
    private String storyOutline;
    private RATING rating;
    private String language;
    private int runningTime;

    /**
     * TYPE of { ACTION, DRAMA, COMEDY, ROMANCE, THRILLER }
     */
    public static enum TYPE {
        ACTION, DRAMA, COMEDY, ROMANCE, THRILLER
    }

    /**
     *  RATING of { _U, _PG, _12, _15, _18 }
     */
    public static enum RATING {
        _U, _PG, _12, _15, _18
    }

    /**
     * Type of movie
     * @return TYPE from enum list
     */
    public TYPE getType() {
        return type;
    }

    /**
     * set movie type to one from enum list
     * @param type from enum list
     */
    public void setType(TYPE type) {
        this.type = type;
    }

    /**
     * Movie rating from enum list
     * @return rating
     */
    public RATING getRating() {
        return rating;
    }

    /**
     * set the rating
     * @param rating from enum list RATING
     */
    public void setRating(RATING rating) {
        this.rating = rating;
    }

    /**
     * Movie title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * set the movie title
     * @param title of movie
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Outline of what the movie is about
     * @return big string story outline
     */
    public String getStoryOutline() {
        return storyOutline;
    }

    /**
     * Set the story outline
     * @param storyOutline as long string
     */
    public void setStoryOutline(String storyOutline) {
        this.storyOutline = storyOutline;
    }

    /**
     * Language the movie is in
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * set the movie language
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * running time in minutes
     * @return int minutes
     */
    public int getRunningTime() {
        return runningTime;
    }

    /**
     * set the running time in minutes
     * @param runningTime in mins
     */
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Constructor
     */
    public MovieEntry() {

    }

    /**
     *
     * @param title movie title
     * @param type type of movie
     * @param storyOutline outline of the story
     * @param rating rating
     * @param language language
     * @param runningTime running time in minutes
     */
    public MovieEntry(String title, TYPE type, String storyOutline,
                      RATING rating, String language, int runningTime){
        this.title = title;
        this.type = type;
        this.storyOutline = storyOutline;
        this.rating = rating;
        this.language = language;
        this.runningTime = runningTime;
    }

    /**
     *
     * @return entry as string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append("\n");
        sb.append("Type: ").append(type).append("\n");
        sb.append("Story Outline: \n").append(storyOutline).append("\n");
        sb.append("Rating: ").append(rating.toString().substring(1)).append("\n");
        sb.append("Language: ").append(language).append("\n");
        sb.append("Running Time: ").append(runningTime).append("mins\n");
        return sb.toString();
    }
}

