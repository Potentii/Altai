package view.controller.context;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Category;
import view.controller.modal.content.CategoryCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.CategoryLVAdapter;
import view.listview.LinkLVAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class CategoryContext extends ListedContentContext<Category> {
    @FXML
    private ListView<Category> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataList = new ArrayList<>();
        dataList.add(new Category(0L, "title 01", new Date().getTime()));
        dataList.add(new Category(1L, "title 02", new Date().getTime()));
        dataList.add(new Category(2L, "title 03", new Date().getTime()));
        dataList.add(new Category(3L, "title 04", new Date().getTime()));
        dataList.add(new Category(4L, "title 05", new Date().getTime()));
        dataList.add(new Category(5L, "title 06", new Date().getTime()));

        listView.setItems(FXCollections.observableArrayList(dataList));
        listView.setCellFactory(param -> new CategoryLVAdapter());
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
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
