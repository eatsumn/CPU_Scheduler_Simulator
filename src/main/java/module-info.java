module org.lorelei.cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens org.lorelei.cpu_scheduler to javafx.fxml;
    exports org.lorelei.cpu_scheduler;
    opens org.lorelei.cpu_scheduler.SceneControllers to javafx.fxml;  // <-- add this
}