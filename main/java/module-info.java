module com.ip13.programming {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;

    opens com.ip13.client.gui to javafx.fxml;
    exports com.ip13.client.gui;
}