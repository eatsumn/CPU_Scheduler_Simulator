package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.lorelei.cpu_scheduler.Settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML
    private Button addDecimalPlaceButton;

    @FXML
    private Button addDecimalPlaceOut;

    @FXML
    private Button decDecimalPlaceButton;

    @FXML
    private Button decDecimalPlaceOut;

    @FXML
    private Text decimalPlaceLabel;

    @FXML
    private Text decimalPlaceOut;

    @FXML
    private HBox header;

    @FXML
    private Button menuButton;

    @FXML
    private CheckBox randomFloatCheckBox;

    @FXML
    private CheckBox secretAlgoCheckBox;


    @FXML
    void addDecimalPlaceOut(ActionEvent event) {
        Settings.OutDecimalPlace*=10;
        if (Settings.OutDecimalPlace > 100000) Settings.OutDecimalPlace = 100000;
        decimalPlaceOut.setText("Decimal Place: " + 1.0/((double) Settings.OutDecimalPlace ));
    }

    @FXML
    void decreaseDecimalOut(ActionEvent event) {
        Settings.OutDecimalPlace/=10;
        if (Settings.OutDecimalPlace < 10) Settings.OutDecimalPlace = 10;
        decimalPlaceOut.setText("Decimal Place: " + 1.0/((double) Settings.OutDecimalPlace ));
    }

    @FXML
    void addDecimalPlace(ActionEvent event) {
        Settings.randomDecimalPlace*=10;
        if (Settings.randomDecimalPlace > 100000) Settings.randomDecimalPlace = 100000;
        decimalPlaceLabel.setText("Decimal Place: " + 1.0/((double) Settings.randomDecimalPlace ));
    }

    @FXML
    void decreaseDecimalPlace(ActionEvent event) {
        Settings.randomDecimalPlace/=10;
        if (Settings.randomDecimalPlace < 10) Settings.randomDecimalPlace = 10;
        decimalPlaceLabel.setText("Decimal Place: " + 1.0/((double) Settings.randomDecimalPlace ));
    }

    @FXML
    void goToMenu(ActionEvent event) throws IOException {
        SceneManager.navigate(event, "/fxml/MenuScreen.fxml",this);
    }

    @FXML
    void secretAlgoSwitch(ActionEvent event){
        if (secretAlgoCheckBox.isSelected()){
            Settings.updateAlgos(true);
        }else {
            Settings.updateAlgos(false);
        }
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
        secretAlgoCheckBox.setSelected(Settings.enableSecretAlgos);
        decimalPlaceLabel.setText("Decimal Place: " + Settings.randomDecimalPlace);

    }
}
