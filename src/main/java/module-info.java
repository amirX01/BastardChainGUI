module BastardChain {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.media;
    requires java.desktop;
    requires com.google.gson;
    requires bcprov.jdk15on;
    exports org.app.GUI;
    opens org.app.GUI to javafx.fxml;
}