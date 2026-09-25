package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.Process;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectionScreenController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int tableIndex = 0;
    private boolean inputError;


    @FXML
    private Button addProcessButton;

    @FXML
    private TextField inputArrivalTime;

    @FXML
    private TextField inputBurstTime;

    @FXML
    private TableView<Process> processTable;

    @FXML
    private TableColumn<Process, Double> tableArrivalTime;

    @FXML
    private TableColumn<Process, String> tableProcessNumber;

    @FXML
    private TableColumn<Process, Double> tableBurstTime;

    @FXML
    void addProcess(ActionEvent event) {
        Double inputAT;
        Double inputBT;

        if(inputBurstTime.getText().isEmpty() || inputArrivalTime.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Missing Values");
            alert.setContentText("please fill out the input boxes");
            alert.show();
            return;
        }

        try {
            inputAT = Double.parseDouble(inputArrivalTime.getText());
            inputBT = Double.parseDouble(inputBurstTime.getText());


        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Values");
            alert.setContentText("please enter number values");
            alert.show();
            return;
        }

        if(inputAT<0||inputBT<0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Values");
            alert.setContentText("Values must not be negative numbers");
            alert.show();
            return;
        }




        Process newProcess = new Process(++tableIndex, inputAT, inputBT);
        processTable.getItems().add(newProcess);
        inputArrivalTime.clear();
        inputBurstTime.clear();







    }

    public void GoToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MenuScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tableIndex = 0;
        tableProcessNumber.setCellValueFactory(new PropertyValueFactory<Process, String>("processNumberDisplay"));
        tableArrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
        tableBurstTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("burstTime"));




    }
}
