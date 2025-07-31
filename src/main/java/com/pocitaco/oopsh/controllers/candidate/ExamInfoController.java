package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.RegistrationDAO;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Registration;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Date;

public class ExamInfoController extends BaseController {

    @FXML
    private Label lblCode;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblFee;

    @FXML
    private Label lblTheoryPassScore;

    @FXML
    private Label lblPracticePassScore;

    @FXML
    private MFXButton btnRegister;

    private ExamTypeDAO examTypeDAO;
    private RegistrationDAO registrationDAO;
    private User currentUser;
    private ExamType currentExamType;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
        this.registrationDAO = new RegistrationDAO();
        this.currentUser = SessionManager.getCurrentUser();
    }

    @Override
    protected void setupEventHandlers() {
        btnRegister.setOnAction(event -> handleRegister());
    }

    public void setExamType(ExamType examType) {
        this.currentExamType = examType;
        loadExamInfo();
    }

    private void loadExamInfo() {
        if (currentExamType != null) {
            lblCode.setText(currentExamType.getCode());
            lblName.setText(currentExamType.getName());
            lblDescription.setText(currentExamType.getDescription());
            lblFee.setText(String.valueOf(currentExamType.getFee()));
            lblTheoryPassScore.setText(String.valueOf(currentExamType.getTheoryPassScore()));
            lblPracticePassScore.setText(String.valueOf(currentExamType.getPracticePassScore()));
        }
    }

    private void handleRegister() {
        if (currentExamType != null && currentUser != null) {
            Registration newRegistration = new Registration();
            newRegistration.setUserId(currentUser.getId());
            newRegistration.setExamTypeId(currentExamType.getId());
            newRegistration.setRegistrationDate(new Date());
            newRegistration.setStatus("PENDING");

            registrationDAO.create(newRegistration);

            showInfo("Registration Successful", "You have successfully registered for " + currentExamType.getName());
        } else {
            showError("Registration Failed", "Could not register for the exam.");
        }
    }

    @Override
    protected void clearForm() {

    }

    @Override
    protected void setFormEnabled(boolean b) {

    }
}

