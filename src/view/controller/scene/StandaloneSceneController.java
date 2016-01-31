package view.controller.scene;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Category;
import view.controller.context.*;
import view.controller.modal.content.CategoryCreateContent;
import view.controller.modal.window.EditModalWindow;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 23/01/2016
 */
public class StandaloneSceneController implements Initializable{
    private Timeline timeline;

    @FXML
    private ScrollPane navigationDrawer;
    @FXML
    private StackPane standaloneRoot;
    @FXML
    private BorderPane contentContainer;


    @FXML
    private Button menuBtn;
    @FXML
    private Label appBarTitle;


    private ContextController<?> contextController;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO get default screen
        menuBtn.setOnAction(event -> menuClosedBtn_onClick());

        loadCategoriesContext();
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
     * Change the content's controller.
     * @param fxml The fmxl file's path
     * @throws ContextLoadException If content couldn't be loaded
     */
    private <T extends ContextController> T changeContent(String fxml, T controller) throws ContextLoadException{
        closeNav();
        try {
            // *Loading the FXML:
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setController(controller);
            Node newNode = loader.load();

            // *Setting the new content:
            contentContainer.setCenter(newNode);
            return controller;
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getMessage());
        }
    }


    public void loadLinkContext(){
        try {
            changeContent("/layout/layout_context_list.fxml", new LinkContext());
            appBarTitle.setText("Links");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadStarsContext(){
        try {
            changeContent("/layout/layout_context_grid.fxml", new StarContext());
            appBarTitle.setText("Stars");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadPicturesContext(){
        try {
            changeContent("/layout/layout_context_list.fxml", new LinkContext()); // TODO
            appBarTitle.setText("Pictures");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadMoviesContext(){
        try {
            changeContent("/layout/layout_context_list.fxml", new LinkContext()); // TODO
            appBarTitle.setText("Movies");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadSettingsContext(){
        try {
            SettingsContext controller = changeContent("/layout/layout_context_settings.fxml", new SettingsContext());
            appBarTitle.setText("Settings");
            controller.setCategoryBtnCallback(this::loadCategoriesContext);
            controller.setHostBtnCallback(this::loadHostsContext);
        } catch (ContextLoadException | ClassCastException e) {
            e.printStackTrace();
        }
    }
    public void loadHostsContext(){
        try {
            changeContent("/layout/layout_context_list.fxml", new HostContext());
            appBarTitle.setText("Hosts");
        } catch (ContextLoadException e) {
            e.printStackTrace();
        }
    }
    public void loadCategoriesContext(){
        try {
            changeContent("/layout/layout_context_list.fxml", new CategoryContext());
            appBarTitle.setText("Categories");
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
