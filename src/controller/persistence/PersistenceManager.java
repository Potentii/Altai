package controller.persistence;

import com.sun.istack.internal.Nullable;
import controller.io.FileIOHandler;
import controller.io.WriteFileCallback;
import controller.persistence.annotation.Entity;
import controller.persistence.entity.EntityFile;
import controller.persistence.entity.EntityTransaction;
import controller.persistence.entity.Metadata;
import org.json.JSONArray;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guilherme Reginaldo
 * @since 21/01/2016
 */
public class PersistenceManager {
    private static PersistenceManager instance;
    private static final String ENTITIES_PATH = getLocalPath() + File.separator + "Entity" + File.separator;
    private static final String ENTITIES_EXTENSION = ".json";
    private Map<Class<?>, String> classAndSourceNameMap;


    private PersistenceManager() {
        classAndSourceNameMap = new HashMap<>();
    }
    public static PersistenceManager getInstance(){
        if(instance == null){
            instance = new PersistenceManager();
        }

        return instance;
    }



    public void initialize(List<Class<?>> classList){
        classAndSourceNameMap = new HashMap<>();

        // TODO implementar um StepByStepEvent
        classAndSourceNameMap.clear();
        classList.forEach(clazz -> {

            // *Getting the Entity's file sourceName:
            String sourceName = getSourceName(clazz);
            /*
            if(clazz.isAnnotationPresent(Entity.class)){
                Entity entity = clazz.getAnnotation(Entity.class);
                sourceName = entity.sourceName();
            } else{
                sourceName = clazz.getName();
            }
            */

            // *Checks if the file doesn't exists:
            FileIOHandler fileIOHandler = new FileIOHandler(ENTITIES_PATH, sourceName + ENTITIES_EXTENSION);
            if(!fileIOHandler.fileExists()){
                fileIOHandler.write(new EntityFile(new Metadata(-1), new JSONArray()).toString(), new WriteFileCallback() {
                    @Override
                    public void onSuccess() {
                        classAndSourceNameMap.put(clazz, sourceName);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // ERROR
                    }
                });
            }
        });
    }


    /*
    public abstract void onSuccess();
    public abstract void onFailure(Exception e);
    */


    @Nullable
    private static String getLocalPath(){
        File jarFile = null;
        try {
            jarFile = new File(FileIOHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jarFile.getParent();
    }


    public String getSourceName(Class<?> clazz){
        if(clazz.isAnnotationPresent(Entity.class)){
            Entity entity = clazz.getAnnotation(Entity.class);
            return entity.sourceName();
        } else{
            return clazz.getName();
        }
    }



    public <T> EntityTransaction<T> getEntityTransaction(Class<T> entityClass) throws UndeclaredEntityException{
        String sourceName = classAndSourceNameMap.get(entityClass);

        if(sourceName == null){
            throw new UndeclaredEntityException();
        } else{
            return new EntityTransaction<>(ENTITIES_PATH, sourceName);
        }
    }



    public String getEntitiesPath() {
        return ENTITIES_PATH;
    }

    public String getEntitiesExtension() {
        return ENTITIES_EXTENSION;
    }
}
