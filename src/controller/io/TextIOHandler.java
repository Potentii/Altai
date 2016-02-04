package controller.io;


import controller.io.callback.ReadFileCallback;
import controller.io.callback.WriteFileCallback;
import org.jetbrains.annotations.NotNull;

import java.io.*;


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
        new Thread(() -> {
            BufferedReader bufferRead = null;
            try{
                StringBuilder content = new StringBuilder();
                String line = null;
                bufferRead = new BufferedReader(new FileReader(file.getAbsoluteFile()));

                while((line = bufferRead.readLine()) != null){
                    content.append(line);
                }

                readFileCallback.onSuccess(content.toString());
            } catch(IOException e) {
                e.printStackTrace();
                readFileCallback.onFailure(e);
            } catch (SecurityException e){
                System.err.println("SECURITY EXCEPTION");
                e.printStackTrace();
                readFileCallback.onFailure(e);
            } finally{
                if (bufferRead != null) {
                    try {
                        bufferRead.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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

