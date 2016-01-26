package view.controller;

import controller.persistence.UndeclaredEntityException;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public abstract class DetailContextController<T> {
    protected T data;

    public final void setData(T data){
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

    protected abstract void onDataBindRequested(T data) throws UndeclaredEntityException, NullPointerException;
    public abstract String getTitle();
}
