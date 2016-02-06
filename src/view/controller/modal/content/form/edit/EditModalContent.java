package view.controller.modal.content.form.edit;

import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;
import view.controller.modal.content.form.FormModalContent;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public abstract class EditModalContent<T> extends FormModalContent<T> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public EditModalContent(String FXML) throws ContextLoadException {
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
    public final void setData(@Nullable T data){
        this.data = data;
        try {
            onDataBindRequested(data);
        } catch (UndeclaredEntityException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
