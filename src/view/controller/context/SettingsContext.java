package view.controller.context;

import javafx.fxml.FXML;
import util.callback.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class SettingsContext extends ContextController<Void> {
    private Callback hostBtnCallback;
    private Callback categoryBtnCallback;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setHostBtnCallback(Callback hostBtnCallback){
        this.hostBtnCallback = hostBtnCallback;
    }
    public void setCategoryBtnCallback(Callback categoryBtnCallback){
        this.categoryBtnCallback = categoryBtnCallback;
    }


    @FXML
    private void hostsBtn_onClick(){
        if(hostBtnCallback != null){
            hostBtnCallback.call();
        }
    }

    @FXML
    private void categoriesBtn_onClick(){
        if(categoryBtnCallback != null){
            categoryBtnCallback.call();
        }
    }
}
