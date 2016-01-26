package model.dao.callback;

import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface RetrieveMultipleDAOCallback<T> {
    void onSuccess(List<T> responseList);
    void onFailure(Exception e);
}
