package view.controller.context;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Link;
import model.dao.DAO;
import model.dao.LinkDAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
import view.controller.modal.content.detail.LinkDetailContent;
import view.controller.modal.content.form.create.LinkCreateContent;
import view.controller.modal.window.DetailModalWindow;
import view.controller.modal.window.EditModalWindow;
import view.listview.LinkLVAdapter;
import view.exception.ContextLoadException;

import java.net.URL;
import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public class LinkContext extends ListedContentContext<Link> {
    @FXML
    private ListView<Link> listView;


    @Override
    protected void onUpdateRequested() {
        try {
            DAO<Link> dao = new LinkDAO();

            dao.retrieveMultiple(
                    entity -> true,
                    Comparator.comparing(Link::getId),
                    new RetrieveMultipleDAOCallback<Link>() {
                        @Override
                        public void onSuccess(List<Link> responseList) {
                            dataList = responseList;

                            Platform.runLater(() -> {
                                listView.setItems(FXCollections.observableArrayList(dataList));
                                listView.setCellFactory(param -> new LinkLVAdapter());
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
    protected void onItemSelected(){
        Link selectedItem = listView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        try {
            DetailModalWindow<Link> detailWindow = new DetailModalWindow<>(new LinkDetailContent(), selectedItem, "Link");
            detailWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Link> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {
        try {
            EditModalWindow<Link> createWindow = new EditModalWindow<>(new LinkCreateContent(), null, "Create link");
            createWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
