module com.example.projectdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.projectdemo to javafx.fxml;
    exports com.example.projectdemo;
}