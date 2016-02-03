package controller.io;


import controller.io.callback.ReadFileCallback;
import controller.io.callback.WriteFileCallback;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Guilherme Reginaldo
 * @since 21/01/2016
 */
public final class TextIOHandler extends FileBridge implements IOHandler<String>{



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public TextIOHandler(@NotNull File file) {
        super(file);
    }
    public TextIOHandler(@NotNull String fullPath) {
        super(fullPath);
    }
    public TextIOHandler(@NotNull String path, @NotNull String fileNameWithExtension) {
        super(path, fileNameWithExtension);
    }
    public TextIOHandler(@NotNull String path, @NotNull String fileName, @NotNull String extension) {
        super(path, fileName, extension);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * IOHandler methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void read(@NotNull ReadFileCallback<String> readFileCallback) {
        // TODO não criar arquivo se ele nao existir, se isso acontecer, lance um erro apenas
    }


    @Override
    public void write(@NotNull String content, @NotNull WriteFileCallback writeFileCallback) {
        new Thread(() -> {
            BufferedWriter bufferWrite = null;
            try{
                if(!file.exists()){
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                bufferWrite = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
                bufferWrite.write(content);
                writeFileCallback.onSuccess();

            } catch(IOException e) {
                e.printStackTrace();
                writeFileCallback.onFailure(e);
            } catch (SecurityException e){
                System.err.println("SECURITY EXCEPTION");
                e.printStackTrace();
                writeFileCallback.onFailure(e);
            } finally{
                if (bufferWrite != null) {
                    try {
                        bufferWrite.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

