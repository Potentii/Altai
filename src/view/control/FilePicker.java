package view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public class FilePicker extends VBox{
    @FXML
    private Button selectFileBtn;
    @FXML
    private Label fileOut;

    private StringProperty buttonText = new SimpleStringProperty(this, "buttonText");

    private File file;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public FilePicker(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/res/control/control_file_picker.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Initializer:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    private void initialize(){
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * FXML properties methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void setButtonText(String str){
        selectFileBtn.setText(str);
    }
    public String getButtonText(){
        return selectFileBtn.getText();
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @FXML
    private void selectFileBtn_onClick(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            fileOut.setText(selectedFile.getName());
            file = selectedFile;
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
}
