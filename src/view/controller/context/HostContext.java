package view.controller.context;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Host;
import view.controller.modal.content.HostCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.HostLVAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class HostContext extends ListedContentContext<Host> {
    @FXML
    private ListView<Host> listView;


    @Override
    protected void onUpdateRequested() {
        dataList = new ArrayList<>();
        dataList.add(new Host(0L, "Google", "http://google.com", "", "", new Date().getTime()));
        dataList.add(new Host(1L, "Google", "http://google.com", "", "", new Date().getTime()));
        dataList.add(new Host(2L, "Google", "http://google.com", "", "", new Date().getTime()));
        dataList.add(new Host(3L, "Google", "http://google.com", "", "", new Date().getTime()));
        dataList.add(new Host(4L, "Google", "http://google.com", "", "", new Date().getTime()));
        dataList.add(new Host(5L, "Google", "http://google.com", "", "", new Date().getTime()));


        listView.setItems(FXCollections.observableArrayList(dataList));
        listView.setCellFactory(param -> new HostLVAdapter());
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
            EditModalWindow<Host> editWindow = new EditModalWindow<>(new HostCreateContent(), null, "Create host");
            editWindow.setOnActionFinishedCallback(this::onUpdateRequested);
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
