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
import view.controller.modal.content.detail.CategoryDetailContent;
import view.controller.modal.content.form.create.CategoryCreateContent;
import view.controller.modal.window.DetailModalWindow;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.CategoryLVAdapter;

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
                    entity -> true,
                    Comparator.comparing(Category::getId),
                    new RetrieveMultipleDAOCallback<Category>() {
                        @Override
                        public void onSuccess(List<Category> responseList) {
                            dataList = responseList;

                            Platform.runLater(() -> {
                                listView.setItems(FXCollections.observableArrayList(dataList));
                                listView.setCellFactory(param -> new CategoryLVAdapter());
                                listView.setOnMouseClicked(event -> {
                                    if(event.getClickCount() == 2) {
                                        onItemSelected();
                                    }
                                });
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
    protected void onItemSelected() {
        Category selectedItem = listView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        try {
            DetailModalWindow<Category> detailWindow = new DetailModalWindow<>(new CategoryDetailContent(), selectedItem, "Category");
            detailWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
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
            EditModalWindow<Category> createWindow = new EditModalWindow<>(new CategoryCreateContent(), null, "Create category");
            createWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
