package view.controller.modal.content.detail;

import com.sun.istack.internal.Nullable;
import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Host;
import org.jetbrains.annotations.NotNull;
import view.controller.modal.content.form.edit.HostEditContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;


/**
 * @author Guilherme Reginaldo
 * @since 04/02/2016
 */
public class HostDetailContent extends DetailModalContent<Host> {
    @FXML
    private ImageView iconImg;
    @FXML
    private Label urlOut;
    @FXML
    private Label urlPatternOut;
    @FXML
    private Label dateOut;




    public HostDetailContent() throws ContextLoadException {
        super("/layout/layout_detail_host.fxml");
    }


    @Override
    protected void onDataBindRequested(@Nullable Host data) throws UndeclaredEntityException, NullPointerException {
        iconImg.setImage(new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + data.getImgPath()));
        urlOut.setText(data.getUrl());
        urlPatternOut.setText(data.getUrlPattern());
        dateOut.setText(data.getDate() + "");
    }

    @Override
    public void onAction() {
        try{
            EditModalWindow<Host> editWindow = new EditModalWindow<>(new HostEditContent(), data, "Edit host");
            editWindow.setOnActionFinishedCallback(this::setData);
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    @Override
    public String getHeaderTitle() {
        return data.getTitle();
    }
}
