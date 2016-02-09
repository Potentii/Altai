package model.dao.callback;

import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 08/02/2016
 */
public interface CreateMultipleDAOCallback {
    void onSuccess(List<Long> idList);
    void onFailure(Exception e);
}
