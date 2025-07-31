package com.pocitaco.oopsh.controllers.candidate;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.dao.RegistrationDAO;
import com.pocitaco.oopsh.models.ExamType;
import com.pocitaco.oopsh.models.Registration;
import com.pocitaco.oopsh.models.User;
import com.pocitaco.oopsh.utils.SessionManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.util.Date;

public class RegisterExamController extends BaseController {

    @FXML
    private MFXComboBox<ExamType> cmbExams;

    @FXML
    private MFXButton btnRegister;

    private ExamTypeDAO examTypeDAO;
    private RegistrationDAO registrationDAO;
    private User currentUser;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
        this.registrationDAO = new RegistrationDAO();
        this.currentUser = SessionManager.getCurrentUser();
        cmbExams.setItems(FXCollections.observableArrayList(examTypeDAO.getAll()));
    }

    @Override
    protected void setupEventHandlers() {
        btnRegister.setOnAction(event -> handleRegistration());
    }

    private void handleRegistration() {
        ExamType selectedExam = cmbExams.getValue();
        if (selectedExam != null && currentUser != null) {
            Registration newRegistration = new Registration();
            newRegistration.setUserId(currentUser.getId());
            newRegistration.setExamTypeId(selectedExam.getId());
            newRegistration.setRegistrationDate(new Date());
            newRegistration.setStatus("PENDING");

            registrationDAO.create(newRegistration);

            showInfo("Registration Successful", "You have successfully registered for " + selectedExam.getName());
        } else {
            showError("Registration Failed", "Please select an exam.");
        }
    }

    @Override
    protected void clearForm() {
        cmbExams.clearSelection();
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        cmbExams.setDisable(!enabled);
        btnRegister.setDisable(!enabled);
    }
}

