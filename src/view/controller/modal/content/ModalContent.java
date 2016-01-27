package view.controller.modal.content;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Controls the content of a modal window.
 * @param <T> the entity type.
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ModalContent<T> {
    protected T data;
    private Supplier<String> titleSupplier;

    public final void setData(@Nullable T data){
        this.data = data;
        try {
            onDataBindRequested(data);
        } catch (UndeclaredEntityException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public final T getData(){
        return data;
    }

    protected abstract void onDataBindRequested(@Nullable T data) throws UndeclaredEntityException, NullPointerException;

    @NotNull
    public abstract String getTitle();
    public abstract void onAction();




    public void setTitleSupplier(Supplier<String> supplier){
        titleSupplier = supplier;
    }

    @Nullable
    protected String consumeTitle(){
        if(titleSupplier != null){
            return titleSupplier.get();
        }
        return null;
    }




    public void setFinishedCallback(){

    }
    protected void finish(){
        // TODO call the callback
    }


}
