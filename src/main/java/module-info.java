module com.example.day6exercise {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.day6exercise to javafx.fxml;
    exports com.example.day6exercise;
}