package view.controller.modal.content.form.create;

import controller.io.FileBridge;
import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Picture;
import model.dao.DAO;
import model.dao.PictureDAO;
import model.dao.callback.CreateDAOCallback;
import util.FormValidator;
import util.callback.SimpleResponseCallback;
import view.control.FilePicker;
import view.exception.ContextLoadException;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author Guilherme Reginaldo
 * @since 13/02/2016
 */
public class PictureCreateContent  extends CreateModalContent<Picture> {
    @FXML
    private FilePicker filePicker;

    @FXML
    private Label filePickerErrorOut;

    private FormValidator validator;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public PictureCreateContent() throws ContextLoadException {
        super("/layout/layout_edit_picture.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested() {
        getTitleIn().setVisible(false);
        filePicker.setFileFilters(FilePicker.FILE_FILTER_IMAGE);

        validator = new FormValidator()
                .addComplexField(filePicker, null, filePickerErrorOut);
    }

    @Override
    public void onAction() {
        validator.doVisualValidation();
        if(!validator.isValid()){
            return;
        }


        // *Generating the selected image's new name:
        FileBridge selectedImage_fileBridge = new FileBridge(filePicker.getFile());
        String newImageFileName = UUID.randomUUID().toString() + "." + selectedImage_fileBridge.getExtension();
        File copiedImageFile = new File(PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.PICTURE_RELATIVE_PATH.getValue() + newImageFileName);


        // *Copying selected image to its appropriate folder:
        selectedImage_fileBridge.copyTo(copiedImageFile, new SimpleResponseCallback() {
            @Override
            public void onSuccess() {

                Picture picture = new Picture(
                        0L,
                        newImageFileName,
                        Calendar.getInstance().getTimeInMillis());

                try {
                    DAO<Picture> pictureDAO = new PictureDAO();
                    pictureDAO.create(picture, new CreateDAOCallback() {
                        @Override
                        public void onSuccess(Long id) {
                            picture.setId(id);
                            data = picture;

                            onActionFinished();
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // ERROR
                            // TODO
                        }
                    });
                } catch (UndeclaredEntityException e) {
                    onFailure(e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                // ERROR
                // TODO
            }
        });
    }
}
