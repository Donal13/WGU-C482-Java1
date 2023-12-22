module task1.c482 {
    requires javafx.controls;
    requires javafx.fxml;

    opens model to javafx.base;
    opens task1.c482 to javafx.fxml;
    exports task1.c482;
}