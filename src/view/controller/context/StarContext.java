package view.controller.context;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.Star;
import view.GridView;
import view.listview.StarGVAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class StarContext extends ListedContentContext<Star> {
    @FXML
    private GridView<Star> gridView;

    @Override
    protected void onUpdateRequested() {
        List<Star> starList = new ArrayList<>();
        starList.add(new Star(0L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(1L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(2L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(3L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(4L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(5L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));

        gridView.setMaxColumns(3);
        gridView.setCellFactory(StarGVAdapter::new);
        gridView.setOnClickListener(event -> System.out.println("clicked"));
        gridView.setItems(starList);
    }

    @Override
    protected void onItemSelected() {

    }

    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Star> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {

    }
}
