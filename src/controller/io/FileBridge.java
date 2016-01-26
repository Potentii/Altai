package controller.io;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public interface FileBridge<T> {
    void read(final ReadFileCallback<T> readFileCallback);
    void write(final T content, final WriteFileCallback writeFileCallback);
    boolean fileExists();
}
