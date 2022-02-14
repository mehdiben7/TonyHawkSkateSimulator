module ca.remechda.remechda {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.thetonyhawks.tonyhawksimulator to javafx.controls;
    exports ca.thetonyhawks.tonyhawksimulator;
}