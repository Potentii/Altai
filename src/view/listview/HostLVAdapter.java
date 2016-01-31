package view.listview;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
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
        titleOut.setText(data.getTitle());

        titleOut.setOnAction(event -> Standalone.requestHostServices().showDocument(data.getUrl()));

        /* TODO
        try {
            DAO<Picture> pictureDAO = new PictureDAO();
            pictureDAO.retrieve(data.getImgId_FK(), new RetrieveDAOCallback<Picture>() {
                @Override
                public void onSuccess(Picture response) {
                    hostImg.setImage(new Image(response.getPath()));
                }

                @Override
                public void onFailure(Exception e) {
                    // ERROR
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
        */
    }
}
