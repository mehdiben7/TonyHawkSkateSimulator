module ca.remechda.remechda {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.remechda.remechda to javafx.controls;
    exports ca.remechda.remechda;
}