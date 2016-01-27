package view.controller.modal.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.controller.modal.content.ModalContent;
import view.exception.ContextLoadException;

import java.io.IOException;

/**
 * @param <T> The entity's data type.
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ModalWindow<T> {
    @FXML
    private BorderPane contentContainer;

    protected ModalContent<T> controller;


    public ModalWindow(String sceneFXML, String contentFXML, String title) throws ContextLoadException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(400);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFXML));
            loader.setController(this);
            window.setScene(new Scene(loader.load()));
            window.show();
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }

        loadContent(contentFXML);
    }



    private void loadContent(String fxml) throws ContextLoadException{
        try {
            // *Loading the FXML:
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane newNode = loader.load();

            // *Adding the new content:
            contentContainer.setCenter(newNode);

            // *Getting the controller:
            controller = loader.getController();
            onControllerLoaded();
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }
    }

    protected abstract void onControllerLoaded();

    //protected abstract void onContentLoaded();
}
