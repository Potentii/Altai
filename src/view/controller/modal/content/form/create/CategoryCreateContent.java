package view.controller.modal.content.form.create;

import controller.persistence.UndeclaredEntityException;
import model.Category;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.callback.CreateDAOCallback;
import util.FormValidator;
import view.exception.ContextLoadException;

import java.util.Calendar;
import java.util.EnumSet;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public class CategoryCreateContent extends CreateModalContent<Category> {
    private FormValidator validator;


    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryCreateContent() throws ContextLoadException {
        super("/layout/content/edit/layout_edit_category.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested(){
        getTitleIn().setPromptText("Category's name");

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
                0L,
                getTitleIn().getText().trim(),
                Calendar.getInstance().getTimeInMillis());


        // *Creating the entity on the persistence layer:
        try {
            DAO<Category> dao = new CategoryDAO();
            dao.create(category, new CreateDAOCallback() {
                @Override
                public void onSuccess(Long id) {
                    category.setId(id);
                    data = category;
                    onActionFinished();
                }

                @Override
                public void onFailure(Exception e) {
                    // ERROR
                    // TODO
                    System.out.println("FAILURE");
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }
}
