module com.example.demo_a {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires flexmark.util.data;
    requires flexmark;
    requires flexmark.util.ast;
    requires java.desktop;
    requires google.api.client;


    opens com.example.demo_a to javafx.fxml;
    exports com.example.demo_a;
}