package controller.io;

import controller.io.callback.ReadFileCallback;
import controller.io.callback.WriteFileCallback;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Guilherme Reginaldo
 * @since 01/02/2016
 */
public class ImageIOReader extends FileBridge implements IOHandler<Image> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public ImageIOReader(@NotNull File file) {
        super(file);
    }
    public ImageIOReader(@NotNull String fullPath) {
        super(fullPath);
    }
    public ImageIOReader(@NotNull String path, @NotNull String fileNameWithExtension) {
        super(path, fileNameWithExtension);
    }
    public ImageIOReader(@NotNull String path, @NotNull String fileName, @NotNull String extension) {
        super(path, fileName, extension);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * IOHandler methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void read(@NotNull ReadFileCallback<Image> readFileCallback) {
    }

    @Override
    public void write(@NotNull Image content, @NotNull WriteFileCallback writeFileCallback) {
    }
}
