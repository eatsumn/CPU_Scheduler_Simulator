package org.lorelei.cpu_scheduler.SceneControllers;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingScreenController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ProgressBar progressBar;

    private AnimationTimer timer;
    private double progress = 0;

    private static final double LOAD_SECONDS = 2.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.setProgress(0);
        startLoop();
    }

    private void startLoop() {
        timer = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                    return;
                }
                double delta = (now - last) / 1000000000.0;
                last = now;
                update(delta);
            }
        };
        timer.start();
    }

    private void update(double delta) {
        progress += delta / LOAD_SECONDS;
        progressBar.setProgress(Math.min(progress, 1.0));

        if (progress >= 1.0) {
            stop();
        }
    }

    public void stop() {
        timer.stop();

        FadeTransition fade = new FadeTransition(Duration.millis(300), rootPane);
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            // look up the parent NOW, not in initialize()
            if (rootPane.getParent() instanceof Pane pane) {
                pane.getChildren().remove(rootPane);
            }
        });
        fade.play();
    }


    public static void StartLoadingScreen(Object a, Pane original){
        try {
            FXMLLoader loader = new FXMLLoader(a.getClass().getResource("/fxml/LoadingScreen.fxml"));
            Parent overlay = loader.load();

            AnchorPane.setTopAnchor(overlay, 0.0);
            AnchorPane.setBottomAnchor(overlay, 0.0);
            AnchorPane.setLeftAnchor(overlay, 0.0);
            AnchorPane.setRightAnchor(overlay, 0.0);

            original.getChildren().add(overlay);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}