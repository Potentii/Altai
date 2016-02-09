package view.controller.modal.content.form.create;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.AssociativeEntity;
import model.Category;
import model.Link;
import model.LinkCategoryAssociation;
import model.dao.AssociativeEntityDAO;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.LinkDAO;
import model.dao.callback.CreateDAOCallback;
import model.dao.callback.CreateMultipleDAOCallback;
import model.dao.callback.RetrieveMultipleDAOCallback;
import util.FormValidator;
import view.CategoryPickerDialog;
import view.exception.ContextLoadException;

import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class LinkCreateContent extends CreateModalContent<Link> {
    @FXML
    private TextField urlIn;
    @FXML
    private TextField descIn;
    @FXML
    private TextField ratingIn;
    @FXML
    private Button categoryPickerBtn;

    @FXML
    private Label urlErrorOut;
    @FXML
    private Label ratingErrorOut;

    private Map<Category, Boolean> categorySelectionMap;
    private FormValidator validator;



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
        getTitleIn().setPromptText("Link's title");
        categorySelectionMap = new LinkedHashMap<>();

        // *Configuring validator:
        validator = new FormValidator()
                .addField(getTitleIn(), getTitleErrorOut(), EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(urlIn, urlErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(ratingIn, ratingErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED, FormValidator.EValidation.FLOAT));

        try {
            // *Retrieving all registered categories:
            DAO<Category> categoryDAO = new CategoryDAO();
            categoryDAO.retrieveMultiple(
                    category -> true,
                    Comparator.comparing(Category::getTitle),
                    new RetrieveMultipleDAOCallback<Category>() {
                        @Override
                        public void onSuccess(List<Category> responseList) {
                            // *Filling the selectionMap with all categories:
                            categorySelectionMap.clear();
                            responseList.forEach(category -> categorySelectionMap.put(category, false));

                            Platform.runLater(() -> {
                                // *Adding listener to categoryPicker's button:
                                categoryPickerBtn.setOnAction(event -> {
                                    try {
                                        CategoryPickerDialog categoryPicker = new CategoryPickerDialog();
                                        categoryPicker.setSelectionMap(categorySelectionMap);
                                        categoryPicker.setNeutralBtnCallback(() -> {});
                                        categoryPicker.setPositiveBtnCallback(() -> {
                                            categorySelectionMap = categoryPicker.getSelectionMap();
                                        });
                                    } catch (ContextLoadException e) {
                                        e.printStackTrace();
                                    }
                                });
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
        validator.doVisualValidation();
        if(!validator.isValid()){
            return;
        }


        Link entity = new Link(
                0L,
                getTitleIn().getText(),
                urlIn.getText(),
                descIn.getText(),
                true,
                Double.parseDouble(ratingIn.getText()),
                Calendar.getInstance().getTimeInMillis(),
                0L
        );

        try {
            DAO<Link> dao = new LinkDAO();
            dao.create(entity, new CreateDAOCallback() {
                @Override
                public void onSuccess(final Long linkId) {

                    // *Selecting all categories that has to be referenced:
                    List<AssociativeEntity> categoryList = new ArrayList<>();
                    categorySelectionMap.forEach((category, status) -> {
                        if(status){
                            AssociativeEntity associativeEntity = new LinkCategoryAssociation(
                                    0L,
                                    linkId,
                                    category.getId(),
                                    Calendar.getInstance().getTimeInMillis()
                            );
                            categoryList.add(associativeEntity);
                        }
                    });


                    // *Creating all the needed link-category association:
                    try {
                        DAO<AssociativeEntity> associativeEntityDAO = new AssociativeEntityDAO<>(LinkCategoryAssociation.class);
                        associativeEntityDAO.createMultiple(categoryList, new CreateMultipleDAOCallback() {
                            @Override
                            public void onSuccess(List<Long> idList) {
                                onActionFinished();
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
                public void onFailure(Exception e) {
                    // ERROR
                    // TODO
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }
}
