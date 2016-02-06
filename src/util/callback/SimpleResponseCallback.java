package util.callback;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public interface SimpleResponseCallback {
    void onSuccess();
    void onFailure(Exception e);
}
