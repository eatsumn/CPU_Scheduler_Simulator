package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.FileChooser;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.*;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.Process;
import org.lorelei.cpu_scheduler.Settings;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class OutputScreenController implements Initializable {
    @FXML private AnchorPane mainRoot;
    @FXML private Label algorithmLabel;
    @FXML private Pane ganttChart;
    @FXML private TableView<Process> resultsTable;
    @FXML private Label averageWT, averageTAT, totalWT, totalTAT;
    @FXML private TableColumn<Process, String> processColumn;
    @FXML private TableColumn<Process, Double> arrivalColumn, burstColumn, startColumn,completionColumn, waitingColumn, turnaroundColumn;

    private Double quantumTime;
    private String selectedAlgorithm;
    private ArrayList<Process> inputTable = new ArrayList<>();
    private Algorithm currentResult;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processColumn.setCellValueFactory(new PropertyValueFactory<>("processNumberDisplay"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        completionColumn.setCellValueFactory(new PropertyValueFactory<>("completeTime"));
        waitingColumn.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
        turnaroundColumn.setCellValueFactory(new PropertyValueFactory<>("turnaroundTime"));

        LoadingScreenController.StartLoadingScreen(this, mainRoot);
    }

    private void showTableResult(Algorithm result, int decimalPointLimit) {
        resultsTable.getItems().setAll(result.getGanttChart().getResult());

        for (TableColumn<Process, Double> col : List.of(
                arrivalColumn, burstColumn, startColumn,
                completionColumn, waitingColumn, turnaroundColumn)) {
            col.setCellFactory(c -> new TableCell<>() {
                @Override
                protected void updateItem(Double value, boolean empty) {
                    super.updateItem(value, empty);
                    if (empty || value == null) {
                        setText(null);
                    } else {
                        setText(value == Math.rint(value)
                                ? String.valueOf(value.longValue())
                                : String.format(java.util.Locale.ROOT, "%." + decimalPointLimit + "f", value));
                    }
                }
            });
        }
    }


    public void setResult(Algorithm result) {
        currentResult = result;
        System.out.println("SET RESULT INPUT RESULT: " + result.getGanttChart().getGanttChartProcess());
       // resultsTable.getItems().setAll(result.getResults());
        showTableResult(result, (String.valueOf(Settings.OutDecimalPlace).length()));
        averageWT.setText("Average WT: \n" + format(result.getAverageWaitingTime()));
        averageTAT.setText("Average TAT: \n" + format(result.getAverageTurnaroundTime()));
        totalWT.setText("Total WT: \n" + format(result.getTotalWaitingTime()));
        totalTAT.setText("Total TAT: \n" + format(result.getTotalTurnaroundTime()));
        var cells = result.getGanttChart().getChart();
        if (cells.isEmpty()) return;
        ganttChartDisplay(cells, ganttChart);
        System.out.println(result.getGanttChart());

    }

    @FXML private void exportResults() {
        if (currentResult == null) return;
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export scheduling results");
        chooser.setInitialFileName("scheduling-results.csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel-compatible CSV (*.csv)", "*.csv"));
        File file = chooser.showSaveDialog(resultsTable.getScene().getWindow());
        if (file == null) return;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            // Excel uses this marker to detect UTF-8 correctly, including on Windows.
            writer.write('\uFEFF');
            csvRow(writer, "CPU Scheduling Results");
            csvRow(writer, "Algorithm", selectedAlgorithm);
            if (quantumTime != null) csvRow(writer, "Time Quantum", format(quantumTime));
            csvRow(writer, "");
            csvRow(writer, "Summary");
            csvRow(writer, "Metric", "Value");
            csvRow(writer, "Average Waiting Time", format(currentResult.getAverageWaitingTime()));
            csvRow(writer, "Average Turnaround Time", format(currentResult.getAverageTurnaroundTime()));
            csvRow(writer, "Total Waiting Time", format(currentResult.getTotalWaitingTime()));
            csvRow(writer, "Total Turnaround Time", format(currentResult.getTotalTurnaroundTime()));
            csvRow(writer, "");
            csvRow(writer, "Process Calculations");
            csvRow(writer, "Process", "Arrival Time", "Burst Time", "Start Time", "Completion Time", "Waiting Time", "Turnaround Time");
            for (Process process : currentResult.getGanttChart().getResult()) {
                csvRow(writer, process.getProcessNumberDisplay(), format(process.getArrivalTime()),
                        format(process.getBurstTime()), format(process.getStartTime()),
                        format(process.getCompleteTime()), format(process.getWaitingTime()),
                        format(process.getTurnaroundTime()));
            }
            csvRow(writer, "");
            csvRow(writer, "Gantt Chart");
            csvRow(writer, "Process", "Start Time", "End Time", "Duration", "Wait List", "Ready List", "Completed List");
            for (GanttCell cell : currentResult.getGanttChart().getChart()) {
                csvRow(writer, cell.isIdle() ? "Idle" : "Process #" + cell.getProcess().getProcessNumber(),
                        format(cell.getStartTime()), format(cell.getCompleteTime()),
                        format(cell.getCompleteTime() - cell.getStartTime()),
                        cell.printList(GanttCell.whichList.processWaitList),
                        cell.printList(GanttCell.whichList.processReadyList),
                        cell.printList(GanttCell.whichList.processCompleteList)
                        );
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Results exported to:\n" + file.getAbsolutePath());
            alert.setHeaderText("Export complete");
            alert.showAndWait();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not export the results.\n" + exception.getMessage());
            alert.setHeaderText("Export failed");
            alert.showAndWait();
        }
    }

    private void csvRow(BufferedWriter writer, String... values) throws IOException {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) writer.write(',');
            String value = values[i] == null ? "" : values[i];
            writer.write('"');
            writer.write(value.replace("\"", "\"\""));
            writer.write('"');
        }
        writer.newLine();
    }



    private void ganttChartDisplay(ArrayList<GanttCell> cells, Pane ganttChart){
        double start = cells.getFirst().getStartTime();
        double end = cells.getLast().getCompleteTime();
        double left = 34;

        double minBlockWidth = 60;
        double smallest = Double.MAX_VALUE;
        for (GanttCell c : cells) {
            double d = c.getCompleteTime() - c.getStartTime();
            if (d > 0) smallest = Math.min(smallest, d);
        }
        double scale = 52;
        if (smallest != Double.MAX_VALUE) {
            scale = Math.max(52, minBlockWidth / smallest);
        }
        ganttChart.setPrefWidth(Math.max(650, (end - start) * scale + left * 2));
        ganttChart.setMinWidth(ganttChart.getPrefWidth());
        ganttChart.setPrefHeight(112);
        ganttChart.setMinHeight(112);

        for (GanttCell cell : cells) {
            double x1 = left + (cell.getStartTime() - start) * scale;
            double x2 = left + (cell.getCompleteTime() - start) * scale;
            double width = (x2 - x1);
            Label time = new Label(format(cell.getStartTime()));
            time.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2e1065;");
            time.setLayoutX(x1 - 25);
            time.setLayoutY(5);
            time.setPrefWidth(50);
            time.setAlignment(javafx.geometry.Pos.CENTER);
            ganttChart.getChildren().add(time);

            Rectangle block = new Rectangle(x1, 47, width, 40);
            block.setArcWidth(16);
            block.setArcHeight(16);
            block.setStrokeWidth(1.5);
            if (cell.isIdle()) {
                block.setFill(Color.web("#ede9fe"));
                block.setStroke(Color.web("#a78bfa"));
            } else {
                block.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#a78bfa")),
                        new Stop(1, Color.web("#7c3aed"))));
                block.setStroke(Color.web("#5b21b6"));
            }
            block.setEffect(new DropShadow(6, 0, 2, Color.web("#4c1d9544")));
            ganttChart.getChildren().add(block);

            Label name = new Label(cell.isIdle() ? "Idle" : "P" + cell.getProcess().getProcessNumber());
            int fontSize = width < 40 ? 11 : 16;
            name.setLayoutX(x1);
            name.setLayoutY(52);
            name.setPrefWidth(width);
            name.setMinWidth(width);
            name.setMaxWidth(width);
            name.setAlignment(javafx.geometry.Pos.CENTER);
            name.setStyle("-fx-font-family: monospace; -fx-font-size: " + fontSize + "px; -fx-font-weight: bold; "
                    + "-fx-padding: 0; -fx-background-color: transparent; "
                    + "-fx-text-fill: " + (cell.isIdle() ? "#5b21b6" : "white") + ";");
            ganttChart.getChildren().add(name);

            Button button = new Button();
            button.setOnMouseClicked(e -> ShowCellDetailScreen.show(this, mainRoot, cell));
            button.setLayoutX(x1);
            button.setLayoutY(47);
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent;");
            button.setPrefSize(width, 40);
            ganttChart.getChildren().add(button);
        }

        double finalX = left + (end - start) * scale;
        Label lastTime = new Label(format(end));
        lastTime.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2e1065;");
        lastTime.setLayoutX(finalX - 25);
        lastTime.setLayoutY(5);
        lastTime.setPrefWidth(50);
        lastTime.setAlignment(javafx.geometry.Pos.CENTER);
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

        if (Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(0))) {
            System.out.println(inputTable);
            FirstComeFirstServed firstComeFirstServed = new FirstComeFirstServed(inputTable);
            setResult(firstComeFirstServed);
        } else if (Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(1))) {
            ShortJobNext shortJobNext = new ShortJobNext(inputTable);
            setResult(shortJobNext);
        } else if (Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(2))) {
            RoundRobin roundRobin = new RoundRobin(inputTable, quantumTime);
            System.out.println(roundRobin.getGanttChart());
            setResult(roundRobin);
        } else if (Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(3))) {
            RandomNext randomNext = new RandomNext(inputTable);
            System.out.println(randomNext.getGanttChart());
            setResult(randomNext);
        } else if (Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(4))) {
            ArrivalBurstProduct arrivalBurstProduct = new ArrivalBurstProduct(inputTable);
            System.out.println(arrivalBurstProduct.getGanttChart());
            setResult(arrivalBurstProduct);
        }
        algorithmLabel.setText(Objects.equals(selectedAlgorithm, Settings.algorithmChoices.get(1))
                ? "Shortest Job Next Results"
                : selectedAlgorithm + " Results");
    }

    @FXML private void backToMenu(ActionEvent event) throws IOException {
        SceneManager.navigate(event, "/fxml/MenuScreen.fxml", this);
    }

    @FXML private void newCalculation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SelectionScreen.fxml"));
        Parent root = loader.load();
        SelectionScreenController selectionScreenController = loader.getController();
        selectionScreenController.returnToSelectionScreen(resultsTable, selectedAlgorithm, quantumTime);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private String format(double value) {
        return value == Math.rint(value) ? String.valueOf((long) value) : String.format(java.util.Locale.ROOT, "%.2f", value);
    }
}
