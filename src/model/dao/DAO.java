package model.dao;

import model.dao.callback.*;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface DAO<T> {
    public void create(T entity, CreateDAOCallback createDAOCallback);
    public void retrieveMultiple(Predicate<T> condition, Comparator<T> sorting, RetrieveMultipleDAOCallback<T> retrieveMultipleDAOCallback);
    public void retrieve(long id, RetrieveDAOCallback<T> retrieveDAOCallback);
    public void update(T newEntity, UpdateDAOCallback updateDAOCallback);
    public void delete(long id, DeleteDAOCallback deleteDAOCallback);
}
