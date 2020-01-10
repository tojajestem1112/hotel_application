module studia.programowanie.zdarzeniowe {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires mysql.connector.java;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    exports sample.Controller.logging;
    exports sample.Controller.homeLeftPanel;
    exports sample.Controller.homeCentralPanel;
    exports sample.Controller.homeRightPanel;

    opens sample;
    opens sample.Controller.logging;
    opens sample.module.entities;
    opens sample.Controller.homeLeftPanel;
    opens sample.Controller.homeCentralPanel;
    opens sample.Controller.homeRightPanel;
        }