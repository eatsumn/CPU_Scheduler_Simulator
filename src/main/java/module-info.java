module org.lorelei.cpu_scheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.lorelei.cpu_scheduler to javafx.fxml;
    exports org.lorelei.cpu_scheduler;
}