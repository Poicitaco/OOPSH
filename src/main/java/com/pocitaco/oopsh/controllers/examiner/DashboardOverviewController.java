package com.pocitaco.oopsh.controllers.examiner;

import com.pocitaco.oopsh.controllers.BaseDashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardOverviewController extends BaseDashboardController {

    @FXML
    private Label lblExamsToGrade;

    @FXML
    private Label lblGradedExams;

    @FXML
    private Label lblAssignedSessions;

    @Override
    protected void loadInitialData() {
        super.loadInitialData();
        loadStatistics();
    }

    private void loadStatistics() {
        // In a real application, you would fetch this data from a database or service
        // based on the current user
        lblExamsToGrade.setText("15");
        lblGradedExams.setText("27");
        lblAssignedSessions.setText("4");
    }

    @Override
    protected void updateUserInfo() {
        // You could update a welcome message here, for example:
        // lblWelcome.setText("Welcome, " + currentUser.getFullName());
    }

    @Override
    protected void loadUserSpecificData() {
        // Load data specific to the examiner
        loadStatistics();
    }

    @Override
    protected void setupNavigation() {
        // Navigation is handled by the MainLayoutController
    }
}
