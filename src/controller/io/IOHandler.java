package controller.io;

import controller.io.callback.ReadFileCallback;
import controller.io.callback.WriteFileCallback;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public interface IOHandler <T>{
    void read(@NotNull ReadFileCallback<T> readFileCallback);
    void write(@NotNull T content, @NotNull WriteFileCallback writeFileCallback);
}
