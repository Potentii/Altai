package model;

import controller.persistence.annotation.Entity;

/**
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
@Entity(sourceName = "CategorySource")
public class Category {
    private long id;
    private String title;
    private long date;



    public Category(long id, String title, long date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }



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

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
