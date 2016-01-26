package controller.io;


import java.io.*;

/**
 * @author Guilherme Reginaldo
 * @since 21/01/2016
 */
public final class FileIOHandler implements FileBridge<String>{
    private String path;
    private String fileName;



    public FileIOHandler(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }



    @Override
    public void read(final ReadFileCallback<String> readFileCallback) {
        // TODO não criar arquivo se ele nao existir, se isso acontecer, lance um erro apenas
    }


    @Override
    public void write(final String content, final WriteFileCallback writeFileCallback) {
        new Thread(() -> {
            BufferedWriter bufferWrite = null;
            File file = new File(path + fileName);

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


    @Override
    public boolean fileExists(){
        return new File(path + fileName).exists();
    }
}

