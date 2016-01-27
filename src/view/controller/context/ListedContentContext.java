package view.controller.context;

import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public abstract class ListedContentContext<T> extends ContextController{
    protected List<T> dataList;

    public abstract void onPrepareForDelete();
    public abstract void onDeleteRequested(List<T> deleteList);
}
