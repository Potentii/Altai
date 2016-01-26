package view;


import javafx.application.Application;
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
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/res/layout/layout_standalone.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("URLS");
        primaryStage.setScene(scene);
        primaryStage.show();






        /*
        List<Class<?>> entityList = new ArrayList<>();
        entityList.add(User.class); //TODO remove
        entityList.add(Category.class);
        entityList.add(Host.class);
        entityList.add(Link.class);

        PersistenceManager persistenceManager = new PersistenceManager(entityList){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        */


        //persistenceManager.verify();

        // TODO

    }



    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
