package view.controller.modal.content.form.create;

import controller.io.FileBridge;
import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Category;
import model.Star;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.StarDAO;
import model.dao.callback.CreateDAOCallback;
import model.dao.callback.RetrieveMultipleDAOCallback;
import util.FormValidator;
import util.callback.SimpleResponseCallback;
import view.CategoryPickerDialog;
import view.control.FilePicker;
import view.exception.ContextLoadException;

import java.io.File;
import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 14/02/2016
 */
public class StarCreateContent  extends CreateModalContent<Star> {
    @FXML
    private TextField descriptionIn;
    @FXML
    private TextField ratingIn;
    @FXML
    private FilePicker imgFilePicker;
    @FXML
    private Button categoryPickerBtn;

    @FXML
    private Label descriptionErrorOut;
    @FXML
    private Label ratingErrorOut;
    @FXML
    private Label imgErrorOut;

    private Map<Category, Boolean> categorySelectionMap;
    private CategoryPickerDialog categoryPickerDialog;
    private boolean dataLoaded;
    private FormValidator validator;




    /*
    *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
    *  * Constructor:
    *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
    */
    public StarCreateContent() throws ContextLoadException {
        super("/layout/content/edit/layout_edit_star.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested() {
        getTitleIn().setPromptText("Star's name");
        categorySelectionMap = new LinkedHashMap<>();
        dataLoaded = false;


        // *Configuring validator:
        validator = new FormValidator()
                .addField(getTitleIn(), getTitleErrorOut(), EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(descriptionIn, descriptionErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(ratingIn, ratingErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED, FormValidator.EValidation.FLOAT))
                .addComplexField(imgFilePicker, null, imgErrorOut);


        try{
            // TODO move this logic inside CategoryPickerDialog:
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
                                        onFailure(e);
                                    }
                                });

                                dataLoaded = true;
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // ERROR
                            dataLoaded = false;
                        }
                    });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
            dataLoaded = false;
        }
    }


    @Override
    public void onAction() {
        if(!dataLoaded){
            return;
        }
        validator.doVisualValidation();
        if(!validator.isValid()){
            return;
        }

        // TODO make this control accept more than just 1 file, and show it on the preview
        // *Generating the selected icon's new name:
        FileBridge selectedImg_fileBridge = new FileBridge(imgFilePicker.getFile());
        String newImgFileName = UUID.randomUUID().toString() + "." + selectedImg_fileBridge.getExtension();
        File copiedImgFile = new File(PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.STAR_MAIN_IMG_RELATIVE_PATH.getValue() + newImgFileName);


        // *Copying selected img to its appropriate folder:
        selectedImg_fileBridge.copyTo(copiedImgFile, new SimpleResponseCallback() {
            @Override
            public void onSuccess() {
                createEntity(newImgFileName);
            }

            @Override
            public void onFailure(Exception e) {
                // ERROR
                // TODO
            }
        });
    }


    private void createEntity(String imgName){
        Star star = new Star(
                0L,
                getTitleIn().getText().trim(),
                descriptionIn.getText().trim(),
                imgName,
                Double.parseDouble(ratingIn.getText().trim()),
                Calendar.getInstance().getTimeInMillis()
        );

        try {
            DAO<Star> starDAO = new StarDAO();
            starDAO.create(star, new CreateDAOCallback() {
                @Override
                public void onSuccess(Long id) {
                    star.setId(id);
                    data = star;
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
}
