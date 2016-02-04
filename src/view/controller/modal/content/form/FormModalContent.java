package view.controller.modal.content.form;

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
    private Supplier<String> titleSupplier;



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
    @NotNull
    public abstract String getHeaderHint();

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
}
