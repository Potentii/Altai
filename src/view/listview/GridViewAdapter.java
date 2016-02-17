package view.listview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public abstract class GridViewAdapter<T> {
    @FXML
    protected Node root;

    protected T data;



    public Node updateItem(T t) {
        data = t;
        if(t == null){
            // TODO
        } else {
            // *Binding the data onto the viewHolder:
            try {
                bindData(t);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return root;
    }


    public GridViewAdapter(){
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


    public Node getRoot() {
        return root;
    }

    public T getData() {
        return data;
    }
}
