package util.event.callback;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public interface TagRegisteredCallback {
    void successful(String tag);
    void unsuccessful(String tag);
}
