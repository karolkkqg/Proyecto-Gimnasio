module mx.blackgym {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires java.base;
    requires javafx.graphics;

    opens mx.bg.gui to javafx.fxml;
    exports mx.bg.logica;
    exports mx.bg.gui;

}
