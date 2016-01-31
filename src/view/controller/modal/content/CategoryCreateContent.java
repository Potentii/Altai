package view.controller.modal.content;

import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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


    public CategoryCreateContent() throws ContextLoadException {
        super("/res/layout/layout_edit_category.fxml");
    }


    @Override
    protected void onInitializationRequested() throws UndeclaredEntityException, NullPointerException {

    }


    @Override
    public void onAction() {
        if(consumeTitle() == null){
           return;
        }


        Category entity = new Category(0L, consumeTitle(), Calendar.getInstance().getTimeInMillis());

        try {
            DAO<Category> dao = new CategoryDAO();
            dao.create(entity, new CreateDAOCallback() {
                @Override
                public void onSuccess(Long id) {
                    onActionFinished();
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
