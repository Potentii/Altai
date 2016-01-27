package view.controller.context;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public abstract class ContextController<T> implements Initializable {

    @FXML
    private Button menuBtn;

    protected List<T> dataList;



    public abstract void onPrepareForDelete();

    public abstract void onDeleteRequested(List<T> deleteList);

    public void setMenuBtnClickListener(EventHandler<MouseEvent> event){
        if(menuBtn != null){
            menuBtn.setOnMouseClicked(event);
        }
    }
}
