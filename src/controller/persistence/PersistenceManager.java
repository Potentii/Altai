package controller.persistence;

import com.sun.istack.internal.Nullable;
import controller.io.FileBridge;
import controller.io.TextIOHandler;
import controller.io.callback.WriteFileCallback;
import controller.persistence.annotation.Entity;
import controller.persistence.entity.EntityFile;
import controller.persistence.entity.EntityTransaction;
import controller.persistence.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import util.event.callback.EventFinishedCallback;
import util.callback.SimpleResponseCallback;
import util.event.TaggedEvent;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 21/01/2016
 */
public class PersistenceManager {
    private static PersistenceManager instance;
    private final String rootPath;
    private final Map<Class<?>, String> classAndSourceNameMap;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Singleton constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private PersistenceManager() {
        rootPath = getLocalPath() + File.separator;
        classAndSourceNameMap = new HashMap<>();
    }
    public static PersistenceManager getInstance(){
        if(instance == null){
            instance = new PersistenceManager();
        }
        return instance;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void initialize(@NotNull List<Class<?>> classList, @NotNull SimpleResponseCallback callback){
        // *Setting up the tagged event:
        Set<String> taggedEvent_set = new HashSet<>();
        for (int i = 0; i < classList.size(); i++) {
            taggedEvent_set.add(i + "");
        }
        taggedEvent_set.add("hostIconFolder");
        taggedEvent_set.add("starMainImgFolder");
        taggedEvent_set.add("pictureFolder");
        taggedEvent_set.add("movieFolder");
        TaggedEvent taggedEvent = new TaggedEvent(taggedEvent_set)
                .setEventFinishedCallback(new EventFinishedCallback() {
                    @Override
                    public void onSuccess() {
                        callback.onSuccess();
                    }

                    @Override
                    public void onFailure(Set<String> failedTags) {
                        StringBuffer failedTags_str = new StringBuffer("Failed steps: ");
                        failedTags.forEach(s -> failedTags_str.append("\n" + s));
                        callback.onFailure(new Exception(failedTags_str.toString()));
                    }
                });


        // *Filling the 'class and source' map:
        classAndSourceNameMap.clear();
        for (int i = 0; i < classList.size(); i++) {
            final Class<?> clazz = classList.get(i);
            final int index = i;

            // *Getting the Entity's file sourceName:
            String sourceName = getSourceName(clazz);

            // *Checks if the file doesn't exists:
            TextIOHandler textIOHandler = new TextIOHandler(rootPath + EAltaiPersistence.ENTITIES_RELATIVE_PATH.getValue() + sourceName + EAltaiPersistence.ENTITY_DEFAULT_EXTENSION.getValue());
            if(!textIOHandler.itExists()){
                textIOHandler.write(new EntityFile(new Metadata(-1), new JSONArray()).toString(), new WriteFileCallback() {
                    @Override
                    public void onSuccess() {
                        classAndSourceNameMap.put(clazz, sourceName);
                        taggedEvent.registerStep(index + "", true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // ERROR
                        taggedEvent.registerStep(index + "", false);
                    }
                });
            } else{
                classAndSourceNameMap.put(clazz, sourceName);
                taggedEvent.registerStep(index + "", true);
            }
        }

        // *Creating the hosts' icon folder:
        new FileBridge(rootPath + EAltaiPersistence.HOST_ICON_RELATIVE_PATH_RAW.getValue())
                .create(new SimpleResponseCallback() {
                    @Override
                    public void onSuccess() {
                        taggedEvent.registerStep("hostIconFolder", true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        taggedEvent.registerStep("hostIconFolder", false);
                    }
                });

        // *Creating the stars' main image folder:
        new FileBridge(rootPath + EAltaiPersistence.STAR_MAIN_IMG_RELATIVE_PATH_RAW.getValue())
                .create(new SimpleResponseCallback() {
                    @Override
                    public void onSuccess() {
                        taggedEvent.registerStep("starMainImgFolder", true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        taggedEvent.registerStep("starMainImgFolder", false);
                    }
                });

        // *Creating the picture's folder:
        new FileBridge(rootPath + EAltaiPersistence.PICTURE_RELATIVE_PATH_RAW.getValue())
                .create(new SimpleResponseCallback() {
                    @Override
                    public void onSuccess() {
                        taggedEvent.registerStep("pictureFolder", true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        taggedEvent.registerStep("pictureFolder", false);
                    }
                });

        // *Creating the movie's folder:
        new FileBridge(rootPath + EAltaiPersistence.MOVIE_RELATIVE_PATH_RAW.getValue())
                .create(new SimpleResponseCallback() {
                    @Override
                    public void onSuccess() {
                        taggedEvent.registerStep("movieFolder", true);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        taggedEvent.registerStep("movieFolder", false);
                    }
                });
    }


    @Nullable
    private static String getLocalPath(){
        File jarFile = null;
        try {
            jarFile = new File(TextIOHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jarFile.getParent();
    }


    /**
     * Retrieves the entity's source name.
     * @param clazz Entity's {@link Class}
     * @return Entity's annotated name, or the entity's class name if it hasn't an {@link Entity} annotation or if it's empty.
     */
    @NotNull
    public String getSourceName(@NotNull Class<?> clazz){
        String sourceName = "";

        // *Looks for the annotated name:
        if(clazz.isAnnotationPresent(Entity.class)){
            Entity entity = clazz.getAnnotation(Entity.class);
            sourceName += entity.sourceName();
        }

        // *If the annotation was empty, or if it not exists:
        if(sourceName.trim().isEmpty()){
            sourceName += clazz.getName();
        }

        return sourceName;
    }



    public <T> EntityTransaction<T> getEntityTransaction(@NotNull Class<T> entityClass) throws UndeclaredEntityException{
        String sourceName = classAndSourceNameMap.get(entityClass);

        if(sourceName == null){
            throw new UndeclaredEntityException();
        } else{
            return new EntityTransaction<>(rootPath + EAltaiPersistence.ENTITIES_RELATIVE_PATH.getValue() + sourceName + EAltaiPersistence.ENTITY_DEFAULT_EXTENSION.getValue());
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public String getRootPath() {
        return rootPath;
    }
}
