package view.controller.modal.content.detail;

import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import model.dao.AssociativeEntityDAO;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.HostDAO;
import model.dao.callback.RetrieveDAOCallback;
import model.dao.callback.RetrieveMultipleDAOCallback;
import org.jetbrains.annotations.Nullable;
import view.Standalone;
import view.exception.ContextLoadException;
import view.listview.CategoryLVAdapter;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 11/02/2016
 */
public class LinkDetailContent extends DetailModalContent<Link> {
    @FXML
    private Hyperlink urlOut;
    @FXML
    private Label ratingOut;
    @FXML
    private ImageView hostIconImg;
    @FXML
    private Hyperlink hostOut;
    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private Label dateOut;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public LinkDetailContent() throws ContextLoadException {
        super("/layout/layout_detail_link.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DetailModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onDataBindRequested(@Nullable Link data) throws UndeclaredEntityException, NullPointerException {
        getTitleOut().setText(data.getTitle());
        urlOut.setText(data.getUrl());
        urlOut.setOnAction(event -> Standalone.requestHostServices().showDocument(data.getUrl()));
        ratingOut.setText(new DecimalFormat("#.#").format(data.getRating()));
        dateOut.setText(data.getDate() + "");
        categoryListView.setCellFactory(param -> new CategoryLVAdapter());


        // *Loading categories:
        DAO<AssociativeEntity> linkCategoryAssociationDAO = new AssociativeEntityDAO<>(LinkCategoryAssociation.class);
        DAO<Category> categoryDAO = new CategoryDAO();
        linkCategoryAssociationDAO.retrieveMultiple(
                associativeEntity -> associativeEntity.getPrimaryId() == data.getId(),
                Comparator.comparing(AssociativeEntity::getId), new RetrieveMultipleDAOCallback<AssociativeEntity>() {
                    @Override
                    public void onSuccess(List<AssociativeEntity> responseList) {
                        categoryDAO.retrieveMultiple(
                                category -> {
                                    for (AssociativeEntity associativeEntity : responseList) {
                                        if(associativeEntity.getSecondaryId() == category.getId()){
                                            return true;
                                        }
                                    }
                                    return false;
                                },
                                Comparator.comparing(Category::getId),
                                new RetrieveMultipleDAOCallback<Category>() {
                                    @Override
                                    public void onSuccess(List<Category> responseList) {
                                        Platform.runLater(() -> {
                                            categoryListView.setItems(FXCollections.observableList(responseList));
                                        });
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        // ERROR
                                        // TODO
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // ERROR
                        // TODO
                    }
                });



        // *Loading host:
        if(data.getHostId_FK() >= 0) {
            DAO<Host> hostDAO = new HostDAO();
            hostDAO.retrieve(data.getHostId_FK(), new RetrieveDAOCallback<Host>() {
                @Override
                public void onSuccess(Host response) {
                    Platform.runLater(() -> {
                        hostIconImg.setImage(new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + response.getImgPath()));
                        hostOut.setText(response.getTitle());
                        hostOut.setOnAction(event -> Standalone.requestHostServices().showDocument(response.getUrl()));
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    // ERROR
                    // TODO
                }
            });
        }
    }

    @Override
    public void onAction() {
        /*
        try{
            EditModalWindow<Link> editWindow = new EditModalWindow<>(new LinkEditContent(), data, "Edit link");
            editWindow.setOnActionFinishedCallback(entity -> {
                setData(entity);

                onActionFinished();
            });
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
        */
    }
}
