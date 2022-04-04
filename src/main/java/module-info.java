module ca.remechda.remechda {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;

    opens ca.thetonyhawks.tonyhawksimulator to javafx.controls, javafx.fxml;
    exports ca.thetonyhawks.tonyhawksimulator;
    exports ca.thetonyhawks.tonyhawksimulator.tests;
    exports ca.thetonyhawks.tonyhawksimulator.model.planes;
    opens ca.thetonyhawks.tonyhawksimulator.model.planes to javafx.controls, javafx.fxml;
    exports ca.thetonyhawks.tonyhawksimulator.model;
    opens ca.thetonyhawks.tonyhawksimulator.model to javafx.controls, javafx.fxml;
    exports ca.thetonyhawks.tonyhawksimulator.controller;
    opens ca.thetonyhawks.tonyhawksimulator.controller to javafx.controls, javafx.fxml;
}