package view;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import view.listview.GridViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class GridView<E> extends GridPane{
    @FXML
    private int maxColumns;

    private int lastColumn = -1;
    private int lastRow = 0;

    private Supplier<GridViewAdapter<E>> cellSupplier;

    private Map<E, GridViewAdapter<E>> itemCellMap;


    public GridView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/layout/GridView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        itemCellMap = new HashMap<>();
    }


    public void setItems(List<E> itemList){
        for (E item : itemList) {
            buildCell(item);
        }
    }


    public void setCellFactory(Supplier<GridViewAdapter<E>> cellSupplier){
        this.cellSupplier = cellSupplier;
    }


    private void buildCell(E item){
        GridViewAdapter<E> cell = cellSupplier.get();



        int newColumn = lastColumn+1;
        int newRow = lastRow;
        if(newColumn > maxColumns){
            System.out.println("df");
            newColumn = 0;
            newRow++;
            addRow(newRow);
        }
        lastColumn = newColumn;
        lastRow = newRow;



        try {
            Node node = cell.updateItem(item);
            node.setOnMouseClicked(onClickListener);
            add(node, newColumn, newRow);
        }catch (Exception e){
            //e.printStackTrace();
        }
        itemCellMap.put(item, cell);
    }

    private EventHandler<? super MouseEvent> onClickListener;

    public void setOnClickListener(EventHandler<? super MouseEvent> event){
        onClickListener = event;
    }


    public void setMaxColumns(int maxColumns) {
        this.maxColumns = maxColumns;
        for (int i = 0; i < maxColumns; i++) {
            addColumn(i);
        }
    }
}
