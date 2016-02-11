package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import model.Link;
import org.jetbrains.annotations.NotNull;
import view.Standalone;

import java.text.DecimalFormat;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public class LinkLVAdapter extends CellAdapter<Link> {
    @FXML
    private ImageView ratingImg;
    @FXML
    private ImageView favoriteImg;
    @FXML
    private Label ratingOut;
    @FXML
    private Hyperlink titleOut;
    @FXML
    private ImageView flagImg;
    @FXML
    private Label descOut;


    @NotNull
    @Override
    protected String getFXMLPath() {
        return "/layout/row_lv_link.fxml";
    }


    @Override
    public void bindData(Link data) throws NullPointerException {
        // *Hue varies between -0.1 to 0.7
        double hue = -0.1d + ((data.getRating()/10d) * 0.8d);
        ratingImg.effectProperty().setValue(new ColorAdjust(hue, 0.6d * data.getRating()/10d, -0.05d, 0.0d));

        favoriteImg.setVisible(data.isFavorite());
        favoriteImg.setFitWidth(data.isFavorite() ? 16:0.05);

        flagImg.setVisible(data.isFlagged());

        ratingOut.setText(new DecimalFormat("#.#").format(data.getRating()));
        titleOut.setText(data.getTitle());
        titleOut.setOnAction(event -> Standalone.requestHostServices().showDocument(data.getUrl()));
        descOut.setText(data.getDescription());
    }

}
