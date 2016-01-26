package model.dao.callback;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface CreateDAOCallback {
    void onSuccess(Long id);
    void onFailure(Exception e);
}
