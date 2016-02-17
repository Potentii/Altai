package view.listview;

import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Star;

import java.text.DecimalFormat;

/**
 * @author Guilherme Reginaldo
 * @since 28/01/2016
 */
public class StarGVAdapter extends GridViewAdapter<Star> {
    @FXML
    private ImageView mainImg;
    @FXML
    private Label titleOut;
    @FXML
    private Label ratingOut;



    @Override
    protected String getFXMLPath() {
        return "/layout/row/row_gv_star.fxml";
    }

    @Override
    public void bindData(Star data) throws NullPointerException {
        titleOut.setText(data.getTitle());
        ratingOut.setText(new DecimalFormat("#.#").format(data.getRating()));

        new Thread(() -> {
            Image image = new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.STAR_MAIN_IMG_RELATIVE_PATH.getValue() + data.getMainImage());
            double imgH = image.getHeight();
            double imgW = image.getWidth();


            Rectangle2D viewPort;
            if(imgH > imgW){
                viewPort = new Rectangle2D(0, (imgH/2) - (imgW/2), imgW, imgW);
            } else if(imgH < imgW){
                viewPort = new Rectangle2D((imgW/2) - (imgH/2), 0, imgH, imgH);
            } else{
                viewPort = new Rectangle2D(0, 0, imgH, imgW);
            }

            Platform.runLater(() -> {
                mainImg.setViewport(viewPort);
                mainImg.setImage(image);
            });
        }).start();
    }
}
