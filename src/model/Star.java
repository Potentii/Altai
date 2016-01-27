package model;

import controller.persistence.annotation.Entity;

/**
 * {@code rating} varies between 0 and 1.
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
@Entity(sourceName = "StarSource")
public class Star {
    private long id;
    private String title;
    private String description;
    private double rating;
    private long date;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public Star(long id, String title, String description, double rating, long date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
