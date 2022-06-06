module com.github.jx4e.installer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;

    opens com.github.jx4e.installer to javafx.fxml;
    exports com.github.jx4e.installer;
}