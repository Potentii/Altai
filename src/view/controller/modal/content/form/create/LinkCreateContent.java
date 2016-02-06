package view.controller.modal.content.form.create;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.Category;
import model.Link;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.callback.RetrieveMultipleDAOCallback;
import view.exception.ContextLoadException;
import view.listview.CategoryCBAdapter;

import java.util.Comparator;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class LinkCreateContent extends CreateModalContent<Link> {
    @FXML
    private ComboBox<Category> categoryComboBox;

    private List<Category> categoryList;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public LinkCreateContent() throws ContextLoadException {
        super("/layout/layout_edit_link.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested() {
        categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {

        });
        categoryComboBox.setPromptText("Select category");
        categoryComboBox.setCellFactory(param -> new CategoryCBAdapter());


        try {
            DAO<Category> categoryDAO = new CategoryDAO();
            categoryDAO.retrieveMultiple(
                    category -> true,
                    Comparator.comparing(Category::getTitle),
                    new RetrieveMultipleDAOCallback<Category>() {
                        @Override
                        public void onSuccess(List<Category> responseList) {
                            categoryList = responseList;
                            Platform.runLater(() -> {
                                categoryComboBox.setItems(FXCollections.observableArrayList(categoryList));
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
    public void onAction() {

    }
}
