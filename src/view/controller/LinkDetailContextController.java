package view.controller;

import controller.persistence.UndeclaredEntityException;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import model.*;
import model.dao.*;
import model.dao.callback.RetrieveMultipleDAOCallback;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 26/01/2016
 */
public class LinkDetailContextController extends DetailContextController<Link>{
    @FXML
    private ImageView ratingImg;
    @FXML
    private Label ratingOut;
    @FXML
    private ImageView hostImg;
    @FXML
    private Hyperlink urlOut;
    @FXML
    private Label dateOut;
    @FXML
    private ListView<Category> categoryListView;


    @Override
    protected void onDataBindRequested(Link data) throws UndeclaredEntityException, NullPointerException {
        // TODO get host info

        // *Loading link's categories:
        DAO<AssociativeEntity> linkCategoryDAO = new AssociativeEntityDAO<>(LinkCategoryAssociation.class);
        linkCategoryDAO.retrieveMultiple(
                associativeEntity -> associativeEntity.getPrimaryId() == data.getId(),
                Comparator.comparing(AssociativeEntity::getId),
                new RetrieveMultipleDAOCallback<AssociativeEntity>() {
                    @Override
                    public void onSuccess(List<AssociativeEntity> responseList) {
                        // TODO list the categories
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // ERROR
                    }
                }
        );



        // *Hue varies between -0.1 to 0.7
        double hue = -0.1d + (data.getRating() * 0.8d); // TODO make this rating field a separate controller
        ratingImg.effectProperty().setValue(new ColorAdjust(hue, 0.5d, -0.1d, 0.0d));
        ratingOut.setText(new DecimalFormat("##").format(data.getRating() * 10));

        urlOut.setText(data.getUrl());
        dateOut.setText("" + data.getDate()); // TODO need to transform on a date string

    }

    @Override
    public String getTitle() {
        return data.getTitle();
    }
}
