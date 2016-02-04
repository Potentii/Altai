package view.controller.modal.content.detail;

import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;
import view.controller.modal.content.ModalContent;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 04/02/2016
 */
public abstract class DetailModalContent<T> extends ModalContent<T> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public DetailModalContent(String FXML) throws ContextLoadException {
        super(FXML);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    protected abstract void onDataBindRequested(@Nullable T data) throws UndeclaredEntityException, NullPointerException;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public void setData(T data) {
        this.data = data;
        try {
            onDataBindRequested(data);
        } catch (UndeclaredEntityException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
