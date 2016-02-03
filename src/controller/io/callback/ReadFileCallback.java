package controller.io.callback;

/**
 * Callback for asynchronous reading operations.
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface ReadFileCallback<T> {
    void onSuccess(final T response);
    void onFailure(final Exception e);
}
