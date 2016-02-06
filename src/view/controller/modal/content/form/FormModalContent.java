package view.controller.modal.content.form;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import view.controller.modal.content.ModalContent;
import view.exception.ContextLoadException;
import java.util.function.Supplier;

/**
 * @author Guilherme Reginaldo
 * @since 04/02/2016
 */
public abstract class FormModalContent<T> extends ModalContent<T> {
    private Supplier<TextField> titleInSupplier;
    private Supplier<Label> titleErrorOutSupplier;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public FormModalContent(String FXML) throws ContextLoadException {
        super(FXML);

    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public final void setTitleInSupplier(Supplier<TextField> supplier){
        titleInSupplier = supplier;
    }
    public final void setTitleErrorOutSupplier(Supplier<Label> supplier){
        titleErrorOutSupplier = supplier;
    }

    @Nullable
    protected final TextField getTitleIn(){
        if(titleInSupplier != null){
            return titleInSupplier.get();
        }
        return null;
    }
    @Nullable
    protected final Label getTitleErrorOut(){
        if(titleErrorOutSupplier != null){
            return titleErrorOutSupplier.get();
        }
        return null;
    }
}
