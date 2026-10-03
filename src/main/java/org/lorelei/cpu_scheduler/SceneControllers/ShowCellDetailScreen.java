package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.Process;
import org.lorelei.cpu_scheduler.SchedulingAlgorithm.GanttCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowCellDetailScreen   {

    GanttCell selectedGanttCell;
    @FXML
    AnchorPane rootPane;

    @FXML
    private Button backButton;

    @FXML
    private Text chartCellNumberDisplay;

    @FXML
    private Text processNumberDisplay;

    @FXML
    private VBox waitListContainer;

    @FXML
    private VBox readyListContainer;

    @FXML
    private VBox completedListContainer;

    @FXML
    void back(ActionEvent event) {
        if (rootPane.getParent() instanceof Pane pane) {
            pane.getChildren().remove(rootPane);
        }
    }
    public enum listType{
        waitList,
        readyList,
        completedList
    }


    private void populateList(ArrayList<Process> pL, listType x) {
        List<Text> items = pL.stream().map(p -> {
            Text t = new Text(String.valueOf(p.getProcessNumberDisplay()));
            t.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: white;");
            return t;
        }).toList();

        if (x == listType.completedList) {
            completedListContainer.getChildren().setAll(items);
        } else if (x == listType.readyList) {
            readyListContainer.getChildren().setAll(items);
        } else if (x == listType.waitList) {
            waitListContainer.getChildren().setAll(items);
        }
    }


    public static void show(Object a, Pane original, GanttCell cell) {
        try {
            FXMLLoader loader = new FXMLLoader(a.getClass().getResource("/fxml/ShowCellDetailScreen.fxml"));
            Parent overlay = loader.load();
            ShowCellDetailScreen controller = loader.getController();
            controller.setCell(cell);
            original.getChildren().add(overlay);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCell(GanttCell cell) {
        this.selectedGanttCell = cell;

        if (!cell.isIdle()) {
            chartCellNumberDisplay.setText(String.valueOf(cell.getProcess().getProcessNumberDisplay()));
        } else {
            chartCellNumberDisplay.setText("Idle");
        }
        processNumberDisplay.setText("Cell #" + cell.getIndex_ID());

        populateList(cell.getProcessWaitList(), listType.waitList);
        populateList(cell.getProcessReadyList(), listType.readyList);
        populateList(cell.getProcessCompleteList(), listType.completedList);
    }
}
