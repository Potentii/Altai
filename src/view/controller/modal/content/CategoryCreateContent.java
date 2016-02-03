package view.controller.modal.content;

import controller.persistence.UndeclaredEntityException;
import model.Category;
import model.dao.CategoryDAO;
import model.dao.DAO;
import model.dao.callback.CreateDAOCallback;
import view.exception.ContextLoadException;

import java.util.Calendar;

/**
 * @author Guilherme Reginaldo
 * @since 30/01/2016
 */
public class CategoryCreateContent extends CreateModalContent<Category> {



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public CategoryCreateContent() throws ContextLoadException {
        super("/res/layout/layout_edit_category.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested(){
    }

    @Override
    public void onAction() {
        // TODO validate this


        // *Creating new entity instance:
        Category entity = new Category(
                0L,
                consumeTitle(),
                Calendar.getInstance().getTimeInMillis());


        // *Creating the entity on the persistence layer:
        try {
            DAO<Category> dao = new CategoryDAO();
            dao.create(entity, new CreateDAOCallback() {
                @Override
                public void onSuccess(Long id) {
                    //TODO
                }

                @Override
                public void onFailure(Exception e) {
                    // ERROR
                    // TODO
                }
            });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
        }
    }
}
