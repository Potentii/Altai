package view.controller.modal.window;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import view.controller.modal.content.detail.DetailModalContent;
import view.exception.ContextLoadException;

/**
 * Creates and controls a new display window.
 * @param <T> The entity's data type.
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class DetailModalWindow<T> extends ModalWindow<T>{
    @FXML
    private Label titleOut;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public DetailModalWindow(DetailModalContent<T> controller, @NotNull T data, String title) throws ContextLoadException{
        super("/res/layout/layout_detail.fxml", controller, title);
        controller.setTitleOutSupplier(() -> titleOut);
        controller.setData(data);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @FXML
    private void editBtn_onClick(){
        if(controller != null){
            controller.onAction();
        }
    }
}
