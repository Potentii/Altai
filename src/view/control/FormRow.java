package view.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Guilherme Reginaldo
 * @since 31/01/2016
 */
public class FormRow extends HBox {

    public FormRow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/control_form_row.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
