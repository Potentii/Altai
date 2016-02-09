package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.callback.Callback;
import view.exception.ContextLoadException;

import java.io.IOException;
import java.net.URL;

/**
 * @author Guilherme Reginaldo
 * @since 07/02/2016
 */
public abstract class DialogFragment {
    @FXML
    private BorderPane contentContainer;
    @FXML
    private ImageView dialogTitleImg;
    @FXML
    private Label dialogTitleOut;

    @FXML
    private Button neutralBtn;
    @FXML
    private Button negativeBtn;
    @FXML
    private Button positiveBtn;

    private Stage window;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public DialogFragment(@NotNull String contentFXML, @Nullable String titleImgPath, @NotNull String title) throws ContextLoadException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(450);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/layout_dialog.fxml"));
            loader.setController(this);
            window.setScene(new Scene(loader.load()));
            window.show();

            if(titleImgPath != null && !titleImgPath.trim().isEmpty()) {
                try {
                    dialogTitleImg.setImage(new Image(getClass().getResourceAsStream(titleImgPath)));
                } catch (NullPointerException e){
                    System.err.println("DialogFragment's header icon not found!");
                }
            } else{
                dialogTitleImg.setDisable(true);
            }

            dialogTitleOut.setText(title);
            loader = new FXMLLoader(getClass().getResource(contentFXML));
            loader.setController(this);
            contentContainer.setCenter(loader.load());

            Builder builder = onCreate(new Builder());
            builder.configureDialogButtons(neutralBtn, negativeBtn, positiveBtn);
        } catch (NullPointerException | IOException e) {
            throw new ContextLoadException(e.getCause().getMessage());
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Abstract methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @NotNull
    protected abstract Builder onCreate(Builder builder);



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    protected void close(){
        window.close();
    }






    protected final class Builder{
        private DialogButtonStruct neutralButtonStruct;
        private DialogButtonStruct negativeButtonStruct;
        private DialogButtonStruct positiveButtonStruct;



        /*
         *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
         *  * Class methods:
         *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
         */
        private void configureDialogButtons(Button neutral, Button negative, Button positive){
            if(neutralButtonStruct != null){
                neutral.setText(neutralButtonStruct.text);
                neutral.setOnAction(event -> neutralButtonStruct.callback.call());
            }
            neutral.setVisible(neutralButtonStruct != null);


            if(negativeButtonStruct != null){
                negative.setText(negativeButtonStruct.text);
                negative.setOnAction(event -> negativeButtonStruct.callback.call());
            }
            negative.setVisible(negativeButtonStruct != null);


            if(positiveButtonStruct != null){
                positive.setText(positiveButtonStruct.text);
                positive.setOnAction(event -> positiveButtonStruct.callback.call());
            }
            positive.setVisible(positiveButtonStruct != null);
        }



        /*
         *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
         *  * Getters and setters:
         *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
         */
        public void setNeutralButton(@NotNull String text, @NotNull Callback callback){
            if(text != null && callback != null){
                neutralButtonStruct = new DialogButtonStruct(text, callback);
            }
        }

        public void setNegativeButton(@NotNull String text, @NotNull Callback callback){
            if(text != null && callback != null){
                negativeButtonStruct = new DialogButtonStruct(text, callback);
            }
        }

        public void setPositiveButton(@NotNull String text, @NotNull Callback callback){
            if(text != null && callback != null){
                positiveButtonStruct = new DialogButtonStruct(text, callback);
            }
        }






        private final class DialogButtonStruct{
            public String text;
            public Callback callback;

            public DialogButtonStruct(String text, Callback callback) {
                this.text = text;
                this.callback = callback;
            }
        }
    }


}
