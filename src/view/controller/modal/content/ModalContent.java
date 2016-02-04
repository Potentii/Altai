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
    protected T data;
    private Supplier<String> titleSupplier;
    private Callback onActionFinishedCallback;



    protected ModalContent(String FXML) throws ContextLoadException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        try {
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            throw new ContextLoadException(e.getCause().getMessage());
        }
    }


    public final T getData(){
        return data;
    }

    /**
     * This is called whenever the data is set, by the modal window.
     * @param data The updated entity data
     */
    public abstract void setData(@Nullable T data);



    @NotNull
    public abstract String getHeaderTitle();
    @NotNull
    public abstract String getHeaderHint();


    /**
     * Called wheter the main action is triggered.
     * <p>
     * Obs: {@link #onActionFinished} should be called when the window has to be closed.
     */
    public abstract void onAction();



    public final void setTitleSupplier(Supplier<String> supplier){
        titleSupplier = supplier;
    }

    @Nullable
    protected final String consumeTitle(){
        if(titleSupplier != null){
            return titleSupplier.get();
        }
        return null;
    }


    public final Node getNode(){
        return root;
    }



    public final void setOnActionFinishedCallback(@NotNull Callback callback){
        onActionFinishedCallback = callback;
    }

    protected void onActionFinished(){
        onActionFinishedCallback.call();
    }
}
