package model.dao;

import controller.persistence.UndeclaredEntityException;
import model.Picture;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class PictureDAO extends GenericDAO<Picture> {
    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "pa",
            "da"
    };



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public PictureDAO() throws UndeclaredEntityException {
        super(Picture.class);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * GenericDAO methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected String getIdKey() {
        return FIELD_SET[0];
    }

    @Override
    protected Function<Picture, JSONObject> getJSONFromEntity() {
        return entity -> new JSONObject()
                .put(FIELD_SET[0], entity.getId())
                .put(FIELD_SET[1], entity.getPath())
                .put(FIELD_SET[2], entity.getDate());
    }

    @Override
    protected Function<JSONObject, Picture> getEntityFromJSON() {
        return jsonObject -> new Picture(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getLong(FIELD_SET[2])
        );
    }
}
