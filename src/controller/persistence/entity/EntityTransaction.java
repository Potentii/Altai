package controller.persistence.entity;

import controller.io.TextIOHandler;
import controller.io.callback.ReadFileCallback;
import controller.io.callback.WriteFileCallback;
import model.dao.callback.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Guilherme Reginaldo
 * @since 22/01/2016
 */
public final class EntityTransaction<T> {
    private TextIOHandler textIOHandler;

    public EntityTransaction(String fullPath){
        this.textIOHandler = new TextIOHandler(fullPath);
    }


    public void create(final JSONObject jsonObject, final String idKey, final CreateDAOCallback createDAOCallback){
        textIOHandler.read(new ReadFileCallback<String>() {
            @Override
            public void onSuccess(String response) {
                EntityFile entityFile = new EntityFile(response);
                final long newId = entityFile.getMetadata().getNewId();

                jsonObject.put(idKey, newId);
                entityFile.appendContent(jsonObject);

                textIOHandler.write(entityFile.toString(), new WriteFileCallback() {
                    @Override
                    public void onSuccess() {
                        createDAOCallback.onSuccess(newId);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        createDAOCallback.onFailure(e);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                createDAOCallback.onFailure(e);
            }
        });
    }


    public void retrieveMultiple(final Predicate<T> condition, final Comparator<T> sorting, final Function<JSONObject, T> getEntityFromJSON, final RetrieveMultipleDAOCallback<T> retrieveMultipleDAOCallback){
        textIOHandler.read(new ReadFileCallback<String>() {
            @Override
            public void onSuccess(String response) {
                EntityFile entityFile = new EntityFile(response);
                JSONArray content = entityFile.getContent();

                List<T> entityList = new ArrayList<>();
                for (int i = 0; i < content.length(); i++) {
                    JSONObject jsonObject = content.getJSONObject(i);
                    T entity = getEntityFromJSON.apply(jsonObject);
                    if(condition.test(entity)){
                        entityList.add(entity);
                    }
                }

                entityList.sort(sorting);

                retrieveMultipleDAOCallback.onSuccess(entityList);
            }

            @Override
            public void onFailure(Exception e) {
                retrieveMultipleDAOCallback.onFailure(e);
            }
        });
    }



    public void retrieve(final long id, final String idKey, final Function<JSONObject, T> getEntityFromJSON, final RetrieveDAOCallback<T> retrieveDAOCallback){
        textIOHandler.read(new ReadFileCallback<String>() {
            @Override
            public void onSuccess(String response) {
                EntityFile entityFile = new EntityFile(response);
                JSONArray content = entityFile.getContent();
                JSONObject jsonObject;
                T entity = null;

                for (int i = 0; i < content.length(); i++) {
                    jsonObject = content.getJSONObject(i);
                    if(jsonObject.getLong(idKey) == id){
                        entity = getEntityFromJSON.apply(jsonObject);
                        break;
                    }
                }

                if(entity != null){
                    retrieveDAOCallback.onSuccess(entity);
                } else{
                    retrieveDAOCallback.onFailure(new Exception("Entity not found"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                retrieveDAOCallback.onFailure(e);
            }
        });
    }


    public void update(final JSONObject jsonObject, final String idKey, final UpdateDAOCallback updateDAOCallback){
        textIOHandler.read(new ReadFileCallback<String>() {
            @Override
            public void onSuccess(String response) {
                EntityFile entityFile = new EntityFile(response);
                JSONArray content = entityFile.getContent();
                long id = jsonObject.getLong(idKey);
                int index = -1;

                for (int i = 0; i < content.length(); i++) {
                    if(content.getJSONObject(i).getLong(idKey) == id){
                        index = i;
                        break;
                    }
                }

                if(index >= 0){
                    entityFile.updateContent(index, jsonObject);

                    textIOHandler.write(entityFile.toString(), new WriteFileCallback() {
                        @Override
                        public void onSuccess() {
                            updateDAOCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            updateDAOCallback.onFailure(e);
                        }
                    });
                } else{
                    updateDAOCallback.onFailure(new Exception("Entity not found"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                updateDAOCallback.onFailure(e);
            }
        });
    }



    public void delete(final long id, final String idKey, final DeleteDAOCallback deleteDAOCallback){
        textIOHandler.read(new ReadFileCallback<String>() {
            @Override
            public void onSuccess(String response) {
                EntityFile entityFile = new EntityFile(response);
                JSONArray content = entityFile.getContent();
                int index = -1;

                for (int i = 0; i < content.length(); i++) {
                    if(content.getJSONObject(i).getLong(idKey) == id){
                        index = i;
                        break;
                    }
                }

                if(index >= 0){
                    entityFile.deleteContent(index);

                    textIOHandler.write(entityFile.toString(), new WriteFileCallback() {
                        @Override
                        public void onSuccess() {
                            deleteDAOCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            deleteDAOCallback.onFailure(e);
                        }
                    });
                } else{
                    deleteDAOCallback.onFailure(new Exception("Entity not found"));
                }
            }

            @Override
            public void onFailure(Exception e) {
                deleteDAOCallback.onFailure(e);
            }
        });
    }
}
