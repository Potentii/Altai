package model.dao.callback;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface UpdateDAOCallback {
    void onSuccess();
    void onFailure(Exception e);
}
