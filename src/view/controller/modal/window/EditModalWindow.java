package view.controller.modal.window;

import com.sun.istack.internal.Nullable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import view.controller.modal.content.ModalContent;
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



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public EditModalWindow(ModalContent<T> controller, @Nullable T data, String title) throws ContextLoadException{
        super("/res/layout/layout_edit.fxml", controller, data, title);
        titleIn.setText(controller.getHeaderTitle());
        titleIn.setPromptText(controller.getHeaderHint());
        controller.setTitleSupplier(titleIn::getText);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @FXML
    private void confirmBtn_onClick(){
        if(controller != null){
            controller.onAction();
        }
    }
}
