package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.*;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.Process;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class OutputScreenController implements Initializable {
    @FXML private Pane ganttChart;
    @FXML private TableView<Process> resultsTable;
    @FXML private Label averageWT, averageTAT;
    @FXML private TableColumn<Process, String> processColumn;
    @FXML private TableColumn<Process, Double> arrivalColumn, burstColumn, completionColumn, waitingColumn, turnaroundColumn;

    private RoundRobin roundRobin;
    private Double quantumTime;
    private String[] algorithmChoices = {"First Come First Serve", "Short Job Next", "Round Robin"};
    private String selectedAlgorithm;
    private ArrayList<Process> inputTable = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processColumn.setCellValueFactory(new PropertyValueFactory<>("processNumberDisplay"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        completionColumn.setCellValueFactory(new PropertyValueFactory<>("completeTime"));
        waitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        turnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));
    }

    public void setResult(Algorithm result) {
        System.out.println(result.getGanttChart().getGanttChartProcess());
        resultsTable.getItems().setAll(result.getGanttChart().getGanttChartProcess());
        averageWT.setText("Average WT: " + format(result.getAverageWaitingTime()));
        averageTAT.setText("Average TAT: " + format(result.getAverageTurnaroundTime()));
        var cells = result.getGanttChart().getChart();
        if (cells.isEmpty()) return;
        double start = cells.getFirst().getStartTime();
        double end = cells.getLast().getCompleteTime();
        double scale = 52;
        double left = 34;
        ganttChart.setPrefWidth(Math.max(650, (end - start) * scale + left * 2));
        ganttChart.setMinWidth(ganttChart.getPrefWidth());
        ganttChart.setPrefHeight(112);
        ganttChart.setMinHeight(112);
        for (GanttCell cell : cells) {
            double x1 = left + (cell.getStartTime() - start) * scale;
            double x2 = left + (cell.getCompleteTime() - start) * scale;
            double width = x2 - x1;
            Label time = new Label(format(cell.getStartTime()));
            time.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #172033;");
            time.setLayoutX(x1 - 25); time.setLayoutY(5); time.setPrefWidth(50); time.setAlignment(javafx.geometry.Pos.CENTER);
            ganttChart.getChildren().add(time);
            Rectangle block = new Rectangle(x1, 47, width, 40);
            block.setFill(cell.isIdle() ? Color.web("#e2e8f0") : Color.web("#dbeafe"));
            block.setStroke(Color.web("#040910"));
            ganttChart.getChildren().add(block);
            Label name = new Label(cell.isIdle() ? "Idle" : "P" + cell.getProcess().getProcessNumber());
            name.setLayoutX(x1); name.setLayoutY(52); name.setPrefWidth(width); name.setAlignment(javafx.geometry.Pos.CENTER);
            name.setStyle("-fx-font-family: monospace; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #172033;");
            ganttChart.getChildren().add(name);
        }
        double finalX = left + (end - start) * scale;
        Label lastTime = new Label(format(end));
        lastTime.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111316;");
        lastTime.setLayoutX(finalX - 25); lastTime.setLayoutY(5); lastTime.setPrefWidth(50); lastTime.setAlignment(javafx.geometry.Pos.CENTER);
        ganttChart.getChildren().add(lastTime);
    }

    public void submitTable(ArrayList<Process> inputTable, String selectedAlgorithm, double quantumTime) {
        this.inputTable = new ArrayList<>(inputTable);
        this.quantumTime = quantumTime;
        this.selectedAlgorithm = selectedAlgorithm;
        startAlgorithm();
    }

    public void submitTable(ArrayList<Process> inputTable, String selectedAlgorithm) {
        this.inputTable = new ArrayList<>(inputTable);
        this.selectedAlgorithm = selectedAlgorithm;
        this.quantumTime = null;
        startAlgorithm();
    }

    public void startAlgorithm() {
        System.out.println("NOW IN |" + inputTable);
        System.out.println("QUANTUM TIME: " + quantumTime);
        System.out.println("Selected Algo: " + selectedAlgorithm);

        if (Objects.equals(selectedAlgorithm, algorithmChoices[0])) {
            System.out.println(inputTable);
            FCFS fcfs = new FCFS(inputTable);
            setResult(fcfs);
            System.out.println(fcfs.getGanttChart());
        } else if (Objects.equals(selectedAlgorithm, algorithmChoices[2])) {
            roundRobin = new RoundRobin(inputTable, quantumTime);
            System.out.println(roundRobin.getGanttChart());
            setResult(roundRobin);
        }
    }

    @FXML private void backToMenu(ActionEvent event) throws IOException {
        navigate(event, "/fxml/MenuScreen.fxml");
    }

    @FXML private void newCalculation(ActionEvent event) throws IOException {
        navigate(event, "/fxml/SelectionScreen.fxml");
    }

    private void navigate(ActionEvent event, String resource) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private String format(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.format(java.util.Locale.ROOT, "%.2f", value);
    }
}
