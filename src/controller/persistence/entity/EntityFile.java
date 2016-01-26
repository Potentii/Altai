package controller.persistence.entity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents the content of each Entity file
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public final class EntityFile extends JSONObject {


    public EntityFile(Metadata metadata, JSONArray content){
        setMetadata(metadata);
        setContent(content);
    }
    public EntityFile(String str){
        super(str);
    }



    public void registerLastID(long id){
        // TODO try if it works:
        getMetadata().setLastId(id);
    }

    public void appendContent(JSONObject jsonObject){
        // TODO try if it works:
        getContent().put(jsonObject);
    }

    public void updateContent(int index, JSONObject newJSONObject){
        // TODO try if it works:
        getContent().put(index, newJSONObject);
    }

    public void deleteContent(int index){
        // TODO try if it works:
        getContent().remove(index);
    }



    public Metadata getMetadata() {
        return (Metadata) getJSONObject("metadata");
    }
    public void setMetadata(Metadata metadata) {
        put("metadata", metadata);
    }

    public JSONArray getContent() {
        return getJSONArray("content");
    }
    public void setContent(JSONArray content) {
        put("content", content);
    }
}
