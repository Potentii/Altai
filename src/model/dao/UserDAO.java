package model.dao;

import controller.persistence.entity.EntityTransaction;
import model.dao.callback.*;
import model.User;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public class UserDAO implements DAO<User> {
    private EntityTransaction<User> entityTransaction;

    /**
     * The Entity's set of fields' names
     */
    private static final String[] FIELD_SET = {
            "id",
            "name",
            "password"
    };

    /**
     * Defines how to get an Entity instance from a JSONObject:
     */
    private Function<JSONObject, User> getEntityFromJSON =
            jsonObject -> new User(
                    jsonObject.getLong(FIELD_SET[0]),
                    jsonObject.getString(FIELD_SET[1]),
                    jsonObject.getString(FIELD_SET[2]));

    /**
     * Defines how to get a JSONObject representation of an Entity:
     */
    private Function<User, JSONObject> getJSONFromEntity =
            entity -> new JSONObject()
                    .put(FIELD_SET[0], entity.getId())
                    .put(FIELD_SET[1], entity.getName())
                    .put(FIELD_SET[2], entity.getPassword());



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public UserDAO(EntityTransaction<User> entityTransaction){
        this.entityTransaction = entityTransaction;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DAO methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void create(User entity, CreateDAOCallback createDAOCallback) {
        entityTransaction.create(
                getJSONFromEntity.apply(entity),
                FIELD_SET[0],
                createDAOCallback
        );
    }


    @Override
    public void retrieveMultiple(Predicate<User> condition, Comparator<User> sorting, RetrieveMultipleDAOCallback<User> retrieveMultipleDAOCallback) {
        entityTransaction.retrieveMultiple(
                condition,
                sorting,
                getEntityFromJSON,
                retrieveMultipleDAOCallback
        );
    }


    @Override
    public void retrieve(long id, RetrieveDAOCallback<User> retrieveDAOCallback){
        entityTransaction.retrieve(
                id,
                FIELD_SET[0],
                getEntityFromJSON,
                retrieveDAOCallback
        );
    }


    @Override
    public void update(User newEntity, UpdateDAOCallback updateDAOCallback) {
        entityTransaction.update(
                getJSONFromEntity.apply(newEntity),
                FIELD_SET[0],
                updateDAOCallback
        );
    }


    @Override
    public void delete(User entity, DeleteDAOCallback deleteDAOCallback) {
        entityTransaction.delete(
                entity.getId(),
                FIELD_SET[0],
                deleteDAOCallback
        );
    }
}
