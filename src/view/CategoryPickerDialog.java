package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Category;
import org.jetbrains.annotations.NotNull;
import util.callback.Callback;
import view.exception.ContextLoadException;

import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 07/02/2016
 */
public class CategoryPickerDialog extends DialogFragment {
    @FXML
    private ListView<Category> listView;

    private List<Category> itemList;
    private List<Category> selectedItemList;

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
        //TODO create the layout's fxml
        super("/layout/layout_dialog_category_picker.fxml", null, "Category picker");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DialogFragment methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected Builder onCreate(Builder builder) {


        builder.setNeutralButton("Cancel", neutralBtnCallback);
        builder.setNeutralButton("OK", positiveBtnCallback);

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
}
