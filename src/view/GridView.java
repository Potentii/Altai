package view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import view.listview.GridViewAdapter;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class GridView<E> extends TilePane {
    private E selectedItem;
    private Supplier<GridViewAdapter<E>> cellSupplier;
    private EventHandler<? super MouseEvent> onClickListener;



    public GridView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/control_grid_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }




    public void setItems(List<E> itemList){
        if(getChildren().size() > 0) {
            getChildren().remove(0, getChildren().size());
        }

        for (E item : itemList) {
            buildCell(item);
        }
    }


    public void setCellFactory(Supplier<GridViewAdapter<E>> cellSupplier){
        this.cellSupplier = cellSupplier;
    }


    private void buildCell(E item){
        GridViewAdapter<E> cellAdapter = cellSupplier.get();
        Node node = cellAdapter.updateItem(item);
        node.setOnMouseClicked(event -> {
            selectedItem = item;
            onClickListener.handle(event);
        });
        getChildren().add(node);
    }



    public void setOnClickListener(EventHandler<? super MouseEvent> event){
        onClickListener = event;
    }

    public E getSelectedItem() {
        return selectedItem;
    }
}
