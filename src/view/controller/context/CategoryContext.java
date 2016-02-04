package view.controller.context;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Category;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
import view.controller.modal.content.CategoryCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.CategoryLVAdapter;
import view.listview.LinkLVAdapter;

import java.net.URL;
import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class CategoryContext extends ListedContentContext<Category> {
    @FXML
    private ListView<Category> listView;


    @Override
    protected void onUpdateRequested() {
        try {
            DAO<Category> dao = new CategoryDAO();

            dao.retrieveMultiple(
                    category -> true,
                    Comparator.comparing(Category::getId),
                    new RetrieveMultipleDAOCallback<Category>() {
                        @Override
                        public void onSuccess(List<Category> responseList) {
                            dataList = responseList;
                            Platform.runLater(() -> {
                                listView.setItems(FXCollections.observableArrayList(dataList));
                                listView.setCellFactory(param -> new CategoryLVAdapter());
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // ERROR
                            // TODO
                        }
                    });

        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Category> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {
        try {
            EditModalWindow<Category> editWindow = new EditModalWindow<>(new CategoryCreateContent(), null, "Create category");
            editWindow.setOnActionFinishedCallback(this::onUpdateRequested);
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
