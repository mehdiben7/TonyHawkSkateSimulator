module ca.remechda.remechda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;
    requires org.junit.jupiter.api;

    opens ca.thetonyhawks.tonyhawksimulator to javafx.controls, javafx.fxml;
    exports ca.thetonyhawks.tonyhawksimulator;
}