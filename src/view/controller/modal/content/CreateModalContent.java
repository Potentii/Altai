package view.controller.modal.content;

import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public abstract class CreateModalContent<T> extends ModalContent<T> {

    public CreateModalContent(String FXML) throws ContextLoadException {
        super(FXML);
    }


    @Override
    public final void setData(@Nullable T data){
        this.data = data;
        try {
            onInitializationRequested();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void onInitializationRequested();

    @Override
    public final String getTitle() {
        return "";
    }
}
