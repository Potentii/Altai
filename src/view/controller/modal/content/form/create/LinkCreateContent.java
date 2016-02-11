package view.controller.modal.content.form.create;

import controller.persistence.UndeclaredEntityException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.*;
import model.dao.*;
import model.dao.callback.CreateDAOCallback;
import model.dao.callback.CreateMultipleDAOCallback;
import model.dao.callback.RetrieveMultipleDAOCallback;
import util.FormValidator;
import util.UrlPatternUtil;
import util.event.TaggedEvent;
import util.event.callback.EventFinishedCallback;
import view.CategoryPickerDialog;
import view.exception.ContextLoadException;

import java.util.*;

/**
 * @author Guilherme Reginaldo
 * @since 06/02/2016
 */
public class LinkCreateContent extends CreateModalContent<Link> {
    @FXML
    private TextField urlIn;
    @FXML
    private TextField descIn;
    @FXML
    private TextField ratingIn;
    @FXML
    private CheckBox favoriteIn;
    @FXML
    private CheckBox flaggedIn;
    @FXML
    private Button categoryPickerBtn;

    @FXML
    private Label urlErrorOut;
    @FXML
    private Label ratingErrorOut;

    private Map<Category, Boolean> categorySelectionMap;
    private List<Host> hostList;
    private FormValidator validator;
    private boolean dataLoaded;
    private UrlPatternUtil urlPatternUtil;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public LinkCreateContent() throws ContextLoadException {
        super("/layout/layout_edit_link.fxml");
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * CreateModalContent methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @Override
    protected void onInitializationRequested() {
        getTitleIn().setPromptText("Link's title");
        categorySelectionMap = new LinkedHashMap<>();
        hostList = new ArrayList<>();
        urlPatternUtil = new UrlPatternUtil();
        dataLoaded = false;


        // *Configuring validator:
        validator = new FormValidator()
                .addField(getTitleIn(), getTitleErrorOut(), EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(urlIn, urlErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED))
                .addField(ratingIn, ratingErrorOut, EnumSet.of(FormValidator.EValidation.REQUIRED, FormValidator.EValidation.FLOAT));


        // *Setting up the loading event:
        final TaggedEvent taggedEvent = new TaggedEvent(
                "hosts",
                "categories"
        ).setEventFinishedCallback(new EventFinishedCallback() {
            @Override
            public void onSuccess() {
                dataLoaded = true;
            }

            @Override
            public void onFailure(Set<String> failedTags) {
                // ERROR
                dataLoaded = false;
            }
        });



        try {
            DAO<Host> hostDAO = new HostDAO();
            hostDAO.retrieveMultiple(
                    host -> true,
                    Comparator.comparing(Host::getId),
                    new RetrieveMultipleDAOCallback<Host>() {
                        @Override
                        public void onSuccess(List<Host> responseList) {
                            hostList = responseList;

                            // *Adding the title's auto-fill on urlIn's listener:
                            urlIn.focusedProperty().addListener((observable, oldValue, newValue) -> {
                                if(!newValue){
                                    // *Out of focus:
                                    if(getTitleIn().getText().trim().isEmpty()){
                                        String url = urlIn.getText();
                                        Host host = urlPatternUtil.getHost(hostList, url);
                                        getTitleIn().setText(urlPatternUtil.getTitle(host, url));
                                    }
                                }
                            });

                            taggedEvent.registerStep("hosts", true);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            taggedEvent.registerStep("hosts", false);
                        }
                    });



            // *Retrieving all registered categories:
            DAO<Category> categoryDAO = new CategoryDAO();
            categoryDAO.retrieveMultiple(
                    category -> true,
                    Comparator.comparing(Category::getTitle),
                    new RetrieveMultipleDAOCallback<Category>() {
                        @Override
                        public void onSuccess(List<Category> responseList) {
                            // *Filling the selectionMap with all categories:
                            categorySelectionMap.clear();
                            responseList.forEach(category -> categorySelectionMap.put(category, false));

                            Platform.runLater(() -> {
                                taggedEvent.registerStep("categories", true);

                                // *Adding listener to categoryPicker's button:
                                categoryPickerBtn.setOnAction(event -> {
                                    try {
                                        CategoryPickerDialog categoryPicker = new CategoryPickerDialog();
                                        categoryPicker.setSelectionMap(categorySelectionMap);
                                        categoryPicker.setNeutralBtnCallback(() -> {});
                                        categoryPicker.setPositiveBtnCallback(() -> {
                                            categorySelectionMap = categoryPicker.getSelectionMap();
                                        });
                                    } catch (ContextLoadException e) {
                                        onFailure(e);
                                    }
                                });
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            // ERROR
                            taggedEvent.registerStep("categories", false);
                        }
                    });
        } catch (UndeclaredEntityException e) {
            e.printStackTrace();
            taggedEvent.registerStep("categories", false);
        }

    }


    @Override
    public void onAction() {
        if(!dataLoaded){
            return;
        }
        validator.doVisualValidation();
        if(!validator.isValid()){
            return;
        }

        String url = urlIn.getText().trim();
        Host host = new UrlPatternUtil().getHost(hostList, url);

        Link entity = new Link(
                0L,
                getTitleIn().getText().trim(),
                url,
                descIn.getText().trim(),
                favoriteIn.isSelected(),
                flaggedIn.isSelected(),
                Double.parseDouble(ratingIn.getText().trim()),
                Calendar.getInstance().getTimeInMillis(),
                host != null ? host.getId() : -1
        );


        try {
            DAO<Link> dao = new LinkDAO();
            dao.create(entity, new CreateDAOCallback() {
                @Override
                public void onSuccess(final Long linkId) {

                    // *Selecting all categories that has to be referenced:
                    List<AssociativeEntity> categoryList = new ArrayList<>();
                    categorySelectionMap.forEach((category, status) -> {
                        if(status){
                            AssociativeEntity associativeEntity = new LinkCategoryAssociation(
                                    0L,
                                    linkId,
                                    category.getId(),
                                    Calendar.getInstance().getTimeInMillis()
                            );
                            categoryList.add(associativeEntity);
                        }
                    });


                    // *Creating all the needed link-category association:
                    try {
                        DAO<AssociativeEntity> associativeEntityDAO = new AssociativeEntityDAO<>(LinkCategoryAssociation.class);
                        associativeEntityDAO.createMultiple(categoryList, new CreateMultipleDAOCallback() {
                            @Override
                            public void onSuccess(List<Long> idList) {
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
