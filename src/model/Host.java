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
    private String imgPath;
    private long date;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public Host(long id, String title, String url, String urlPattern, String imgPath, long date) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.urlPattern = urlPattern;
        this.imgPath = imgPath;
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

    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
