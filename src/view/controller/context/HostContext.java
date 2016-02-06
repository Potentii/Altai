package view.controller.context;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Host;
import model.dao.DAO;
import model.dao.HostDAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
import view.controller.modal.content.form.create.HostCreateContent;
import view.controller.modal.content.detail.HostDetailContent;
import view.controller.modal.window.DetailModalWindow;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.HostLVAdapter;

import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class HostContext extends ListedContentContext<Host> {
    @FXML
    private ListView<Host> listView;


    @Override
    protected void onUpdateRequested() {
        try {
            DAO<Host> dao = new HostDAO();

            dao.retrieveMultiple(host -> true, Comparator.comparing(Host::getId), new RetrieveMultipleDAOCallback<Host>() {
                @Override
                public void onSuccess(List<Host> responseList) {
                    dataList = responseList;

                    Platform.runLater(() -> {
                        listView.setItems(FXCollections.observableArrayList(dataList));
                        listView.setCellFactory(param -> new HostLVAdapter());
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
        Host selectedItem = listView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            return;
        }
        try {
            DetailModalWindow<Host> detailWindow = new DetailModalWindow<>(new HostDetailContent(), selectedItem, "Host");
            detailWindow.setOnActionFinishedCallback(host -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Host> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {
        try {
            EditModalWindow<Host> createWindow = new EditModalWindow<>(new HostCreateContent(), null, "Create host");
            createWindow.setOnActionFinishedCallback(host -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
