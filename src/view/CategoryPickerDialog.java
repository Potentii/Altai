package view;

import javafx.fxml.FXML;
import model.Category;
import org.jetbrains.annotations.NotNull;
import util.KeyValue;
import util.callback.Callback;
import view.control.SelectionListView;
import view.exception.ContextLoadException;

import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 07/02/2016
 */
public class CategoryPickerDialog extends DialogFragment {
    @FXML
    private SelectionListView<Category> selectionListView;


    @NotNull
    private Callback neutralBtnCallback;
    @NotNull
    private Callback positiveBtnCallback;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryPickerDialog() throws ContextLoadException {
        super("/layout/layout_dialog_category_picker.fxml", "/image/ic_category_24.png", "Select categories");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DialogFragment methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    @NotNull
    protected Builder onCreate(Builder builder) {
        selectionListView.setCellAdapter(() ->
                new SelectionLVAdapter<Category>() {
                    @NotNull
                    @Override
                    protected String onTextRequested(Category data) {
                        return data.getTitle();
                    }
                });


        builder.setNeutralButton("Cancel", () -> {
            neutralBtnCallback.call();
            close();
        });
        builder.setPositiveButton("OK", () -> {
            positiveBtnCallback.call();
            close();
        });

        return builder;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setNeutralBtnCallback(@NotNull Callback neutralBtnCallback) {
        this.neutralBtnCallback = neutralBtnCallback;
    }

    public void setPositiveBtnCallback(@NotNull Callback positiveBtnCallback) {
        this.positiveBtnCallback = positiveBtnCallback;
    }

    @NotNull
    public Map<Category, Boolean> getSelectionMap() {
        Map<Category, Boolean> selectionMap = new LinkedHashMap<>();
        for (KeyValue<Category, Boolean> keyValue : selectionListView.getItems()) {
            selectionMap.put(keyValue.getKey(), keyValue.getValue());
        }
        return selectionMap;
    }
    public void setSelectionMap(@NotNull Map<Category, Boolean> selectionMap) {
        List<KeyValue<Category, Boolean>> keyValueList = new ArrayList<>();
        selectionMap.forEach((k, v) -> keyValueList.add(new KeyValue<>(k, v)));

        this.selectionListView.setItems(keyValueList);
    }
    public void setSelectionList(@NotNull List<Category> itemList) {
        List<KeyValue<Category, Boolean>> keyValueList = new ArrayList<>();
        itemList.forEach(k -> keyValueList.add(new KeyValue<>(k, false)));

        this.selectionListView.setItems(keyValueList);
    }
}
