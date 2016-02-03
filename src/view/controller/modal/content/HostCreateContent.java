package view.controller.modal.content;

import controller.io.FileBridge;
import controller.persistence.EAltaiPersistence;
import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Host;
import model.dao.DAO;
import model.dao.HostDAO;
import model.dao.callback.CreateDAOCallback;
import util.callback.SimpleResponseCallback;
import view.control.FilePicker;
import view.exception.ContextLoadException;
import java.io.File;
import java.rmi.server.UID;
import java.util.Calendar;

/**
 * @author Guilherme Reginaldo
 * @since 01/02/2016
 */
public class HostCreateContent extends CreateModalContent<Host> {
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
    public HostCreateContent() throws ContextLoadException {
        super("/res/layout/layout_edit_host.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested(){
    }

    @Override
    public void onAction(){
        // TODO validate this


        // *Generating the selected icon's new name:
        FileBridge selectedIcon_fileBridge = new FileBridge(filePicker.getFile());
        String newIconFileName = new UID().toString() + "." + selectedIcon_fileBridge.getExtension();
        File copiedIconFile = new File(EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + newIconFileName);


        // *Copying selected icon to its appropriate folder:
        selectedIcon_fileBridge.copyTo(copiedIconFile, new SimpleResponseCallback() {
            @Override
            public void onSuccess() {


                // *Creating new entity instance:
                Host host = new Host(
                        0L,
                        consumeTitle(),
                        urlIn.getText(),
                        urlPatternIn.getText(),
                        newIconFileName,
                        Calendar.getInstance().getTimeInMillis());


                // *Creating the entity on the persistence layer:
                try {
                    DAO<Host> dao = new HostDAO();
                    dao.create(host, new CreateDAOCallback() {
                        @Override
                        public void onSuccess(Long id) {
                            //TODO
                        }

                        @Override
                        public void onFailure(Exception e) {
                            //TODO
                            //ERROR
                        }
                    });
                } catch (UndeclaredEntityException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });



    }
}
