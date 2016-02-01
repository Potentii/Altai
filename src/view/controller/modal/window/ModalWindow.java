package view.controller.modal.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    /*@Nullable
    protected T data;*/

    /*
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
    */
    public ModalWindow(@NotNull String sceneFXML, @NotNull ModalContent<T> controller, @Nullable  T data, @NotNull String title) throws ContextLoadException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(400);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFXML));
            loader.setController(this);
            window.setScene(new Scene(loader.load()));
            window.show();

            this.controller = controller;
            contentContainer.setCenter(controller.getNode());

            controller.setOnActionFinishedCallback(window::close);
            controller.setData(data);
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }


    }



    /*
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
    */

    //protected abstract void onControllerLoaded();

    //protected abstract void onContentLoaded();
}
