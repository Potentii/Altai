package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Star;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class StarGVAdapter extends GridViewAdapter<Star> {
    @FXML
    private Label titleOut;

    @Override
    protected String getFXMLPath() {
        return "/res/layout/cell_gv_star.fxml";
    }

    @Override
    public void bindData(Star data) throws NullPointerException {
        System.out.println("bind");
        titleOut.setText(data.getTitle());
    }
}
