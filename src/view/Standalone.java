package view;



import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author Guilherme Reginaldo
 * @since 21/01/2016
 */
public class Standalone extends Application {
    private static HostServices hostServices;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/res/layout/layout_standalone.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Altai");
        primaryStage.setScene(scene);
        primaryStage.show();

        hostServices = getHostServices();
    }

    public static HostServices requestHostServices(){
        return hostServices;
    }

}
