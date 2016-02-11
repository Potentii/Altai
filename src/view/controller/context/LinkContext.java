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
import view.controller.modal.content.form.create.LinkCreateContent;
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

        /*
        dataList = new ArrayList<>();
        dataList.add(new Link(0L, "title placeholder 2", "http://google.com/", "", false, false, 0.0f, new Date().getTime(), 0L));
        dataList.add(new Link(1L, "title placeholder 1", "http://google.com/", "", false, true, 0.1f, new Date().getTime(), 0L));
        dataList.add(new Link(2L, "title placeholder 2", "http://google.com/", "", false, true, 0.2f, new Date().getTime(), 0L));
        dataList.add(new Link(3L, "title placeholder 2", "http://google.com/", "", true, false, 0.3f, new Date().getTime(), 0L));
        dataList.add(new Link(4L, "title placeholder 2", "http://google.com/", "", false, true, 0.4f, new Date().getTime(), 0L));
        dataList.add(new Link(5L, "title placeholder 2", "http://google.com/", "", false, true, 0.5f, new Date().getTime(), 0L));
        dataList.add(new Link(6L, "title placeholder 2", "http://google.com/", "", true, true, 0.6f, new Date().getTime(), 0L));
        dataList.add(new Link(7L, "title placeholder 2", "http://google.com/", "", false, true, 0.7f, new Date().getTime(), 0L));
        dataList.add(new Link(8L, "title placeholder 2", "http://google.com/", "", false, false, 0.8f, new Date().getTime(), 0L));
        dataList.add(new Link(9L, "title placeholder 2", "http://google.com/", "", false, false, 0.9f, new Date().getTime(), 0L));
        dataList.add(new Link(10L, "title placeholder 2", "http://google.com/", "", true, false, 1.0f, new Date().getTime(), 0L));

        ObservableList<Link> userList = FXCollections.observableArrayList(dataList);

        listView.setItems(userList);
        listView.setCellFactory(param -> new LinkLVAdapter());
        listView.setOnMouseClicked(event -> onItemSelected());
        */
    }

    @Override
    protected void onItemSelected(){
        Link selectedItem = listView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }/*
        try {
            DetailModalWindow<Link> detailWindow = new DetailModalWindow<>(new LinkDetailContent(), selectedItem, "Link");
            detailWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }*/
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
