package controller.io;

import org.jetbrains.annotations.NotNull;
import util.callback.SimpleResponseCallback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public class FileBridge {
    protected File file;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public FileBridge(@NotNull File file){
        this.file = file;
    }
    public FileBridge(@NotNull String fullPath) {
        this(new File(fullPath));
    }
    public FileBridge(@NotNull String path, @NotNull String fileNameWithExtension) {
        this(path + fileNameWithExtension);
    }
    public FileBridge(@NotNull String path, @NotNull String fileName, @NotNull String extension) {
        this(path, fileName + extension);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     * Asynchronously copy the current file to another location.
     * @param newLocation The new location, if already exists, it'll be replaced
     * @param callback The response callback
     */
    public void copyTo(@NotNull File newLocation, @NotNull SimpleResponseCallback callback){
        new Thread(() -> {
            try {
                Files.copy(
                        file.toPath(),
                        newLocation.toPath(),
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES,
                        LinkOption.NOFOLLOW_LINKS);
                callback.onSuccess();
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(e);
            }
        }).start();
    }


    /**
     * Creates an empty file or directory.
     * @param callback The response callback
     */
    public void create(@NotNull SimpleResponseCallback callback){
        new Thread(() -> {
            try{
                if(!file.exists()){
                    file.mkdirs();
                    if(file.isFile()){
                        file.createNewFile();
                    }
                }
                callback.onSuccess();
            } catch (IOException e){
                e.printStackTrace();
                callback.onFailure(e);
            }
        }).start();
    }


    /**
     * Retrieves if the current file exists.
     * @return True if it exists, false otherwise
     */
    public boolean itExists(){
        return file.exists();
    }


    /**
     * Retrieves the current file's extension (without the dot).
     * @return The file's extension, or empty string if can't get extension
     */
    @NotNull
    public String getExtension() {
        try {
            String name = file.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
}
