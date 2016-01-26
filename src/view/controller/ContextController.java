package view.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public abstract class ContextController {
    @FXML
    private Button menuBtn;


    public void setMenuBtnClickListener(EventHandler<MouseEvent> event){
        if(menuBtn != null){
            menuBtn.setOnMouseClicked(event);
        }
    }
}
