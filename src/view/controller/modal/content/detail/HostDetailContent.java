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



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public HostDetailContent() throws ContextLoadException {
        super("/layout/layout_detail_host.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DetailModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onDataBindRequested(@Nullable Host data) throws UndeclaredEntityException, NullPointerException {
        getTitleOut().setText(data.getTitle());
        iconImg.setImage(new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + data.getImgPath()));
        urlOut.setText(data.getUrl());
        urlPatternOut.setText(data.getUrlPattern());
        dateOut.setText(data.getDate() + "");
    }

    @Override
    public void onAction() {
        try{
            EditModalWindow<Host> editWindow = new EditModalWindow<>(new HostEditContent(), data, "Edit host");
            editWindow.setOnActionFinishedCallback(entity -> {
                setData(entity);

                onActionFinished();
            });
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }

}
