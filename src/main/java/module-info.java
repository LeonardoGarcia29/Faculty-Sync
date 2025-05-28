module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;

    opens s25.cs151.application to javafx.fxml;
    opens s25.cs151.application.Controllers to javafx.fxml;
    exports s25.cs151.application to javafx.graphics;
    exports s25.cs151.application.Controllers to javafx.fxml;
    exports s25.cs151.application.JavaBeans to javafx.graphics;
    opens s25.cs151.application.JavaBeans to javafx.fxml;
}