package view.control;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import util.KeyValue;
import view.SelectionLVAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 08/02/2016
 */
public class SelectionListView<T> extends ListView<KeyValue<T, Boolean>> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public SelectionListView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/control_selection_list_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Initializer:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private void initialize(){
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private void onSelectionStateChanged(T element, boolean newState){
        getItems().forEach(keyValue -> {
            if(element.equals(keyValue.getKey())){
                keyValue.setValue(newState);
            }
        });
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setCellAdapter(Supplier<SelectionLVAdapter<T>> supplier){
        super.setCellFactory(param -> {
            SelectionLVAdapter<T> cellAdapter = supplier.get();
            cellAdapter.setOnStatusChanged(this::onSelectionStateChanged);
            return cellAdapter;
        });
    }

    public void setItems(List<KeyValue<T, Boolean>> keyValueList){
        super.setItems(FXCollections.observableArrayList(keyValueList));
    }
}
