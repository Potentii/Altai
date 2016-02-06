package view.controller.modal.content.detail;

import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Category;
import org.jetbrains.annotations.Nullable;
import view.controller.modal.content.form.edit.CategoryEditContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class CategoryDetailContent extends DetailModalContent<Category> {
    @FXML
    private Label dateOut;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryDetailContent() throws ContextLoadException {
        super("/layout/layout_detail_category.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * DetailModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onDataBindRequested(@Nullable Category data) throws UndeclaredEntityException, NullPointerException {
        getTitleOut().setText(data.getTitle());
        dateOut.setText(data.getDate() + "");
    }

    @Override
    public void onAction() {
        try{
            EditModalWindow<Category> editWindow = new EditModalWindow<>(new CategoryEditContent(), data, "Edit category");
            editWindow.setOnActionFinishedCallback(entity -> {
                setData(entity);

                onActionFinished();
            });
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
}
