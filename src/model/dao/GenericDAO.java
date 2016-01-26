package model.dao;

import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import controller.persistence.entity.EntityTransaction;
import model.dao.callback.*;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * DAO's generic implementation.
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public abstract class GenericDAO<T> implements DAO<T> {
    protected EntityTransaction<T> entityTransaction;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public GenericDAO(Class<T> clazz) throws UndeclaredEntityException{
        entityTransaction = PersistenceManager.getInstance().getEntityTransaction(clazz);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Abstract methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     * Returns the Entity's id key.
     */
    protected abstract String getIdKey();
    /**
     * Defines how to get a JSONObject representation of an Entity.
     */
    protected abstract Function<T, JSONObject> getJSONFromEntity();
    /**
     * Defines how to get an Entity instance from a JSONObject:
     */
    protected abstract Function<JSONObject, T> getEntityFromJSON();



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DAO methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void create(T entity, CreateDAOCallback createDAOCallback) {
        entityTransaction.create(
                getJSONFromEntity().apply(entity),
                getIdKey(),
                createDAOCallback
        );
    }

    @Override
    public void retrieveMultiple(Predicate<T> condition, Comparator<T> sorting, RetrieveMultipleDAOCallback<T> retrieveMultipleDAOCallback) {
        entityTransaction.retrieveMultiple(
                condition,
                sorting,
                getEntityFromJSON(),
                retrieveMultipleDAOCallback
        );
    }

    @Override
    public void retrieve(long id, RetrieveDAOCallback<T> retrieveDAOCallback) {
        entityTransaction.retrieve(
                id,
                getIdKey(),
                getEntityFromJSON(),
                retrieveDAOCallback
        );
    }

    @Override
    public void update(T newEntity, UpdateDAOCallback updateDAOCallback) {
        entityTransaction.update(
                getJSONFromEntity().apply(newEntity),
                getIdKey(),
                updateDAOCallback
        );
    }

    @Override
    public void delete(long id, DeleteDAOCallback deleteDAOCallback) {
        entityTransaction.delete(
                id,
                getIdKey(),
                deleteDAOCallback
        );
    }
}
