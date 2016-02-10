package model.dao;

import controller.persistence.UndeclaredEntityException;
import model.Link;
import org.json.JSONObject;
import java.util.function.Function;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class LinkDAO extends GenericDAO<Link> {
    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "ti",
            "ur",
            "de",
            "fa",
            "fl",
            "ra",
            "da",
            "ho"
    };



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public LinkDAO() throws UndeclaredEntityException {
        super(Link.class);
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
    protected Function<Link, JSONObject> getJSONFromEntity() {
        return entity -> new JSONObject()
                .put(FIELD_SET[0], entity.getId())
                .put(FIELD_SET[1], entity.getTitle())
                .put(FIELD_SET[2], entity.getUrl())
                .put(FIELD_SET[3], entity.getDescription())
                .put(FIELD_SET[4], entity.isFavorite())
                .put(FIELD_SET[5], entity.isFlagged())
                .put(FIELD_SET[6], entity.getRating())
                .put(FIELD_SET[7], entity.getDate())
                .put(FIELD_SET[8], entity.getHostId_FK());
    }

    @Override
    protected Function<JSONObject, Link> getEntityFromJSON() {
        return jsonObject -> new Link(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getString(FIELD_SET[2]),
                jsonObject.getString(FIELD_SET[3]),
                jsonObject.getBoolean(FIELD_SET[4]),
                jsonObject.getBoolean(FIELD_SET[5]),
                jsonObject.getDouble(FIELD_SET[6]),
                jsonObject.getLong(FIELD_SET[7]),
                jsonObject.getLong(FIELD_SET[8])
        );
    }
}
