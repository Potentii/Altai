package view.controller.context;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import model.Picture;
import model.dao.DAO;
import model.dao.PictureDAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
import view.GridView;
import view.controller.modal.content.form.create.PictureCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;
import view.listview.PictureGVAdapter;

import java.util.Comparator;
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
        gridView.setCellHeight(150);
        gridView.setCellWidth(150);
        gridView.setCellFactory(PictureGVAdapter::new);
        gridView.setOnCellClickListener(event -> {
            if(event.getClickCount() == 2) {
                onItemSelected();
            }
        });


        try {
            DAO<Picture> dao = new PictureDAO();

            dao.retrieveMultiple(
                    entity -> true,
                    Comparator.comparing(Picture::getId),
                    new RetrieveMultipleDAOCallback<Picture>() {
                        @Override
                        public void onSuccess(List<Picture> responseList) {
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
        Picture selectedItem = gridView.getSelectedItem();
        if(selectedItem == null){
            return;
        }
        /*
        try {
            DetailModalWindow<Host> detailWindow = new DetailModalWindow<>(new PictureDetailContent(), selectedItem, "Picture");
            detailWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onPrepareForDelete() {

    }

    @Override
    public void onDeleteRequested(List<Picture> deleteList) {

    }

    @Override
    protected void addBtn_onClick() {
        try {
            EditModalWindow<Picture> createWindow = new EditModalWindow<>(new PictureCreateContent(), null, "Create picture");
            createWindow.setOnActionFinishedCallback(entity -> onUpdateRequested());
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
