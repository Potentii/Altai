package view.controller.modal.content;

import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public abstract class EditModalContent<T> extends ModalContent<T> {

    public EditModalContent(String FXML) throws ContextLoadException {
        super(FXML);
    }

    @Override
    public void setData(@Nullable T data){
        this.data = data;
        try {
            onDataBindRequested(data);
        } catch (UndeclaredEntityException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onDataBindRequested(@Nullable T data) throws UndeclaredEntityException, NullPointerException;
}
