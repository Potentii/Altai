package model.dao;

import controller.persistence.UndeclaredEntityException;
import model.Category;
import model.Link;
import org.json.JSONObject;

import java.util.function.Function;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class CategoryDAO extends GenericDAO<Category> {
    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "ti",
            "da"
    };



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryDAO() throws UndeclaredEntityException {
        super(Category.class);
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
    protected Function<Category, JSONObject> getJSONFromEntity() {
        return entity -> new JSONObject()
                .put(FIELD_SET[0], entity.getId())
                .put(FIELD_SET[1], entity.getTitle())
                .put(FIELD_SET[2], entity.getDate());
    }

    @Override
    protected Function<JSONObject, Category> getEntityFromJSON() {
        return jsonObject -> new Category(
                jsonObject.getLong(FIELD_SET[0]),
                jsonObject.getString(FIELD_SET[1]),
                jsonObject.getLong(FIELD_SET[2])
        );
    }
}
