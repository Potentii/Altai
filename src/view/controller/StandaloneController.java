package view.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
public class StandaloneController implements Initializable{
    private Timeline timeline;

    @FXML
    private ScrollPane navigationDrawer;
    @FXML
    private StackPane standaloneRoot;
    @FXML
    private BorderPane contentContainer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO get default screen

        loadLinkContext();
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Navigation drawer animation methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     * Opens the navigation drawer.
     */
    private void openNav(){
        timeline = new Timeline();
        timeline.setCycleCount(1);
        navigationDrawer.setOnMouseEntered(event1 -> showNavLabels());
        navigationDrawer.setOnMouseExited(event1 -> hideNavLabels());

        KeyValue keyValue1 = new KeyValue(navigationDrawer.maxWidthProperty(), 68, Interpolator.EASE_IN);
        KeyValue keyValue2 = new KeyValue(navigationDrawer.minWidthProperty(), 68, Interpolator.EASE_IN);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(100), keyValue1, keyValue2);

        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
    }

    /**
     * Closes the navigation drawer.
     */
    private void closeNav(){
        navigationDrawer.setOnMouseEntered(event1 -> {});
        navigationDrawer.setOnMouseExited(event1 -> {});

        timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyValue keyValue1 = new KeyValue(navigationDrawer.minWidthProperty(), 0, Interpolator.EASE_BOTH);
        KeyValue keyValue2 = new KeyValue(navigationDrawer.maxWidthProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(150), keyValue1, keyValue2);

        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
    }

    /**
     * Expands the navigation drawer so its labels become visible.
     */
    private void showNavLabels(){
        timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyValue keyValue1 = new KeyValue(navigationDrawer.maxWidthProperty(), 192, Interpolator.EASE_OUT);
        KeyValue keyValue2 = new KeyValue(navigationDrawer.minWidthProperty(), 192, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(182), keyValue1, keyValue2);

        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
    }

    /**
     * Shrinks the navigation drawer hiding its labels.
     */
    private void hideNavLabels(){
        timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyValue keyValue1 = new KeyValue(navigationDrawer.minWidthProperty(), 68, Interpolator.EASE_OUT);
        KeyValue keyValue2 = new KeyValue(navigationDrawer.maxWidthProperty(), 68, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(150), keyValue1, keyValue2);

        timeline.getKeyFrames().add(keyFrame1);
        timeline.play();
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Load content methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     *
     * @param fxml The fmxl file's path
     * @param <T> {@link ContextController} subtype of the new content
     * @return  The controller
     * @throws ContextLoadException If content couldn't be loaded
     */
    private <T extends ContextController> T changeContent(String fxml) throws ContextLoadException{
        closeNav();
        try {
            // *Loading the FXML:
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane newNode = loader.load();

            // *Removing all the current children:
            /*
            ObservableList<Node> children = contentContainer.getChildren();
            for (int i = 0; i < children.size(); i++) {
                children.remove(i);
            }*/

            // *Adding the new content:
            //contentContainer.getChildren().add(newNode);
            contentContainer.setCenter(newNode);

            // *Getting the controller:
            T controller = loader.getController();

            // *Setting the "menuBtn" listener:
            controller.setMenuBtnClickListener(event -> menuClosedBtn_onClick());

            return controller;
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            throw new ContextLoadException(e.getMessage());
        }
    }


    public void loadLinkContext(){
        try {
            changeContent("/layout/layout_link_context.fxml");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadStarsContext(){
        try {
            changeContent("/layout/layout_link_context.fxml"); // TODO
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadPicturesContext(){
        try {
            changeContent("/layout/layout_link_context.fxml"); // TODO
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadMoviesContext(){
        try {
            changeContent("/layout/layout_link_context.fxml"); // TODO
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadSettingsContext(){
        try {
            changeContent("/layout/layout_link_context.fxml"); // TODO
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void menuOpenedBtn_onClick(){
        closeNav();
    }
    public void menuClosedBtn_onClick(){
        openNav();
    }
    public void linksBtn_onClick(){
        loadLinkContext();
    }
    public void starsBtn_onClick(){
        loadStarsContext();
    }
    public void picturesBtn_onClick(){
        loadPicturesContext();
    }
    public void moviesBtn_onClick(){
        loadMoviesContext();
    }
    public void settingsBtn_onClick(){
        loadSettingsContext();
    }
}
