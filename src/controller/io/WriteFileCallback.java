package controller.io;

/**
 * Callback for asynchronous writing operations.
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface WriteFileCallback {
    void onSuccess();
    void onFailure(final Exception e);
}
