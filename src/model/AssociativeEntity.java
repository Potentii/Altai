package model;


/**
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
public abstract class AssociativeEntity {
    private long id;
    private long primaryId;
    private long secondaryId;
    private long date;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public AssociativeEntity(long id, long primaryId, long secondaryId, long date) {
        this.id = id;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
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

    public long getPrimaryId() {
        return primaryId;
    }
    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public long getSecondaryId() {
        return secondaryId;
    }
    public void setSecondaryId(long secondaryId) {
        this.secondaryId = secondaryId;
    }

    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
}
