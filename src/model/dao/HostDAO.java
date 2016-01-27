package model.dao;

import controller.persistence.UndeclaredEntityException;
import model.Host;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class HostDAO extends GenericDAO<Host> {
    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "ti",
            "ur",
            "up",
            "da",
            "im"
    };



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public HostDAO() throws UndeclaredEntityException {
        super(Host.class);
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
    protected Function<Host, JSONObject> getJSONFromEntity() {
        return entity -> new JSONObject()
                .put(FIELD_SET[0], entity.getId())
                .put(FIELD_SET[1], entity.getTitle())
                .put(FIELD_SET[2], entity.getUrl())
                .put(FIELD_SET[3], entity.getUrlPattern())
                .put(FIELD_SET[4], entity.getDate())
                .put(FIELD_SET[5], entity.getImgId_FK());
    }

    @Override
    protected Function<JSONObject, Host> getEntityFromJSON() {
        return jsonObject -> new Host(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getString(FIELD_SET[2]),
                jsonObject.getString(FIELD_SET[3]),
                jsonObject.getLong(FIELD_SET[4]),
                jsonObject.getLong(FIELD_SET[5])
        );
    }
}
