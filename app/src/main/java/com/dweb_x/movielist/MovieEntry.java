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
    private String type;
    private String storyOutline;
    private String rating;
    private String language;
    private int runningTime;

    /**
     * Type of movie
     * @return String from enum list
     */
    public String getType() {
        return type;
    }

    /**
     * set movie type to one from enum list
     * @param type from enum list
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Movie rating from enum list
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * set the rating
     * @param rating rating
     */
    public void setRating(String rating) {
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
     * @param language language
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
        //init through other constructor
        this("","","","","",0);
    }

    /**
     * Constructor
     * @param title movie title
     * @param type type of movie
     * @param storyOutline outline of the story
     * @param rating rating
     * @param language language
     * @param runningTime running time in minutes
     */
    public MovieEntry(String title, String type, String storyOutline,
                      String rating, String language, int runningTime){
        this.title = title;
        this.type = type;
        this.storyOutline = storyOutline;
        this.rating = rating;
        this.language = language;
        this.runningTime = runningTime;
    }

    /**
     *  Returns HTML String.
     * @return entry as string
     */
    @Override
    public String toString() {
        return "<b>Title: </b>" + title +
                "<br><b>Type: </b>" + type +
                "<br><b>Story Outline: </b>" + storyOutline +
                "<br><b>Rating: </b>" + rating +
                "<br><b>Language: </b>" + language +
                "<br><b>Running Time: </b>" + runningTime + " mins<br>";
    }
}

