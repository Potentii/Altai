package model.dao;

import controller.persistence.UndeclaredEntityException;
import model.Star;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class StarDAO extends GenericDAO<Star> {
    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "ti",
            "de",
            "ra",
            "da"
    };



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public StarDAO() throws UndeclaredEntityException {
        super(Star.class);
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
    protected Function<Star, JSONObject> getJSONFromEntity() {
        return entity -> new JSONObject()
                .put(FIELD_SET[0], entity.getId())
                .put(FIELD_SET[1], entity.getTitle())
                .put(FIELD_SET[2], entity.getDescription())
                .put(FIELD_SET[3], entity.getRating())
                .put(FIELD_SET[4], entity.getDate());
    }

    @Override
    protected Function<JSONObject, Star> getEntityFromJSON() {
        return jsonObject -> new Star(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getString(FIELD_SET[2]),
                jsonObject.getDouble(FIELD_SET[3]),
                jsonObject.getLong(FIELD_SET[4])
        );
    }
}
