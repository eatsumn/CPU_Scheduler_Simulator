package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import org.lorelei.cpu_scheduler.Settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML
    private Button addDecimalPlaceButton;

    @FXML
    private Button decDecimalPlaceButton;

    @FXML
    private Text decimalPlaceLabel;

    @FXML
    private Button menuButton;

    @FXML
    private CheckBox randomFloatCheckBox;

    @FXML
    void addDecimalPlace(ActionEvent event) {
        Settings.randomDecimalPlace+=10;
        decimalPlaceLabel.setText("Decimal Place: " + Settings.randomDecimalPlace);
    }

    @FXML
    void decreaseDecimalPlace(ActionEvent event) {
        Settings.randomDecimalPlace-=10;
        if (Settings.randomDecimalPlace == 0) Settings.randomDecimalPlace = 10;
        decimalPlaceLabel.setText("Decimal Place: " + Settings.randomDecimalPlace);
    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        SceneManager.navigate(event, "/fxml/MenuScreen.fxml",this);
    }

    @FXML
    void randomFloatSwitch(ActionEvent event) {
        if (randomFloatCheckBox.isSelected()){
            Settings.randomFloat=true;
        }else {
            Settings.randomFloat=false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        randomFloatCheckBox.setSelected(Settings.randomFloat);
        decimalPlaceLabel.setText("Decimal Place: " + Settings.randomDecimalPlace);

    }
}
