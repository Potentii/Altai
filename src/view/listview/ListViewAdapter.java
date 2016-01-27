package view.listview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * @param <T> The entity's data type
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public abstract class ListViewAdapter<T> extends ListCell<T> {
    @FXML
    private Node root;


    @Override
    public void updateItem(T t, boolean empty) {
        super.updateItem(t, empty);

        if(empty || t ==null){
            // TODO
        } else {
            // *Binding the data onto the viewHolder:
            try {
                bindData(t);
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            // *Setting the graphic using the generated node:
            setGraphic(root);
        }
    }


    public ListViewAdapter(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getFXMLPath()));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        }
        catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @return the row's fxml path
     */
    protected abstract String getFXMLPath();
    public abstract void bindData(T data) throws NullPointerException;
}
