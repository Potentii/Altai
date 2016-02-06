package view.controller.modal.content;

import org.jetbrains.annotations.NotNull;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.jetbrains.annotations.Nullable;
import util.callback.Callback;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Controls the content of a modal window.
 * @param <T> the entity type.
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ModalContent<T> {
    protected Node root;
    @Nullable
    protected T data;
    @Nullable
    private Callback onActionFinishedCallback;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    protected ModalContent(String FXML) throws ContextLoadException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        try {
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            throw new ContextLoadException(e.getCause().getMessage());
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Nullable
    public final T getData(){
        return data;
    }

    /**
     * This is called whenever the data is set, by the modal window.
     * @param data The updated entity data
     */
    public abstract void setData(@Nullable T data);

    public final Node getNode(){
        return root;
    }

    public final void setOnActionFinishedCallback(@Nullable Callback callback){
        onActionFinishedCallback = callback;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     * Called whether the main action is triggered.
     * <p>
     * Obs: {@link #onActionFinished} should be called when the window has to be closed.
     */
    public abstract void onAction();

    protected void onActionFinished(){
        if(onActionFinishedCallback != null) {
            onActionFinishedCallback.call();
        }
    }
}
