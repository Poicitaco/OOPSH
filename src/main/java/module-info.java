module com.pocitaco.oopsh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.xml;
    requires java.desktop;

    opens com.pocitaco.oopsh to javafx.fxml;
    opens com.pocitaco.oopsh.controllers to javafx.fxml;

    exports com.pocitaco.oopsh;
    exports com.pocitaco.oopsh.controllers;
    exports com.pocitaco.oopsh.models;
    exports com.pocitaco.oopsh.enums;
    exports com.pocitaco.oopsh.utils;
    exports com.pocitaco.oopsh.dao;
}
