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
import model.dao.callback.CreateDAOCallback;
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



    public HostEditContent() throws ContextLoadException {
        super("/layout/layout_edit_host.fxml");
    }

    @Override
    protected void onDataBindRequested(@Nullable Host data) throws UndeclaredEntityException, NullPointerException {
        filePicker.setFileFilters(FilePicker.FILE_FILTER_IMAGE);
        urlIn.setText(data.getUrl());
        urlPatternIn.setText(data.getUrlPattern());
        filePicker.setFile(new File(PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + data.getImgPath()));
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
        // TODO
    }
}
