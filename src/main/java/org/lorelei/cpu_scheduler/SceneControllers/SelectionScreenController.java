package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionScreenController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button addProcess;

    @FXML
    private TextField inputArrivalTime;

    @FXML
    private TextField inputBurstTime;

    @FXML
    private TextField inputProcessName;

    @FXML
    private TableColumn<?, ?> tableArrivalTime;

    @FXML
    private TableColumn<?, ?> tableProcessNumber;

    @FXML
    private TableColumn<?, ?> timeBurstTime;



    public void GoToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MenuScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
