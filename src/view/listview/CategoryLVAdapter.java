package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Category;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class CategoryLVAdapter extends ListViewAdapter<Category> {
    @FXML
    private Label titleOut;

    @Override
    protected String getFXMLPath() {
        return "/layout/row_lv_category.fxml";
    }

    @Override
    public void bindData(Category data) throws NullPointerException {
        titleOut.setText(data.getTitle());
    }
}
