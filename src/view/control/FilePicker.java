package view.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public class FilePicker extends VBox{
    public static final short FILE_FILTER_IMAGE = 1;
    public static final short FILE_FILTER_TEXT = 2;
    public static final short FILE_FILTER_JSON = 3;
    public static final short FILE_FILTER_VIDEO = 4;

    @FXML
    private Button selectFileBtn;
    @FXML
    private Label fileOut;

    private StringProperty buttonText = new SimpleStringProperty(this, "buttonText");

    private File file;
    private List<ExtensionFilter> fileFilterList;
    private boolean fileChanged;



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
        fileFilterList = new ArrayList<>();
        fileChanged = false;
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
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public void printFileName(){
        if(file != null){
            fileOut.setText(file.getName());
        } else{
            fileOut.setText("");
        }
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Listener methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    @FXML
    private void selectFileBtn_onClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");

        fileChooser.getExtensionFilters().addAll(fileFilterList);
        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null){
            fileChanged = !selectedFile.equals(file);
            file = selectedFile;
            printFileName();
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

    public void setFileFilters(short... fileFilters) {
        fileFilterList.clear();
        for (short fileFilter : fileFilters) {
            ExtensionFilter filter = null;
            switch (fileFilter){
                case FILE_FILTER_IMAGE:
                    filter = new ExtensionFilter("Image", "*.jpg", "*.png", "*.bmp", "*.gif");
                    break;
                case FILE_FILTER_JSON:
                    filter = new ExtensionFilter("Json", "*.json");
                    break;
                case FILE_FILTER_TEXT:
                    filter = new ExtensionFilter("Text", "*.txt");
                    break;
                case FILE_FILTER_VIDEO:
                    filter = new ExtensionFilter("Video", "*.mp4", "*.flv");
                    break;
            }
            if(filter != null){
                fileFilterList.add(filter);
            }
        }
    }

    public boolean isFileChanged() {
        return fileChanged;
    }
    public void setFileChanged(boolean fileChanged) {
        this.fileChanged = fileChanged;
    }
}
