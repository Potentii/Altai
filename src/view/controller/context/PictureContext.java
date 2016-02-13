package view.controller.context;

import javafx.fxml.FXML;
import model.Picture;
import view.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 12/02/2016
 */
public class PictureContext extends ListedContentContext<Picture> {
    @FXML
    private GridView<Picture> gridView;

    @Override
    protected void onUpdateRequested() {
        List<Picture> pictureList = new ArrayList<>();


        //gridView.setCellFactory(PictureGVAdapter::new);
        gridView.setOnClickListener(event -> System.out.println("clicked"));
        gridView.setItems(pictureList);
    }

    @Override
    protected void onItemSelected() {

    }

    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Picture> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {

    }
}
