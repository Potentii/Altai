package view.controller.modal.content.form.edit;

import com.sun.istack.internal.Nullable;
import controller.io.FileBridge;
import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Host;
import model.dao.DAO;
import model.dao.HostDAO;
import model.dao.callback.UpdateDAOCallback;
import org.jetbrains.annotations.NotNull;
import util.callback.SimpleResponseCallback;
import view.control.FilePicker;
import view.exception.ContextLoadException;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author Guilherme Reginaldo
 * @since 04/02/2016
 */
public class HostEditContent extends EditModalContent<Host> {
    @FXML
    private TextField urlIn;
    @FXML
    private TextField urlPatternIn;
    @FXML
    private FilePicker filePicker;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public HostEditContent() throws ContextLoadException {
        super("/layout/layout_edit_host.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * EditModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onDataBindRequested(@Nullable Host data) throws UndeclaredEntityException, NullPointerException {
        filePicker.setFileFilters(FilePicker.FILE_FILTER_IMAGE);
        urlIn.setText(data.getUrl());
        urlPatternIn.setText(data.getUrlPattern());
        filePicker.setFile(new File(PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + data.getImgPath()));
        filePicker.printFileName();
    }

    @NotNull
    @Override
    public String getHeaderHint() {
        return "Host name";
    }

    @NotNull
    @Override
    public String getHeaderTitle() {
        return data.getTitle();
    }

    @Override
    public void onAction() {
        // TODO validate this


        if(filePicker.isFileChanged()) {
            // TODO delete the old icon

            // *Generating the selected icon's new name:
            FileBridge selectedIcon_fileBridge = new FileBridge(filePicker.getFile());
            String newIconFileName = UUID.randomUUID().toString() + "." + selectedIcon_fileBridge.getExtension();
            File copiedIconFile = new File(PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + newIconFileName);

            // *Copying selected icon to its appropriate folder:
            selectedIcon_fileBridge.copyTo(copiedIconFile, new SimpleResponseCallback(){
                @Override
                public void onSuccess() {
                    createEntity(newIconFileName);
                }

                @Override
                public void onFailure(Exception e) {
                    // ERROR
                    // TODO
                }
            });
        } else{
            createEntity(data.getImgPath());
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private void createEntity(String newIconFileName){
        // *Creating new entity instance:
        Host host = new Host(
                data.getId(),
                consumeTitle(),
                urlIn.getText(),
                urlPatternIn.getText(),
                newIconFileName,
                Calendar.getInstance().getTimeInMillis());


        // *Creating the entity on the persistence layer:
        try {
            DAO<Host> dao = new HostDAO();
            dao.update(host, new UpdateDAOCallback() {
                @Override
                public void onSuccess() {
                    data = host;
                    onActionFinished();
                }

                @Override
                public void onFailure(Exception e) {
                    //TODO
                    //ERROR
                    System.out.println("FAILURE");
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }

}
