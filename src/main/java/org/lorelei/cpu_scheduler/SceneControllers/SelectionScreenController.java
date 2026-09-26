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
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.ProcessList;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.FCFS;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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

    @FXML private Button simulateButton;

    @FXML
    private Button clearListButton;

    @FXML
    private Button addRandomButton;

    @FXML
    private TextField quantumTimeInput;

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
    void AddRandomProcess(ActionEvent event) {
        ArrayList<Process> list = new ArrayList<Process>(processTable.getItems());
        System.out.println(list);
        double highAT = (ProcessList.highestAT(list));
        double highBT = (ProcessList.highestBT(list));

        if(highAT<=1) highAT = Math.random() * 10;
        if(highBT<=1) highBT = Math.random() * 10;

        Process newProcess = new Process(++tableIndex, (Math.ceil(Math.random() * (highAT + Math.random() * 5))), (Math.ceil(Math.random() * (highBT + Math.random() * 5))));
        processTable.getItems().add(newProcess);
    }

    @FXML
    void ClearList(ActionEvent event) {
        if(!processTable.getItems().isEmpty()) processTable.getItems().removeAll(processTable.getItems());
        tableIndex = 0;
    }

    @FXML
    void RemoveLastProcess(ActionEvent event) {
        if(!processTable.getItems().isEmpty()){
            processTable.getItems().removeLast();
            tableIndex--;
        }
    }

    @FXML
    void addProcess(ActionEvent event) {
        Double inputAT;
        Double inputBT;

        if(inputBurstTime.getText().isEmpty() || inputArrivalTime.getText().isEmpty()) {
            errorEmptyInput("Process");
            return;
        }

        try {
            inputAT = Double.parseDouble(inputArrivalTime.getText());
            inputBT = Double.parseDouble(inputBurstTime.getText());


        } catch (NumberFormatException e){
            errorInvalidInput("Process");
            return;
        }

        if(inputAT<0||inputBT<0) {
           errorNegativeInput("Process");
            return;
        }




        Process newProcess = new Process(++tableIndex, inputAT, inputBT);
        processTable.getItems().add(newProcess);
        inputArrivalTime.clear();
        inputBurstTime.clear();


    }

    Alert alert;

    private void errorEmptyInput(String s){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Missing Values at " + s);
        alert.setContentText("please fill out the input boxes");
        alert.show();
    }

    private void errorInvalidInput(String s){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Value at " + s);
        alert.setContentText("please enter number values");
        alert.show();
    }
    private void errorNegativeInput(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Values  at " + s);
        alert.setContentText("please enter non-negative numbers");
        alert.show();
    }


    private void editDate()  {

        tableArrivalTime.setCellFactory(TextFieldTableCell.<Process, Double>forTableColumn(new SafeDoubleStringConverter()));
        tableArrivalTime.setOnEditCommit(event ->{
            Process process = event.getTableView().getItems().get(event.getTablePosition().getRow());


            if(event.getNewValue()==null) {
                errorEmptyInput("Table");
                event.getTableView().refresh();
                return;
            }

            if(event.getNewValue()<0) {
                errorNegativeInput("Table");
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

    @FXML
    private void simulate(ActionEvent event) throws IOException {
        if (processTable.getItems().isEmpty()) {
            errorEmptyInput("Process");
            return;
        }
        if (!"First Come First Serve".equals(algoChoice.getValue())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Algorithm not available yet");
            alert.setContentText("Choose First Come First Serve to view its results.");
            alert.show();
            return;
        }
        FCFS result = new FCFS(processTable.getItems());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OutputScreen.fxml"));
        root = loader.load();
        loader.<OutputScreenController>getController().setResult(result);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void goToStart(ActionEvent event) throws IOException {
        ArrayList<Process> inputTable = new ArrayList<Process>(processTable.getItems());
        if (inputTable.isEmpty()) {
            errorEmptyInput("Empty List");
            return;
        }

        if ("Round Robin".equals(choice) && quantumTimeInputError()) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OutputScreen.fxml"));
        root = loader.load();
        OutputScreenController outputScreenController = loader.getController();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start Algorithm");
        alert.setContentText("Start?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
            return;
        }

        if ("Round Robin".equals(choice)) {
            double inputQuantumTime = Double.parseDouble(quantumTimeInput.getText());
            outputScreenController.submitTable(inputTable, choice, inputQuantumTime);
        } else {
            outputScreenController.submitTable(inputTable, choice);
        }

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


    Boolean quantumTimeInputError() {
       double inputQT;
        if(quantumTimeInput.getText().isEmpty()) {
            errorEmptyInput("Quantum Time");
            return true;
        }

        try {
            inputQT = Double.parseDouble(quantumTimeInput.getText());


        } catch (NumberFormatException e){
            errorInvalidInput("Quantum Time");
            return true;
        }

        if(inputQT<0) {
            errorNegativeInput("Quantum Time");
            return true;
        }

        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        algoChoice.setValue(algorithmChoices[0]);
        choice = algorithmChoices[0];

        timeQuantumContainer.setVisible(false);
        timeQuantumContainer.setVisible(false);
        this.tableIndex = 0;
        tableProcessNumber.setCellValueFactory(new PropertyValueFactory<Process, String>("processNumberDisplay"));
        tableArrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
        tableBurstTime.setCellValueFactory(new PropertyValueFactory<Process, Double>("burstTime"));

        algoChoice.getItems().addAll(algorithmChoices);
        algoChoice.setOnAction(this::getAlgoChoice);





        editDate(); //back

    }
}
