package view.controller.modal.window;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.callback.Callback;
import view.controller.modal.content.ModalContent;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @param <T> The entity's data type.
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ModalWindow<T> {
    @FXML
    private BorderPane contentContainer;

    private Consumer<T> onActionFinishedCallback;
    private Stage window;
    protected ModalContent<T> controller;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public ModalWindow(@NotNull String sceneFXML, @NotNull ModalContent<T> controller, @NotNull String title) throws ContextLoadException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(450);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneFXML));
            loader.setController(this);
            window.setScene(new Scene(loader.load()));
            window.show();
            contentContainer.setCenter(controller.getNode());

            controller.setOnActionFinishedCallback(this::onActionFinished);
            this.controller = controller;
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */

    public void setOnActionFinishedCallback(Consumer<T> callback){
        onActionFinishedCallback = callback;
    }
    protected void onActionFinished(){
        Platform.runLater(() -> {
            if(onActionFinishedCallback != null) {
                onActionFinishedCallback.accept(controller.getData());
            }

            window.close();
        });
    }


    /*
    @FXML
    private void onAction(){
        if(controller != null) {
            controller.onAction();
        }
    }
    */


    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public ModalContent<T> getController() {
        return controller;
    }
    public void setController(ModalContent<T> controller) {
        this.controller = controller;
    }
}
