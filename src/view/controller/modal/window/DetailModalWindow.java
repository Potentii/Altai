package view.controller.modal.window;

import com.sun.istack.internal.Nullable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import view.exception.ContextLoadException;

/**
 * Creates and controls a new display window.
 * @param <T> The entity's data type.
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class DetailModalWindow<T>/* extends ModalWindow<T>*/{
    @FXML
    private Label titleOut;

    private T data;


/*
    public DetailModalWindow(String contentFXML, @Nullable T data) throws ContextLoadException{
        super("/res/layout/layout_edit_scene.fxml", contentFXML, "Show");
        this.data = data;
    }



    @Override
    protected void onControllerLoaded() {
        controller.setData(data);
    }



    @FXML
    private void editBtn_onClick(){
    }
    */
}
