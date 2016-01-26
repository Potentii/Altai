package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Link;
import model.User;
import view.LinkLVAdapter;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author Guilherme Reginaldo
 * @since 24/01/2016
 */
public class LinkContextController extends ContextController implements Initializable {
    @FXML
    private ListView<Link> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LINK");

        ObservableList<Link> userList = FXCollections.observableArrayList();
        userList.addAll(
                new Link(0L, "title placeholder 1", "http://google.com/", 0.1f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.2f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.3f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.4f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.5f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.6f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.7f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.8f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 0.9f, new Date().getTime(), 0L),
                new Link(1L, "title placeholder 2", "http://google.com/", 1.0f, new Date().getTime(), 0L)
        );

        listView.setItems(userList);

        listView.setCellFactory(param -> new LinkLVAdapter());
    }


}
