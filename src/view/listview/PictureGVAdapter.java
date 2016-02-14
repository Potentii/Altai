package view.listview;

import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Picture;
import org.jetbrains.annotations.NotNull;

/**
 * @author Guilherme Reginaldo
 * @since 13/02/2016
 */
public class PictureGVAdapter extends GridViewAdapter<Picture> {
    /*
    @FXML
    private ImageView root;
    */


    @Override
    @NotNull
    protected String getFXMLPath() {
        return "/layout/row_gv_picture.fxml";
    }

    @Override
    public void bindData(Picture data) throws NullPointerException {
        Image image = new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.PICTURE_RELATIVE_PATH.getValue() + data.getPath());
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

        ImageView pictureImg = (ImageView) root;
        pictureImg.setViewport(viewPort);
        pictureImg.setImage(image);
    }
}
