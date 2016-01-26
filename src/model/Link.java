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
    private float rating;
    private long date;

    private long hostId_FK;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public Link(long id, String title, String url, float rating, long date, long hostId_FK) {
        this.id = id;
        this.title = title;
        this.url = url;
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

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
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
