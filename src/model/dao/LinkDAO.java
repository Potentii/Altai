package model.dao;

import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import controller.persistence.entity.EntityTransaction;
import model.Link;
import model.dao.callback.*;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

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
                .put(FIELD_SET[3], entity.getRating())
                .put(FIELD_SET[4], entity.getDate())
                .put(FIELD_SET[5], entity.getHostId_FK());
    }

    @Override
    protected Function<JSONObject, Link> getEntityFromJSON() {
        return jsonObject -> new Link(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getString(FIELD_SET[2]),
                jsonObject.getDouble(FIELD_SET[3]),
                jsonObject.getLong(FIELD_SET[4]),
                jsonObject.getLong(FIELD_SET[5])
        );
    }
}
