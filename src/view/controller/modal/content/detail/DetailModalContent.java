package view.controller.modal.content.detail;

import controller.persistence.UndeclaredEntityException;
import javafx.scene.control.Label;
import org.jetbrains.annotations.Nullable;
import view.controller.modal.content.ModalContent;
import view.exception.ContextLoadException;

import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 04/02/2016
 */
public abstract class DetailModalContent<T> extends ModalContent<T> {
    private Supplier<Label> titleOutSupplier;



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

    public final void setTitleOutSupplier(Supplier<Label> supplier){
        titleOutSupplier = supplier;
    }
    @Nullable
    protected final Label getTitleOut(){
        if(titleOutSupplier != null){
            return titleOutSupplier.get();
        }
        return null;
    }

}
