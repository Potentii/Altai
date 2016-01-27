package view.controller.modal.content;

import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Category;
import model.Link;
import model.dao.DAO;

/**
 * @author Guilherme Reginaldo
 * @since 27/01/2016
 */
public class LinkEditModalContent extends ModalContent<Link>{

    @FXML
    private TextField urlIn;
    @FXML
    private TextField descIn;
    @FXML
    private ComboBox<Category> categoryIn; // TODO change to a map<Category, Boolean>



    @Override
    protected void onDataBindRequested(Link data) throws UndeclaredEntityException, NullPointerException {
        // *If it's editing do the binding:
        if(data != null){
            urlIn.setText(data.getUrl());
            descIn.setText(data.getDescription());
            // TODO categoryIn.setItems();
        }

    }

    @Override
    public String getTitle() {
        return data==null?"":data.getTitle();
    }

    @Override
    public void onAction() {

    }
}
