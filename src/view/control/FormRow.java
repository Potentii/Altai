package view.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @author Guilherme Reginaldo
 * @since 31/01/2016
 */
public class FormRow extends HBox {
    @FXML
    private ImageView iconImg;

    private String iconPath;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
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



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * FXML properties methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public final void setIcon(String iconPath) {
        if(iconPath.trim().isEmpty()){
            getChildren().remove(iconImg);
        } else{
            iconImg.setImage(new Image(iconPath));
        }
        this.iconPath = iconPath;
    }

    public final String getIcon() {
        return iconPath;
    }
}
