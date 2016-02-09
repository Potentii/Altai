package view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import org.jetbrains.annotations.NotNull;
import util.KeyValue;
import view.listview.CellAdapter;

import java.util.function.BiConsumer;

/**
 * @author Guilherme Reginaldo
 * @since 08/02/2016
 */
public abstract class SelectionLVAdapter<T> extends CellAdapter<KeyValue<T, Boolean>> {
    @FXML
    private CheckBox checkBox;

    private T data;

    @NotNull
    @Override
    protected String getFXMLPath() {
        return "/layout/row_lv_selection.fxml";
    }

    @Override
    public void bindData(KeyValue<T, Boolean> data) throws NullPointerException {
        this.data = data.getKey();
        checkBox.setSelected(data.getValue() == null ? false: data.getValue());
        checkBox.setText(onTextRequested(this.data));
    }

    public void setOnStatusChanged(@NotNull BiConsumer<T, Boolean> statusConsumer) {
        checkBox.setOnAction(event -> statusConsumer.accept(data, checkBox.isSelected()));
    }

    @NotNull
    protected abstract String onTextRequested(T data);
}
