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
        root = FXMLLoader.load(getClass().getResource("/fxml/SelectionScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void ExitProgram(ActionEvent e){

    }


}
