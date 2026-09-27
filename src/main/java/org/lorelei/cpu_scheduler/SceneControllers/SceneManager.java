package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static void navigate(ActionEvent event, String resource, Object self) throws IOException {
        Parent root = FXMLLoader.load(self.getClass().getResource(resource));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
