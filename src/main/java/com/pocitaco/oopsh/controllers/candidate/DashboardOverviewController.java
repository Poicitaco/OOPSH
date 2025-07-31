package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseDashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardOverviewController extends BaseDashboardController {

    @FXML
    private Label lblUpcomingExams;

    @FXML
    private Label lblCompletedExams;

    @FXML
    private Label lblCertificatesEarned;

    @Override
    protected void loadInitialData() {
        super.loadInitialData();
        loadStatistics();
    }

    private void loadStatistics() {
        // In a real application, you would fetch this data from a database or service
        // based on the current user
        lblUpcomingExams.setText("3");
        lblCompletedExams.setText("12");
        lblCertificatesEarned.setText("5");
    }

    @Override
    protected void updateUserInfo() {
        // You could update a welcome message here, for example:
        // lblWelcome.setText("Welcome, " + currentUser.getFullName());
    }

    @Override
    protected void loadUserSpecificData() {
        // Load data specific to the candidate
        loadStatistics();
    }

    @Override
    protected void setupNavigation() {
        // Navigation is handled by the MainLayoutController
    }
}

