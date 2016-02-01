package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 31/01/2016
 */
public class FormRow extends HBox {

    public FormRow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/layout/row_form_text.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
