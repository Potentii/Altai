package view.controller.context;

import javafx.fxml.FXML;
import model.Star;
import view.GridView;
import view.controller.modal.content.form.create.StarCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.StarGVAdapter;
import java.util.*;

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
        /*
        starList.add(new Star(0L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(1L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(2L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(3L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(4L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        starList.add(new Star(5L, "title 01", "desc", 0.0, Calendar.getInstance().getTimeInMillis()));
        */
        /*
        gridView.setCellFactory(StarGVAdapter::new);
        gridView.setOnClickListener(event -> System.out.println("clicked"));
        gridView.setItems(starList);
        */
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
        try {
            EditModalWindow<Star> createWindow = new EditModalWindow<>(new StarCreateContent(), null, "Create star");
            createWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
