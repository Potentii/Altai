package model.dao;

import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import controller.persistence.entity.EntityTransaction;
import model.AssociativeEntity;
import model.dao.callback.*;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class AssociativeEntityDAO<T extends AssociativeEntity> implements DAO<AssociativeEntity> {
    private EntityTransaction<AssociativeEntity> entityTransaction;

    /**
     * The Entity's set of fields' names
     */
    private final String[] FIELD_SET = {
            "id",
            "pId",
            "sId",
            "d"
    };

    /**
     * Defines how to get an Entity instance from a JSONObject:
     */
    private Function<JSONObject, AssociativeEntity> getEntityFromJSON =
            jsonObject -> new AssociativeEntity(
                    jsonObject.getLong(FIELD_SET[0]),
                    jsonObject.getLong(FIELD_SET[1]),
                    jsonObject.getLong(FIELD_SET[2]),
                    jsonObject.getLong(FIELD_SET[3]));

    /**
     * Defines how to get a JSONObject representation of an Entity:
     */
    private Function<AssociativeEntity, JSONObject> getJSONFromEntity =
            entity -> new JSONObject()
                    .put(FIELD_SET[0], entity.getId())
                    .put(FIELD_SET[1], entity.getPrimaryId())
                    .put(FIELD_SET[2], entity.getSecondaryId())
                    .put(FIELD_SET[3], entity.getDate());



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public AssociativeEntityDAO(Class<T> clazz) throws UndeclaredEntityException {
        PersistenceManager persistenceManager = PersistenceManager.getInstance();
        this.entityTransaction = new EntityTransaction<>(persistenceManager.getRootPath() + EAltaiPersistence.ENTITIES_RELATIVE_PATH.getValue() + persistenceManager.getSourceName(clazz) + EAltaiPersistence.ENTITY_DEFAULT_EXTENSION.getValue());
    }




    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DAO methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void create(AssociativeEntity entity, CreateDAOCallback createDAOCallback) {
        entityTransaction.create(
                getJSONFromEntity.apply(entity),
                FIELD_SET[0],
                createDAOCallback
        );
    }

    @Override
    public void createMultiple(List<AssociativeEntity> entityList, CreateMultipleDAOCallback createMultipleDAOCallback) {
        entityTransaction.createMultiple(
                entityList,
                getJSONFromEntity,
                FIELD_SET[0],
                createMultipleDAOCallback);
    }

    @Override
    public void retrieveMultiple(Predicate<AssociativeEntity> condition, Comparator<AssociativeEntity> sorting, RetrieveMultipleDAOCallback<AssociativeEntity> retrieveMultipleDAOCallback) {
        entityTransaction.retrieveMultiple(
                condition,
                sorting,
                getEntityFromJSON,
                retrieveMultipleDAOCallback
        );
    }

    @Override
    public void retrieve(long id, RetrieveDAOCallback<AssociativeEntity> retrieveDAOCallback) {
        entityTransaction.retrieve(
                id,
                FIELD_SET[0],
                getEntityFromJSON,
                retrieveDAOCallback
        );
    }

    @Override
    public void update(AssociativeEntity newEntity, UpdateDAOCallback updateDAOCallback) {
        entityTransaction.update(
                getJSONFromEntity.apply(newEntity),
                FIELD_SET[0],
                updateDAOCallback
        );
    }

    @Override
    public void delete(long id, DeleteDAOCallback deleteDAOCallback) {
        entityTransaction.delete(
                id,
                FIELD_SET[0],
                deleteDAOCallback
        );
    }
}
