package model;

import controller.persistence.annotation.Entity;

/**
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
@Entity(sourceName = "HostSource")
public class Host {
    private long id;
    private String title;
    private String url;
    private String urlPattern;
    private long date;

    private long imgId_FK;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public Host(long id, String title, String url, String urlPattern, long date, long imgId_FK) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.urlPattern = urlPattern;
        this.date = date;
        this.imgId_FK = imgId_FK;
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

    public String getUrlPattern() {
        return urlPattern;
    }
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }

    public long getImgId_FK() {
        return imgId_FK;
    }
    public void setImgId_FK(long imgId_FK) {
        this.imgId_FK = imgId_FK;
    }
}
