package view.controller.scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.controller.DetailContextController;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class DetailSceneController<T>{
    @FXML
    private Label titleOut;
    @FXML
    private BorderPane contentContainer;

    private DetailContextController<T> controller;



    public DetailSceneController(String fxml, T data) throws ContextLoadException{
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("");
        window.setMinWidth(300);
        window.setMinHeight(450);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/layout/layout_detail_scene.fxml"));
            loader.setController(this);
            window.setScene(new Scene(loader.load()));
            window.show();
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }

        loadContent(fxml, data);
    }



    private void loadContent(String fxml, T data) throws ContextLoadException{
        try {
            // *Loading the FXML:
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane newNode = loader.load();

            // *Adding the new content:
            contentContainer.setCenter(newNode);

            // *Getting the controller:
            controller = loader.getController();
            controller.setData(data);
            titleOut.setText(controller.getTitle());

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            throw new ContextLoadException(e.getMessage());
        }
    }


    @FXML
    private void editBtn_onClick(){
        //TODO
        controller.getData();
    }



    public void setOnFinishedCallback(){

    }

    /*
    @Override
    public void close() {
        super.close();

        System.out.println("Close stage requested");
    }
    */
}
