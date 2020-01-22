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
    exports sample.controller.logging;
    exports sample.controller.homeLeftPanel;
    exports sample.controller.homeCentralPanel;
    exports sample.controller.homeRightPanel.accountManagment;
    exports sample.controller.homeRightPanel.clientManagment;
    exports sample.controller.homeRightPanel.employeeManagment;
    exports sample.controller.homeRightPanel.roomManagment;
    exports sample.controller.homeRightPanel.personManagment;

    opens sample;
    opens sample.controller.logging;
    opens sample.model.entities;
    opens sample.controller.homeLeftPanel;
    opens sample.controller.homeCentralPanel;
    opens sample.controller.homeRightPanel.accountManagment;
    opens sample.controller.homeRightPanel.clientManagment;
    opens sample.controller.homeRightPanel.employeeManagment;
    opens sample.controller.homeRightPanel.roomManagment;
    opens sample.controller.homeRightPanel.personManagment;
        }