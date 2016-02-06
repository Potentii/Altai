package view.controller.modal.content.form.edit;

import com.sun.istack.internal.Nullable;
import controller.persistence.UndeclaredEntityException;
import model.Category;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.callback.UpdateDAOCallback;
import util.FormValidator;
import view.exception.ContextLoadException;

import java.util.Calendar;
import java.util.EnumSet;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class CategoryEditContent extends EditModalContent<Category> {
    private FormValidator validator;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryEditContent() throws ContextLoadException {
        super("/layout/layout_edit_category.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * EditModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onDataBindRequested(@Nullable Category data) throws UndeclaredEntityException, NullPointerException {
        getTitleIn().setText(data.getTitle());
        getTitleIn().setPromptText("Category name");

        validator = new FormValidator()
                .addField(getTitleIn(), getTitleErrorOut(), EnumSet.of(FormValidator.EValidation.REQUIRED));

    }

    @Override
    public void onAction() {
        validator.doVisualValidation();
        if(!validator.isValid()){
            return;
        }


        // *Creating new entity instance:
        Category category = new Category(
                data.getId(),
                getTitleIn().getText(),
                Calendar.getInstance().getTimeInMillis());


        // *Creating the entity on the persistence layer:
        try {
            DAO<Category> dao = new CategoryDAO();
            dao.update(category, new UpdateDAOCallback() {
                @Override
                public void onSuccess() {
                    data = category;
                    onActionFinished();
                }

                @Override
                public void onFailure(Exception e) {
                    //TODO
                    //ERROR
                    System.out.println("FAILURE");
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }
}
