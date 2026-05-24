module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.proyectofinal.app to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinal.app;
    exports co.edu.uniquindio.poo.proyectofinal.model;
    exports co.edu.uniquindio.poo.proyectofinal.controller;
    opens co.edu.uniquindio.poo.proyectofinal.model to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.controller to javafx.fxml;
}
