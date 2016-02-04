package view.listview;

import controller.persistence.EAltaiPersistence;
import controller.persistence.PersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Host;
import view.Standalone;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class HostLVAdapter extends ListViewAdapter<Host> {
    @FXML
    private Hyperlink titleOut;
    @FXML
    private ImageView hostImg;


    @Override
    protected String getFXMLPath() {
        return "/layout/row_lv_host.fxml";
    }

    @Override
    public void bindData(Host data) throws NullPointerException {

        hostImg.setImage(new Image("file:" + PersistenceManager.getInstance().getRootPath() + EAltaiPersistence.HOST_ICON_RELATIVE_PATH.getValue() + data.getImgPath()));

        titleOut.setText(data.getTitle());
        titleOut.setOnAction(event -> Standalone.requestHostServices().showDocument(data.getUrl()));
    }
}
