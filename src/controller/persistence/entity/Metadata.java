package controller.persistence.entity;

import org.json.JSONObject;

/**
 * Has additinal information about the content of an Entity file.
 * {@code lastId} has to be updated only when CREATING new instances.
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public class Metadata extends JSONObject {


    public Metadata(long lastId){
        setLastId(lastId);
    }


    public long getNewId() {
        return getLastId()+1;
    }



    public long getLastId() {
        return getLong("lastId");
    }
    public void setLastId(long lastId) {
        put("lastId", lastId);
    }
}
