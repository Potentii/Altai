package view.controller.modal.window;

import com.sun.istack.internal.Nullable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.exception.ContextLoadException;

/**
 * Creates and controls a new editing/creation window.
 * @param <T> The entity's data type.
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class EditModalWindow<T> extends ModalWindow<T> {
    @FXML
    private TextField titleIn;

    private T data;



    public EditModalWindow(String contentFXML, @Nullable T data) throws ContextLoadException{
        super("/res/layout/layout_edit_scene.fxml", contentFXML, "Edit");
        this.data = data;
    }



    @Override
    protected void onControllerLoaded() {
        titleIn.setText(controller.getTitle());
        controller.setData(data);
        controller.setTitleSupplier(titleIn::getText);
    }



    @FXML
    private void confirmBtn_onClick(){
        if(controller != null){
            controller.onAction();
        }
    }
}
