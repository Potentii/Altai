package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Category;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class CategoryCBAdapter extends CellAdapter<Category> {
    @FXML
    private Label titleOut;

    @NotNull
    @Override
    protected String getFXMLPath() {
        return "/layout/row_cb_category.fxml";
    }

    @Override
    public void bindData(Category data) throws NullPointerException {
        titleOut.setText(data.getTitle());
    }
}
