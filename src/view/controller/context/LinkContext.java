package view.controller.context;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Link;
import view.controller.modal.content.form.create.LinkCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.listview.LinkLVAdapter;
import view.controller.modal.window.DetailModalWindow;
import view.exception.ContextLoadException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public class LinkContext extends ListedContentContext<Link> {
    @FXML
    private ListView<Link> listView;


    @Override
    protected void onUpdateRequested() {
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
    }

    @Override
    protected void onItemSelected(){
        Link selectedData = listView.getSelectionModel().getSelectedItem();
        /*
        try {
            new DetailModalWindow<>("/res/layout/layout_detail_link.fxml", selectedData);
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
        */
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
