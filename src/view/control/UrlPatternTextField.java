package view.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import util.UrlPatternUtil;
import util.Validatable;

import java.io.IOException;

/**
 * @author Guilherme Reginaldo
 * @since 09/02/2016
 */
public class UrlPatternTextField extends TextField implements Validatable {
    private String invalidText;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public UrlPatternTextField(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/control_url_pattern_text_field.fxml"));
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
     *  * Validatable methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public boolean isValid() {
        String text = this.getText().trim();
        if(text.isEmpty()){
            invalidText = "It should not be left empty";
            return false;
        } else if(!text.contains(UrlPatternUtil.TITLE_TAG)){
            invalidText = "It should have a " + UrlPatternUtil.TITLE_TAG + " tag";
            return false;
        } else if(text.split(UrlPatternUtil.TITLE_TAG, -1).length > 2){
            invalidText = "It should have just one " + UrlPatternUtil.TITLE_TAG + " tag";
            return false;
        }

        return true;
    }

    @Override
    public String getInvalidText() {
        return invalidText;
    }
}
