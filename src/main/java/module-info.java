module ca.remechda.remechda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ca.thetonyhawks.tonyhawksimulator to javafx.controls, javafx.fxml;
    exports ca.thetonyhawks.tonyhawksimulator;
}