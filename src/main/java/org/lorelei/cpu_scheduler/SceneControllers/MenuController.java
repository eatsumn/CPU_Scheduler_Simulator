package org.lorelei.cpu_scheduler.SceneControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane rootPane;


    public void GoToSelection(ActionEvent event) throws IOException {
        SceneManager.navigate(event,"/fxml/SelectionScreen.fxml", this);
    }

    public void GoToSettings(ActionEvent event) throws IOException {
        SceneManager.navigate(event,"/fxml/SettingsMenu.fxml", this);
    }


    public void ExitProgram(ActionEvent e){
        Stage currentStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        currentStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
