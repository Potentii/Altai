package view.controller.context;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import model.Star;
import model.dao.DAO;
import model.dao.StarDAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
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
        gridView.setCellFactory(StarGVAdapter::new);
        gridView.setOnCellClickListener(event -> {
            if(event.getClickCount() == 2) {
                onItemSelected();
            }
        });


        try {
            DAO<Star> dao = new StarDAO();

            dao.retrieveMultiple(
                    entity -> true,
                    Comparator.comparing(Star::getId),
                    new RetrieveMultipleDAOCallback<Star>() {
                        @Override
                        public void onSuccess(List<Star> responseList) {
                            dataList = responseList;
                            Platform.runLater(() -> gridView.setItems(dataList));
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
