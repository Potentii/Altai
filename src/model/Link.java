package model;

import controller.persistence.annotation.Entity;

/**
 *
 * {@code rating} varies between 0 and 1.
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
@Entity(sourceName = "LinkSource")
public class Link {
    private long id;
    private String title;
    private String url;
    private String description;
    private boolean favorite;
    private boolean flagged;
    private double rating;
    private long date;

    private long hostId_FK;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public Link(long id, String title, String url, String description, boolean favorite, boolean flagged, double rating, long date, long hostId_FK) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
        this.favorite = favorite;
        this.flagged = flagged;
        this.rating = rating;
        this.date = date;
        this.hostId_FK = hostId_FK;
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

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFlagged() {
        return flagged;
    }
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
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

    public long getHostId_FK() {
        return hostId_FK;
    }
    public void setHostId_FK(long hostId_FK) {
        this.hostId_FK = hostId_FK;
    }
}
