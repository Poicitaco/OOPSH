package com.pocitaco.oopsh.controllers.admin;

import com.pocitaco.oopsh.controllers.BaseController;
import com.pocitaco.oopsh.dao.ExamTypeDAO;
import com.pocitaco.oopsh.models.ExamType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ExamCreateController extends BaseController {

    @FXML
    private MFXTextField txtCode;

    @FXML
    private MFXTextField txtName;

    @FXML
    private MFXTextField txtDescription;

    @FXML
    private MFXTextField txtFee;

    @FXML
    private MFXTextField txtTheoryPassScore;

    @FXML
    private MFXTextField txtPracticePassScore;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnSave;

    private ExamTypeDAO examTypeDAO;

    @Override
    public void initializeComponents() {
        this.examTypeDAO = new ExamTypeDAO();
    }

    @Override
    protected void setupEventHandlers() {
        btnSave.setOnAction(event -> handleSaveExamType());
        btnCancel.setOnAction(event -> handleCancel());
    }

    private void handleSaveExamType() {
        if (validateInput()) {
            ExamType newExamType = new ExamType();
            newExamType.setCode(txtCode.getText());
            newExamType.setName(txtName.getText());
            newExamType.setDescription(txtDescription.getText());
            newExamType.setFee(Double.parseDouble(txtFee.getText()));
            newExamType.setTheoryPassScore(Integer.parseInt(txtTheoryPassScore.getText()));
            newExamType.setPracticePassScore(Integer.parseInt(txtPracticePassScore.getText()));

            examTypeDAO.create(newExamType);

            showInfo("Exam Type Created", "Exam Type " + newExamType.getName() + " has been created successfully.");
            clearForm();
            // Optionally, navigate back to the exam management screen
        }
    }

    private void handleCancel() {
        // Logic to navigate back to the exam management screen
        showInfo("Cancelled", "Create exam type operation cancelled.");
    }

    private boolean validateInput() {
        if (txtCode.getText().isEmpty() || txtName.getText().isEmpty() || txtFee.getText().isEmpty() ||
            txtTheoryPassScore.getText().isEmpty() || txtPracticePassScore.getText().isEmpty()) {
            showError("Validation Error", "Please fill in all required fields.");
            return false;
        }
        try {
            Double.parseDouble(txtFee.getText());
            Integer.parseInt(txtTheoryPassScore.getText());
            Integer.parseInt(txtPracticePassScore.getText());
        } catch (NumberFormatException e) {
            showError("Validation Error", "Fee and scores must be numbers.");
            return false;
        }
        return true;
    }

    @Override
    protected void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtDescription.clear();
        txtFee.clear();
        txtTheoryPassScore.clear();
        txtPracticePassScore.clear();
    }

    @Override
    protected void setFormEnabled(boolean enabled) {
        txtCode.setDisable(!enabled);
        txtName.setDisable(!enabled);
        txtDescription.setDisable(!enabled);
        txtFee.setDisable(!enabled);
        txtTheoryPassScore.setDisable(!enabled);
        txtPracticePassScore.setDisable(!enabled);
        btnSave.setDisable(!enabled);
        btnCancel.setDisable(!enabled);
    }
}
