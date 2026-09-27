package org.lorelei.cpu_scheduler.SceneControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void GoToSelection(ActionEvent event) throws IOException {
        SceneManager.navigate(event,"/fxml/SelectionScreen.fxml", this);
    }

    public void GoToSettings(ActionEvent event) throws IOException {
        SceneManager.navigate(event,"/fxml/SettingsMenu.fxml", this);
    }


    public void ExitProgram(ActionEvent e){

    }


}
