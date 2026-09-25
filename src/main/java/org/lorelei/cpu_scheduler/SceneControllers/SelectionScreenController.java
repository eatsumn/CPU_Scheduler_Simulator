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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.Process;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectionScreenController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int tableIndex = 0;
    private boolean inputError;
    String choice;

    String[] algorithmChoices = {"First Come First Serve", "Short Job Next", "Round Robin"};

    @FXML
    private Button addProcessButton;

    @FXML
    private TextField inputArrivalTime;

    @FXML
    private TextField inputBurstTime;

    @FXML
    private TableView<Process> processTable;

    @FXML
    private ChoiceBox<String> algoChoice;

    @FXML
    private HBox timeQuantumContainer;

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
            errorEmptyInput();
            return;
        }

        try {
            inputAT = Double.parseDouble(inputArrivalTime.getText());
            inputBT = Double.parseDouble(inputBurstTime.getText());


        } catch (NumberFormatException e){
            errorInvalidInput();
            return;
        }

        if(inputAT<0||inputBT<0) {
           errorNegativeInput();
            return;
        }




        Process newProcess = new Process(++tableIndex, inputAT, inputBT);
        processTable.getItems().add(newProcess);
        inputArrivalTime.clear();
        inputBurstTime.clear();







    }

    private void errorEmptyInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Missing Values");
        alert.setContentText("please fill out the input boxes");
        alert.show();
    }

    private void errorInvalidInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Values");
        alert.setContentText("please enter number values");
        alert.show();
    }
    private void errorNegativeInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Values");
        alert.setContentText("please enter non-negative numbers");
        alert.show();
    }


    private void editDate()  {

        tableArrivalTime.setCellFactory(TextFieldTableCell.<Process, Double>forTableColumn(new SafeDoubleStringConverter()));
        tableArrivalTime.setOnEditCommit(event ->{
            Process process = event.getTableView().getItems().get(event.getTablePosition().getRow());


            if(event.getNewValue()==null) {
                errorEmptyInput();
                event.getTableView().refresh();
                return;
            }

            if(event.getNewValue()<0) {
                errorNegativeInput();
                event.getTableView().refresh();
                return;
            }

            process.setArrivalTime(event.getNewValue());


        });

        tableBurstTime.setCellFactory(TextFieldTableCell.<Process, Double>forTableColumn(new SafeDoubleStringConverter()));
        tableBurstTime.setOnEditCommit(event ->{
            Process process = event.getTableView().getItems().get(event.getTablePosition().getRow());
            process.setBurstTime(event.getNewValue());
            System.out.println(process.getProcessNumberDisplay());
        });

    }

    public void GoToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/MenuScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void getAlgoChoice(ActionEvent event){
        choice = algoChoice.getValue();

        if(!choice.equals("Round Robin")){
            timeQuantumContainer.setVisible(false);
            timeQuantumContainer.setVisible(false);
        }else{
            timeQuantumContainer.setVisible(true);
            timeQuantumContainer.setVisible(true);
        }
    }

    private static class SafeDoubleStringConverter extends DoubleStringConverter {
        @Override
        public Double fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algoChoice.setValue(algorithmChoices[0]);
        timeQuantumContainer.setVisible(false);
        timeQuantumContainer.setVisible(false);
        this.tableIndex = 0;
        tableProcessNumber.setCellValueFactory(new PropertyValueFactory<Process, String>("processNumberDisplay"));
        tableArrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
        tableBurstTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("burstTime"));

        algoChoice.getItems().addAll(algorithmChoices);
        algoChoice.setOnAction(this::getAlgoChoice);

        editDate();

    }
}
