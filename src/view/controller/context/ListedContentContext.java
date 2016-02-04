package view.controller.context;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ListedContentContext<T> extends ContextController{
    protected List<T> dataList;

    public abstract void onPrepareForDelete();
    public abstract void onDeleteRequested(List<T> deleteList);

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        onUpdateRequested();
    }

    protected abstract void onUpdateRequested();

    @FXML
    protected abstract void addBtn_onClick();
}
