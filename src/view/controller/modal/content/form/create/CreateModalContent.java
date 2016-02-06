package view.controller.modal.content.form.create;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;
import view.controller.modal.content.form.FormModalContent;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public abstract class CreateModalContent<T> extends FormModalContent<T> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CreateModalContent(String FXML) throws ContextLoadException {
        super(FXML);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    protected abstract void onInitializationRequested();



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    public final void setData(@Nullable T data){
        this.data = data;
        try {
            onInitializationRequested();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
