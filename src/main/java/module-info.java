module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.ui;
    opens com.example.demo.actors;
    opens com.example.demo.utilities;
    opens com.example.demo.projectiles;
    opens com.example.demo.levels;

    exports com.example.demo.controller;
    exports com.example.demo.ui;
    exports com.example.demo.actors;
    exports com.example.demo.utilities;
    exports com.example.demo.projectiles;
    exports com.example.demo.levels;
}