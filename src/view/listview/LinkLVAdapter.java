package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import model.Link;

import java.text.DecimalFormat;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public class LinkLVAdapter extends ListViewAdapter<Link> {
    @FXML
    private ImageView ratingImg;
    @FXML
    private Label ratingOut;
    @FXML
    private Label titleOut;
    @FXML
    private Label descOut;


    @Override
    protected String getFXMLPath() {
        return "/layout/row_lv_link.fxml";
    }


    @Override
    public void bindData(Link data) throws NullPointerException {
        // *Hue varies between -0.1 to 0.7
        double hue = -0.1d + (data.getRating() * 0.8d);
        ratingImg.effectProperty().setValue(new ColorAdjust(hue, 0.5d, -0.1d, 0.0d));
        ratingOut.setText(new DecimalFormat("##").format(data.getRating() * 10));
        titleOut.setText(data.getTitle());
        descOut.setText("dfds f d fdsfdref dsf ewfwe fdf ergrhgdf gf gtghef wef ");
    }

}